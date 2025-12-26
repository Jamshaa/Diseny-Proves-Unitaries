package interfaces;

import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import org.junit.jupiter.api.Test;

public interface MedicalPrescriptionTestInterface {
    @Test
    void addLineTest() throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException;
    // Checks the addLine(ProductID prodID, String[] instruc) method

    @Test
    void addLineEmptyInstructionsTest() throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException;

    @Test
    void settersTest();
    // Checks te setters methods
}
