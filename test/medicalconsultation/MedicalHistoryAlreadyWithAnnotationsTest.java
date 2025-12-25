package medicalconsultation;

import data.HealthCardID;
import medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MedicalHistoryAlreadyWithAnnotationsTest {

    private HealthCardID cip;
    private int memberShipNum;
    private MedicalHistory mh;

    @BeforeEach
    public void setUp() {
        cip = new HealthCardID("ABCD123456789012");
        memberShipNum = 12345;
        mh = new MedicalHistory(cip, memberShipNum);
        mh.addMedicalHistoryAnnotations("First annotation");
    }

    @Test
    public void addMedicalHistoryAnnotationsTest() throws IncorrectParametersException{
        mh.addMedicalHistoryAnnotations("Second annotation");
        assertEquals("First annotation\nSecond annotation", mh.getHistory());
    }

}