package medicalconsultation;

import data.HealthCardID;
import interfaces.MedicalHistoryTestInterface;
import medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicalHistoryAlreadyWithAnnotationsTest implements MedicalHistoryTestInterface {

    private MedicalHistory mh;

    @BeforeEach
    public void setUp() {
        HealthCardID cip = new HealthCardID("ABCD123456789012");
        int memberShipNum = 12345;
        mh = new MedicalHistory(cip, memberShipNum);
        mh.addMedicalHistoryAnnotations("First annotation");
    }

    @Test
    public void addMedicalHistoryAnnotationsTest() throws IncorrectParametersException{
        mh.addMedicalHistoryAnnotations("Second annotation");
        assertEquals("First annotation\nSecond annotation", mh.getHistory());
    }

    @Test public void setNewDoctorTest() throws IncorrectParametersException{
        mh.setNewDoctor(123554);
        assertEquals(123554, mh.getMembShipNumb());
    }

}