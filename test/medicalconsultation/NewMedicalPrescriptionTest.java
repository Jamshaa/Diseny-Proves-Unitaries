package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import data.ePrescripCode;
import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;


import static org.junit.jupiter.api.Assertions.*;

public class NewMedicalPrescriptionTest {

    private MedicalPrescription mp;
    private HealthCardID cip;
    private int membShipNumb;

    @BeforeEach
    void setUp() {

        cip = new HealthCardID("ABCDE12345678901");
        membShipNumb = 1234;
        mp = new MedicalPrescription(cip, membShipNumb, "Hypertension");
    }

    @Test
    void MedicalPrescriptionCorrectlyCreatedTest() {

        assertEquals(cip, mp.getCip());
        assertEquals("Hypertension", mp.getIllness());
        assertEquals(membShipNumb, mp.getMembShipNumb());
    }

    @Test
    void addLineTest() throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException {

        String[] instruct = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};
        assertDoesNotThrow(() -> mp.addLine(new ProductID("123456789012"), instruct));

    }

    @Test
    void addLineEmptyInstructionsTest() throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException {

        String[] instruct = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        assertDoesNotThrow(() -> mp.addLine(new ProductID("123456789012"), instruct));

    }

    @Test
    void settersTest() {
        mp.setPrescCode(new ePrescripCode("EP12355555"));
        mp.setEndDate(new Date());
        mp.setESign(new DigitalSignature(new byte[]{1, 2, 3}));

        //Si no se produce ningún error
        assertTrue(true);
    }
}
