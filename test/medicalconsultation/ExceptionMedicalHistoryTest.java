package medicalconsultation;

import data.HealthCardID;
import medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionMedicalHistoryTest {

    private HealthCardID validCip;
    private MedicalHistory mh;

    @BeforeEach
    public void setUp() throws IncorrectParametersException {
        validCip = new HealthCardID("ABCD123456789012");
        mh = new MedicalHistory(validCip, 12345);
    }

    @Test
    public void constructorMemberShipNumIsZeroTest() {
        assertThrows(IncorrectParametersException.class, () ->
                new MedicalHistory(validCip, 0)
        );
    }

    @Test
    public void constructorMemberShipNumIsNegativeTest() {
        assertThrows(IncorrectParametersException.class, () ->
                new MedicalHistory(validCip, -5)
        );
    }

    @Test
    public void addNullAnnotationTest() {
        assertThrows(IncorrectParametersException.class, () ->
                mh.addMedicalHistoryAnnotations(null)
        );
    }

    @Test
    public void addEmptyAnnotationTest() {
        assertThrows(IncorrectParametersException.class, () ->
                mh.addMedicalHistoryAnnotations("")
        );
    }

    @Test
    public void addBlankAnnotationTest() {
        assertThrows(IncorrectParametersException.class, () ->
                mh.addMedicalHistoryAnnotations("   ")
        );
    }

    @Test
    public void setNewDoctorWhenMemberShipNumIsZeroTest() {
        assertThrows(IncorrectParametersException.class, () ->
                mh.setNewDoctor(0)
        );
    }

    @Test
    public void setNewDoctorWhenMemberShipNumIsNegativeTest() {
        assertThrows(IncorrectParametersException.class, () ->
                mh.setNewDoctor(-1)
        );
    }
}