package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import medicalconsultation.exceptions.ProductNotInPrescriptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionMedicalPrescriptionTest {

    private MedicalPrescription mp;

    @BeforeEach
    void setUp() {
        HealthCardID cip = new HealthCardID("ABCD123456789012");
        mp = new MedicalPrescription(cip, 12345, "Hypertension");
    }

    @Test
    void addLineProductAlreadyInPrescriptionTest() throws ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException {
        ProductID pid = new ProductID("243516578917");
        String[] instruc = {"BEFORELUNCH", "15", "1", "1", "DAY"};

        mp.addLine(pid, instruc);

        assertThrows(ProductAlreadyInPrescriptionException.class, () ->
                mp.addLine(pid, instruc)
        );
    }

    @Test
    void addLineIncorrectTakingGuidelinesTest() throws  ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException{
        ProductID pid = new ProductID("243516578917");
        String[] badInstruc = {"NOTADAYMOMENT", "x", "y", "z", "NOTAFQUNIT"};

        assertThrows(IncorrectTakingGuidelinesException.class, () ->
                mp.addLine(pid, badInstruc)
        );
    }

    @Test
    void addLineIncompleteTakingGuidelinesTest() throws IncorrectTakingGuidelinesException {
        ProductID pid = new ProductID("243516578917");
        // Falta FqUnit (instruc[4]) => ArrayIndexOutOfBounds dentro del try/catch
        String[] incompleteInstruc = {"BEFORELUNCH", "15", "1", "1"};

        assertThrows(IncorrectTakingGuidelinesException.class, () ->
                mp.addLine(pid, incompleteInstruc)
        );
    }

    @Test
    void modifyDoseInLineProductNotInPrescriptionTest() throws ProductNotInPrescriptionException{
        ProductID pid = new ProductID("640557143200");

        assertThrows(ProductNotInPrescriptionException.class, () ->
                mp.modifyDoseInLine(pid, 3.0f)
        );
    }

    @Test
    void removeLineProductNotInPrescriptionTest() throws ProductNotInPrescriptionException {
        ProductID pid = new ProductID("640557143200");

        assertThrows(ProductNotInPrescriptionException.class, () ->
                mp.removeLine(pid)
        );
    }
}