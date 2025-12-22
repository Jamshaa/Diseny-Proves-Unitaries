package medicalconsultation;
import data.HealthCardID;
/**
 * Package for the classes involved in the use case Supervise treatment
 */
public class MedicalHistory { // A class that represents a medical history
    private HealthCardID cip;  // the CIP of the patient
    private int membShipNumb;  // the membership number of the family doctor
    private String history;   // the diverse annotations in the patient’s HCE
    public MedicalHistory (HealthCardID cip, int memberShipNum) { }
    public void addMedicalHistoryAnnotations (String annot){}
    public void setNewDoctor (int mshN) {}
}
