package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import data.ePrescripCode;
import interfaces.MedicalPrescriptionTestInterface;
import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import medicalconsultation.exceptions.ProductNotInPrescriptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static medicalconsultation.DayMoment.AFTERBREAKFAST;
import static org.junit.jupiter.api.Assertions.*;

public class AlreadyCreatedMedicalPrescriptionTest implements MedicalPrescriptionTestInterface {

    private MedicalPrescription mp;

    @BeforeEach
    public void setUp() {

        HealthCardID cip = new HealthCardID("ABCDE12345678901");
        int membShipNumb = 1234;
        mp = new MedicalPrescription(cip, membShipNumb, "Hypertension");
        String[] instruct = {"AFTERBREAKFAST", "12", "1", "1", "DAY", "Take with water"};
        mp.addLine(new ProductID("123456789012"), instruct);
    }

    @Test
    public void addLineTest() throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException {

        String[] instruct = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};
        assertDoesNotThrow(() -> mp.addLine(new ProductID("123453789012"), instruct));
    }

    @Test
    public void addLineEmptyInstructionsTest() throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException {

        String[] instruct = {"BEFORELUNCH", "15", "1", "1", "DAY"};
        assertDoesNotThrow(() -> mp.addLine(new ProductID("126456789012"), instruct));

    }

    @Test
    public void modifyDoseInLineTest() throws ProductNotInPrescriptionException{
        TakingGuideline tg = new TakingGuideline(AFTERBREAKFAST, 12, 2.00f, 1, FqUnit.DAY, "Take with water");
        mp.modifyDoseInLine(new ProductID("123456789012"), 2.00f);
        assertEquals(mp.getLines().get(new ProductID("123456789012")).getPosology().getDose(), tg.getPosology().getDose());
    }

    @Test
    public void removeLineTest() throws ProductNotInPrescriptionException{

        mp.removeLine(new ProductID("123456789012"));

        assertTrue(mp.getLines().isEmpty());
    }

    @Test
    public void settersTest() {
        mp.setPrescCode(new ePrescripCode("EP12355555"));
        mp.setEndDate(new Date());
        mp.setESign(new DigitalSignature(new byte[]{1, 2, 3}));

        //Si no se produce ningún error
        assertTrue(true);
    }
}
