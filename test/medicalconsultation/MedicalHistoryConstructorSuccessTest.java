package medicalconsultation;

import data.HealthCardID;
import medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicalHistoryConstructorSuccessTest {

    private HealthCardID cip;
    private int memberShipNum;

    @BeforeEach
    void setUp() {
        cip = new HealthCardID("ABCD123456789012");
        memberShipNum = 12345;
    }

    @Test
    void CorrectMedicalHistoryConstructorTest()
            throws IncorrectParametersException {

        MedicalHistory mh = new MedicalHistory(cip, memberShipNum);

        assertEquals(cip, mh.getCip());
        assertEquals(memberShipNum, mh.getMembShipNumb());
        assertEquals("", mh.getHistory());
    }
}