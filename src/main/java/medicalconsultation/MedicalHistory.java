package medicalconsultation;

import data.HealthCardID;
import medicalconsultation.exceptions.IncorrectParametersException;

/**
 * Validates and manages the patient's medical history annotations.
 */
public class MedicalHistory {
    private HealthCardID cip;
    private int membShipNumb;
    private String history;

    public MedicalHistory(HealthCardID cip, int memberShipNum) throws IncorrectParametersException {
        if (cip == null) {
            throw new IncorrectParametersException("HealthCardID cannot be null");
        }
        // Additional checks could go here
        this.cip = cip;
        this.membShipNumb = memberShipNum;
        this.history = ""; // Initialize empty or as needed
    }

    public void addMedicalHistoryAnnotations(String annot) {
        if (annot != null && !annot.isEmpty()) {
            this.history += annot + "\n";
        }
    }

    public void setNewDoctor(int mshN) {
        this.membShipNumb = mshN;
    }

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
