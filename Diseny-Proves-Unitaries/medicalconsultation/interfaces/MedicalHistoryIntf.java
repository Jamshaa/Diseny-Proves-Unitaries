package medicalconsultation.interfaces;

import data.HealthCardID;
import medicalconsultation.exceptions.IncorrectParametersException;

public interface MedicalHistoryIntf {

    // Adds new annotations to the patient history
    void addMedicalHistoryAnnotations(String annot)
            throws IncorrectParametersException;

    // Modifies the family doctor for patient
    void setNewDoctor(int mshN)
            throws IncorrectParametersException;

    // Getters
    HealthCardID getCip();

    int getMembShipNumb();

    String getHistory();
}