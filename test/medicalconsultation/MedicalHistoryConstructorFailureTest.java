package medicalconsultation;

import data.HealthCardID;
import medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MedicalHistoryConstructorFailureTest {

    private HealthCardID validCip;

    @BeforeEach
    void setUp() {
        validCip = new HealthCardID("ABCD123456789012");
    }

    @Test
    void MemberShipNumIsZeroTest() {
        assertThrows(IncorrectParametersException.class, () ->
                new MedicalHistory(validCip, 0)
        );
    }

    @Test
    void MemberShipNumIsNegativeTest() {
        assertThrows(IncorrectParametersException.class, () ->
                new MedicalHistory(validCip, -7)
        );
    }
}