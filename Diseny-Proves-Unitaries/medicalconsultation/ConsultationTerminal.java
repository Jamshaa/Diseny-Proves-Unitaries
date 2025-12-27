package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import data.DigitalSignature;
import medicalconsultation.exceptions.*;
import services.HealthNationalService;
import services.DecisionMakingAI;
import services.Suggestion;
import services.exceptions.*;
import java.net.ConnectException;
import java.util.Date;
import java.util.List;

public class ConsultationTerminal {

    private enum State {
        INIT, READY, EDITING, SIGNED, FINISHED
    }

    private State currentState;
    private HealthNationalService healthService;
    private DecisionMakingAI aiService;

    private MedicalHistory currentHistory;
    private MedicalPrescription currentPrescription;
    private HealthCardID currentCIP;
    private String currentIllness;

    private boolean isPeriodSet = false;

    public ConsultationTerminal() {
        this.currentState = State.INIT;
    }

    public void setHealthNationalService(HealthNationalService service) {
        this.healthService = service;
    }

    public void setDecisionMakingAI(DecisionMakingAI service) {
        this.aiService = service;
    }

    public void initRevision(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException {
        this.currentCIP = cip;
        this.currentIllness = illness;
        this.currentHistory = healthService.getMedicalHistory(cip);
        this.currentPrescription = healthService.getMedicalPrescription(cip, illness);
        this.currentState = State.READY;
    }

    public void initMedicalPrescriptionEdition() throws ProceduralException {
        if (currentState != State.READY) {
            throw new ProceduralException("Revision must be in READY state to start edition.");
        }
        this.currentState = State.EDITING;
    }

    public void enterMedicalAssessmentInHistory(String assess)
            throws ProceduralException, IncorrectParametersException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("Terminal must be in EDITING state.");
        }
        currentHistory.addMedicalHistoryAnnotations(assess);
    }

    public void enterMedicineWithGuidelines(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException, ProceduralException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("Terminal must be in EDITING state.");
        }
        createMedPrescriptionLine(prodID, instruc);
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException, ProceduralException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("Terminal must be in EDITING state.");
        }
        currentPrescription.modifyDoseInLine(prodID, newDose);
    }

    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException, ProceduralException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("Terminal must be in EDITING state.");
        }
        currentPrescription.removeLine(prodID);
    }

    public void enterTreatmentEndingDate(Date date)
            throws IncorrectEndingDateException, ProceduralException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("Terminal must be in EDITING state.");
        }
        setPrescDateAndEndDate(date);
        this.isPeriodSet = true;
    }

    public void finishMedicalPrescriptionEdition() throws ProceduralException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("Terminal must be in EDITING state.");
        }
        if (!isPeriodSet) {
            throw new ProceduralException("Treatment period must be set before finishing edition.");
        }
        // State remains EDITING until signed or specifically transitioned if needed by
        // contract
    }

    public void stampeeSignature() throws eSignatureException, ProceduralException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("Terminal must be in EDITING state.");
        }
        if (!isPeriodSet) {
            throw new ProceduralException("Treatment period must be set before signing.");
        }

        // Simulating electronic signature acquisition
        // In a real scenario, this would interact with a signature provider
        byte[] dummySign = "DoctorSignaturePlaceholder".getBytes();
        try {
            DigitalSignature signature = new DigitalSignature(dummySign);
            currentPrescription.setESign(signature);
            this.currentState = State.SIGNED;
        } catch (Exception e) {
            throw new eSignatureException("Failed to generate digital signature: " + e.getMessage());
        }
    }

    public MedicalPrescription sendHistoryAndPrescription()
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException,
            NotCompletedMedicalPrescription, ProceduralException {
        if (currentState != State.SIGNED) {
            throw new ProceduralException("Prescription must be SIGNED before sending.");
        }

        MedicalPrescription result = healthService.sendHistoryAndPrescription(
                currentCIP, currentHistory, currentIllness, currentPrescription);

        this.currentState = State.FINISHED;
        return result;
    }

    // AI methods
    public void callDecisionMakingAI() throws AIException, ProceduralException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("AI can only be consulted during edition.");
        }
        aiService.initDecisionMakingAI();
    }

    public void askAIForSuggest(String prompt) throws BadPromptException, ProceduralException {
        if (currentState != State.EDITING) {
            throw new ProceduralException("AI can only be consulted during edition.");
        }
        aiService.getSuggestions(prompt);
    }

    public void extractGuidelinesFromSugg() throws ProceduralException {
        // Implementation logic not strictly requested, but must check state
        if (currentState != State.EDITING) {
            throw new ProceduralException("Guidelines can only be extracted during edition.");
        }
    }

    // Private helper methods
    private void createMedPrescriptionLine(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException {
        currentPrescription.addLine(prodID, instruc);
    }

    private void setPrescDateAndEndDate(Date date) throws IncorrectEndingDateException {
        if (date == null || date.before(new Date())) {
            throw new IncorrectEndingDateException("Ending date must be in the future.");
        }
        currentPrescription.setPrescDate(new Date());
        currentPrescription.setEndDate(date);
    }
}
