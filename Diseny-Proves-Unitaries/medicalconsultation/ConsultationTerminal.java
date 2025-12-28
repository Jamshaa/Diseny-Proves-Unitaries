package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import medicalconsultation.exceptions.*;
import services.*;
import services.exceptions.*;

import java.net.ConnectException;
import java.util.Date;

public class ConsultationTerminal {

    private HealthCardID cip;
    private String illness;
    private MedicalHistory hce;
    private MedicalPrescription mPresc;
    private boolean editingPrescription = false;
    private boolean treatmentPeriodEstablished = false;
    private boolean decisionMakingAIReady = false;
    private boolean gotAISuggestions = false;
    private boolean stampedESign = false;
    private String aiAnswer;
    private DigitalSignature eSign;
    private HealthNationalService hns;
    private DecisionMakingAI dmAI;

    public void initRevision(HealthCardID cip, String illness)
            throws ConnectException,
            HealthCardIDException,
            AnyCurrentPrescriptionException {

        this.cip = cip;
        this.illness = illness;

        this.hce = hns.getMedicalHistory(cip);
        this.mPresc = hns.getMedicalPrescription(cip, illness);

    }

    public void enterMedicalAssessmentInHistory(String assess)
            throws IncorrectParametersException {

        ensureEditing();

        hce.addMedicalHistoryAnnotations(assess);
    }

    public void initMedicalPrescriptionEdition() {

        editingPrescription = true;
    }

    public void enterMedicineWithGuidelines(ProductID prodID, String[] instruc)
            throws
            IncorrectTakingGuidelinesException,
            ProductAlreadyInPrescriptionException {

        ensureEditing();

        createMedPrescriptionLine(prodID, instruc);
    }

    public void callDecisionMakingAI() throws AIException {

        ensureEditing();
        dmAI.initDecisionMakingAI();
        decisionMakingAIReady = true;
    }

    public void askAIForSuggest(String prompt) throws BadPromptException{

        ensureEditing();
        if (!decisionMakingAIReady) throw new ProceduralException("Decision making AI has not been started");

        aiAnswer = dmAI.getSuggestions(prompt);
        gotAISuggestions = true;
    }

    public void extractGuidelinesFromSugg() {

        ensureEditing();
        if (!gotAISuggestions) throw new ProceduralException("Decision making AI has not send any suggestions");

        dmAI.parseSuggest(aiAnswer);
    }

    public void modifyDoseInLine(ProductID prodID, float newDose) throws ProductNotInPrescriptionException {

        ensureEditing();
        mPresc.modifyDoseInLine(prodID, newDose);
    }

    public void removeLine(ProductID prodID) throws ProductNotInPrescriptionException {

        ensureEditing();
        mPresc.removeLine(prodID);
    }

    public void enterTreatmentEndingDate(Date date)
            throws IncorrectEndingDateException {

        ensureEditing();

        if (date == null) {
            throw new IncorrectEndingDateException("Ending date cannot be null");
        }

        Date today = new Date();

        // igual o anterior a hoy
        if (!date.after(today)) {
            throw new IncorrectEndingDateException(
                    "Ending date must be after current date"
            );
        }

        // demasiado próxima (menos de 24h)
        long oneDayMillis = 24L * 60L * 60L * 1000L;
        if (date.getTime() - today.getTime() < oneDayMillis) {
            throw new IncorrectEndingDateException(
                    "Ending date is too close to current date"
            );
        }

        setPrescDateAndEndDate(date);
    }

    public void finishMedicalPrescriptionEdition() {

        editingPrescription = false;
    }

    public void stampeESignature()
            throws eSignatureException {

        ensureEditing();

        if (!treatmentPeriodEstablished) {
            throw new ProceduralException(
                    "Medical prescription treatment period has not been established"
            );
        }

        if (eSign == null) {
            throw new eSignatureException(
                    "Digital signature has not been provided"
            );
        }

        mPresc.setESign(eSign);
        stampedESign = true;

    }

    public MedicalPrescription sendHistoryAndPrescription()
            throws ConnectException,
            HealthCardIDException,
            AnyCurrentPrescriptionException,
            NotCompletedMedicalPrescription {

        if (!editingPrescription || !treatmentPeriodEstablished || !stampedESign) {
            throw new ProceduralException(
                    "Medical prescription edition is not completed (editing, treatment period and electronic signature required)"
            );
        }

        hns.sendHistoryAndPrescription(cip, hce, illness, mPresc);
        return mPresc;
    }

    public void printMedicalPrescrip() throws printingException {
        //No implementation needed
    }


    // internal operations
    private void createMedPrescriptionLine(ProductID prodID, String[] instruc){
        mPresc.addLine(prodID, instruc);
    }

    private void setPrescDateAndEndDate(Date date) {

        mPresc.setPrescDate(new Date());
        mPresc.setEndDate(date);

        treatmentPeriodEstablished = true;
    }

    // Setter methods for injecting dependencies
    public void setHealthNationalService(HealthNationalService hns) {this.hns = hns;}

    public void setDecisionMakingAI(DecisionMakingAI dmAI){this.dmAI = dmAI;}

    public void setESignature(DigitalSignature sign) {this.eSign = sign;}

    // Refactoring extract method
    private void ensureEditing() throws ProceduralException {
        if (!editingPrescription) {
            throw new ProceduralException(ERR_NO_EDITION);
        }
    }

    private static final String ERR_NO_EDITION =
            "Medical prescription edition has not been started";


}