package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import medicalconsultation.exceptions.ProductNotInPrescriptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalPrescriptionTest {

    private MedicalPrescription prescription;
    private ProductID product1;

    @BeforeEach
    public void setUp() throws Exception {
        HealthCardID id = new HealthCardID("1234567890123456");
        prescription = new MedicalPrescription(id, 12345, "Flu");
        product1 = new ProductID("111111111111");
    }

    @Test
    public void testAddLine() throws Exception {
        String[] instructions = { "AFTERMEALS", "10", "1", "1", "DAY", "Take with water" };
        prescription.addLine(product1, instructions);

        assertTrue(prescription.getPrescriptionLines().containsKey(product1));
        MedicalPrescriptionLine line = prescription.getPrescriptionLines().get(product1);
        assertEquals(1.0f, line.getTakingGuideline().getPosology().getDose());
    }

    @Test
    public void testAddLineDuplicate() throws Exception {
        String[] instructions = { "AFTERMEALS", "10", "1", "1", "DAY", "Take with water" };
        prescription.addLine(product1, instructions);

        assertThrows(ProductAlreadyInPrescriptionException.class, () -> {
            prescription.addLine(product1, instructions);
        });
    }

    @Test
    public void testRemoveLine() throws Exception {
        String[] instructions = { "AFTERMEALS", "10", "1", "1", "DAY", "Take with water" };
        prescription.addLine(product1, instructions);

        prescription.removeLine(product1);
        assertFalse(prescription.getPrescriptionLines().containsKey(product1));
    }

    @Test
    public void testRemoveLineNotFound() {
        assertThrows(ProductNotInPrescriptionException.class, () -> {
            prescription.removeLine(product1);
        });
    }
}
