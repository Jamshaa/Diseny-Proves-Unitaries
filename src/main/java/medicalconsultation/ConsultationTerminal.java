package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import data.exceptions.DigitalSignatureException;
import data.exceptions.HealthCardIDException;
import medicalconsultation.exceptions.*;
import services.DecisionMakingAI;
import services.HealthNationalService;
import services.Suggestion;
import services.exceptions.*;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

public class ConsultationTerminal {

    private HealthNationalService healthNationalService;
    private DecisionMakingAI decisionMakingAI;

    private HealthCardID currentCIP;
    private MedicalHistory currentMedicalHistory;
    private MedicalPrescription currentMedicalPrescription;

    public void setHealthNationalService(HealthNationalService hns) {
        this.healthNationalService = hns;
    }

    public void setDecisionMakingAI(DecisionMakingAI decisionMakingAI) {
        this.decisionMakingAI = decisionMakingAI;
    }

    public void initRevision(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException {

        this.currentCIP = cip;
        this.currentMedicalHistory = healthNationalService.getMedicalHistory(cip);
        this.currentMedicalPrescription = healthNationalService.getMedicalPrescription(cip, illness);
    }

    public void enterMedicalAssessmentInHistory(String assess) {
        checkProcedure(currentMedicalHistory != null, "Medical History not initialized");
        currentMedicalHistory.addMedicalHistoryAnnotations(assess);
    }

    public void initMedicalPrescriptionEdition() {
        checkProcedure(currentMedicalPrescription != null, "Medical Prescription not initialized");
        // Logic to start edition if any specific state needed, otherwise just ensuring
        // it's loaded is enough
    }

    public void enterMedicineWithGuidelines(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException {
        checkProcedure(currentMedicalPrescription != null, "Prescription edition not active");
        currentMedicalPrescription.addLine(prodID, instruc);
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException {
        checkProcedure(currentMedicalPrescription != null, "Prescription edition not active");
        currentMedicalPrescription.modifyDoseInLine(prodID, newDose);
    }

    public void removeLine(ProductID prodID) throws ProductNotInPrescriptionException {
        checkProcedure(currentMedicalPrescription != null, "Prescription edition not active");
        currentMedicalPrescription.removeLine(prodID);
    }

    public void enterTreatmentEndingDate(Date date) throws IncorrectEndingDateException {
        checkProcedure(currentMedicalPrescription != null, "Prescription edition not active");
        Date now = new Date();
        if (date.before(now) || date.equals(now)) {
            throw new IncorrectEndingDateException("Ending date must be in the future");
        }
        currentMedicalPrescription.setPrescDate(now);
        currentMedicalPrescription.setEndDate(date);
    }

    public void finishMedicalPrescriptionEdition() {
        // Just a state transition if we had a detailed state machine
    }

    public void stampeeSignature() throws eSignatureException {
        checkProcedure(currentMedicalPrescription != null && currentMedicalPrescription.getEndDate() != null,
                "Treatment ending date not set");
        try {
            // Mocking a signature generation
            byte[] signatureBytes = new byte[] { 1, 2, 3, 4 };
            DigitalSignature signature = new DigitalSignature(signatureBytes);
            currentMedicalPrescription.seteSign(signature);
        } catch (DigitalSignatureException e) {
            throw new eSignatureException("Failed to generate signature: " + e.getMessage());
        }
    }

    public MedicalPrescription sendHistoryAndPrescription()
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException,
            NotCompletedMedicalPrescription {

        checkProcedure(currentMedicalPrescription != null && currentMedicalPrescription.geteSign() != null,
                "Signature not stamped");

        MedicalPrescription result = healthNationalService.sendHistoryAndPrescription(
                currentCIP,
                currentMedicalHistory,
                currentMedicalPrescription.getIllness(),
                currentMedicalPrescription);
        this.currentMedicalPrescription = result; // Update with the one returned (with code)
        return result;
    }

    // AI Operations

    public void callDecisionMakingAI() throws AIException {
        checkProcedure(currentMedicalPrescription != null, "Prescription edition not active");
        decisionMakingAI.initDecisionMakingAI();
    }

    public void askAIForSuggest(String prompt) throws BadPromptException {
        // In a real app this would store the answer to be displayed or parsed
        // For this assignment, we mostly delegate
        String response = decisionMakingAI.getSuggestions(prompt);
        System.out.println("AI Response: " + response);
    }

    public List<Suggestion> extractGuidelinesFromSugg(String aiAnswer) {
        // The prompt implies the system asks the AI to parse *its own previous answer*
        // or a specific text.
        // 'parseSuggest(String aiAnswer)' in interface.
        // Context: "Una vez obtenida la respuesta, el sistema solicita a la IA que
        // desglose el resultado..."
        return decisionMakingAI.parseSuggest(aiAnswer);
    }

    // Internal helper for preconditions
    private void checkProcedure(boolean condition, String message) {
        if (!condition) {
            throw new ProceduralException(message);
        }
    }
}
