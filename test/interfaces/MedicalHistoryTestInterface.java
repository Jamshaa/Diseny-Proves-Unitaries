package interfaces;

import medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.Test;

public interface MedicalHistoryTestInterface {

    @Test
    void addMedicalHistoryAnnotationsTest() throws IncorrectParametersException;
    // Checks the addMedicalHistoryAnnotations(String annot) method

    @Test
    void setNewDoctorTest() throws IncorrectParametersException;
    // Checks the setNewDoctor(int mshN) method
}
