package medicalconsultation;

import data.HealthCardID;
import medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewEmptyMedicalHistoryTest {

    private HealthCardID cip;
    private int memberShipNum;
    private MedicalHistory mh;

    @BeforeEach
    public void setUp() {
        cip = new HealthCardID("ABCD123456789012");
        memberShipNum = 12345;
        mh = new MedicalHistory(cip, memberShipNum);
    }

    @Test
    public void medicalHistoryCorrectlyCreatedTest() throws IncorrectParametersException {
        assertEquals(cip, mh.getCip());
        assertEquals(memberShipNum, mh.getMembShipNumb());
        assertEquals("", mh.getHistory());
    }

    @Test
    public void addMedicalHistoryAnnotationsTest() throws IncorrectParametersException{
        mh.addMedicalHistoryAnnotations("First annotation");
        assertEquals("First annotation", mh.getHistory());
    }


    @Test public void setNewDoctorTest() throws IncorrectParametersException{
        mh.setNewDoctor(123);
        assertEquals(123, mh.getMembShipNumb());
    }
}