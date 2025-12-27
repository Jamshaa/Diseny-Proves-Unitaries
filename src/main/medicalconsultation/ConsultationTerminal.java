package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import data.exceptions.DigitalSignatureException;
import data.exceptions.HealthCardIDException;
import medicalconsultation.exceptions.IncorrectEndingDateException;
import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProceduralException;
import medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import medicalconsultation.exceptions.ProductNotInPrescriptionException;
import medicalconsultation.exceptions.eSignatureException;
import services.DecisionMakingAI;
import services.HealthNationalService;
import services.Suggestion;
import services.exceptions.AIException;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.BadPromptException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

public class ConsultationTerminal {

    // Dependencies
    private HealthNationalService hns;
    private DecisionMakingAI ai;

    // Session state
    private HealthCardID cip;
    private String illness;
    private MedicalHistory history;
    private MedicalPrescription prescription;

    // IA
    private String lastAIAnswer;
    private List<Suggestion> lastSuggestions;

    // Edition state
    private boolean revisionStarted = false;
    private boolean editingPrescription = false;
    private boolean signed = false;


    public ConsultationTerminal(Object o, Object object) {
        // dependencies injected later with setters
    }

    public void setHealthNationalService(HealthNationalService hns) {
        this.hns = hns;
    }

    public void setDecisionMakingAI(DecisionMakingAI ai) {
        this.ai = ai;
    }


    public void initRevision(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException, ProceduralException {

        if (hns == null) throw new ProceduralException("HealthNationalService not set");
        if (cip == null) throw new IllegalArgumentException("cip cannot be null");
        if (illness == null || illness.isBlank()) throw new IllegalArgumentException("illness cannot be null/blank");

        this.cip = cip;
        this.illness = illness;

        this.history = hns.getMedicalHistory(cip);
        this.prescription = hns.getMedicalPrescription(cip, illness);

        this.revisionStarted = true;
        this.editingPrescription = false;
        this.signed = false;
    }

    public void enterMedicalAssessmentInHistory(String assess) throws ProceduralException {
        ensureRevisionStarted();
        if (assess == null || assess.isBlank()) throw new IllegalArgumentException("assess cannot be null/blank");
        history.addMedicalHistoryAnnotations(assess);
    }

    public void initMedicalPrescriptionEdition() throws ProceduralException {
        ensureRevisionStarted();
        this.editingPrescription = true;
    }

    public void enterMedicineWithGuidelines(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException, ProceduralException {

        ensureEditing();
        createMedPrescriptionLine(prodID, instruc);
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException, ProceduralException {

        ensureEditing();
        prescription.modifyDoseInLine(prodID, newDose);
    }

    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException, ProceduralException {

        ensureEditing();
        prescription.removeLine(prodID);
    }

    public void enterTreatmentEndingDate(Date date)
            throws IncorrectEndingDateException, ProceduralException {

        ensureEditing();
        if (date == null) throw new IllegalArgumentException("date cannot be null");

        Date now = new Date();
        if (!date.after(now)) {
            throw new IncorrectEndingDateException("Ending date must be in the future");
        }

        long diffMs = date.getTime() - now.getTime();
        long oneDayMs = 24L * 60 * 60 * 1000;
        if (diffMs < oneDayMs) {
            throw new IncorrectEndingDateException("Ending date too close to current date");
        }

        // Guardamos fechas en la prescripción
        setPrescDateAndEndDate(date);
    }

    public void finishMedicalPrescriptionEdition() throws ProceduralException {
        ensureEditing();
        this.editingPrescription = false;
    }

    public void stampeeSignature() throws eSignatureException {
        if (prescription == null) {
            throw new IllegalStateException("No prescription loaded. Call initRevision first.");
        }

        try {
            DigitalSignature sign = new DigitalSignature("SIGNED".getBytes());
            prescription.seteSign(sign);
        } catch (DigitalSignatureException e) {
            throw new eSignatureException("Error stamping electronic signature", e);
        }
    }

    public MedicalPrescription sendHistoryAndPrescription()
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException,
            NotCompletedMedicalPrescription, ProceduralException {

        ensureRevisionStarted();
        if (editingPrescription) throw new ProceduralException("Finish edition before sending");

        // normalmente se espera que esté “lista”

        prescription = hns.sendHistoryAndPrescription(cip, history, illness, prescription);
        return prescription;
    }


    public void callDecisionMakingAI() throws AIException, ProceduralException {
        if (ai == null) throw new ProceduralException("DecisionMakingAI not set");
        ai.initDecisionMakingAI();
    }

    public void askAIForSuggest(String prompt) throws BadPromptException, ProceduralException {
        if (ai == null) throw new ProceduralException("DecisionMakingAI not set");
        if (prompt == null || prompt.isBlank()) throw new IllegalArgumentException("prompt cannot be null/blank");
        lastAIAnswer = ai.getSuggestions(prompt);
    }

    public void extractGuidelinesFromSugg() throws ProceduralException {
        if (ai == null) throw new ProceduralException("DecisionMakingAI not set");
        if (lastAIAnswer == null) throw new ProceduralException("No AI answer available. Call askAIForSuggest first.");
        lastSuggestions = ai.parseSuggest(lastAIAnswer);
    }

    public List<Suggestion> getLastSuggestions() {
        return lastSuggestions;
    }

    //operaciones internas

    private void createMedPrescriptionLine(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException {

        if (prodID == null) throw new IllegalArgumentException("prodID cannot be null");
        if (instruc == null) throw new IllegalArgumentException("instruc cannot be null");
        prescription.addLine(prodID, instruc);
    }

    private void setPrescDateAndEndDate(Date end) {
        // prescDate = now
        prescription.setPrescDate(new Date());
        prescription.setEndDate(end);
    }


    private void ensureRevisionStarted() throws ProceduralException {
        if (!revisionStarted || cip == null || illness == null || history == null || prescription == null) {
            throw new ProceduralException("initRevision must be called first");
        }
    }

    private void ensureEditing() throws ProceduralException {
        ensureRevisionStarted();
        if (!editingPrescription) {
            throw new ProceduralException("initMedicalPrescriptionEdition must be called first");
        }
    }
}
