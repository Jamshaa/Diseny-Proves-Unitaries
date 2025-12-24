package medicalconsultation;

import data.HealthCardID;
import medicalconsultation.exceptions.IncorrectParametersException;
import medicalconsultation.interfaces.MedicalHistoryIntf;

public class MedicalHistory implements MedicalHistoryIntf { // A class that represents a medical history

    private HealthCardID cip;      // the CIP of the patient
    private int membShipNumb;      // the membership number of the family doctor
    private String history;        // the diverse annotations in the patient’s HCE

    public MedicalHistory(HealthCardID cip, int memberShipNum)
            throws IncorrectParametersException {
        // Makes its inicialization
        if (memberShipNum <= 0) {
            throw new IncorrectParametersException("memberShipNum must be > 0");
        }

        this.cip = cip;
        this.membShipNumb = memberShipNum;
        this.history = "";
    }

    // Adds new annotations to the patient history
    public void addMedicalHistoryAnnotations(String annot)
            throws IncorrectParametersException {

        if (annot == null) {
            throw new IncorrectParametersException("annotation cannot be null");
        } if (annot.trim().isEmpty()){
            throw new IncorrectParametersException("annotation cannot be empty");
        }

        if (history.isEmpty()) {
            history = annot;
        } else {
            history = history + "\n" + annot;
        }
    }

    // Modifies the family doctor for patient
    public void setNewDoctor(int mshN)
            throws IncorrectParametersException {

        if (mshN <= 0) {
            throw new IncorrectParametersException("membership number must be > 0");
        }
        this.membShipNumb = mshN;
    }

    // the getters
    public HealthCardID getCip() {
        return cip;
    }

    public int getMembShipNumb() {
        return membShipNumb;
    }

    public String getHistory() {
        return history;
    }
}