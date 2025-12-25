package medicalconsultation.medicalhistory;

import data.HealthCardID;
import medicalconsultation.MedicalHistory;
import medicalconsultation.exceptions.IncorrectParametersException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicalHistoryAddAnnotationSuccessTest {

    private MedicalHistory mh;

    @BeforeEach
    void setUp() throws IncorrectParametersException {
        HealthCardID cip = new HealthCardID("ABCD123456789012");
        mh = new MedicalHistory(cip, 12345);
    }

    @Test
    void addFirstAnnotation_storesAnnotation() throws IncorrectParametersException {
        mh.addMedicalHistoryAnnotations("Patient shows improvement");

        assertEquals("Patient shows improvement", mh.getHistory());
    }

    @Test
    void addSecondAnnotation_appendsWithNewLine() throws IncorrectParametersException {
        mh.addMedicalHistoryAnnotations("First annotation");
        mh.addMedicalHistoryAnnotations("Second annotation");

        assertEquals(
                "First annotation\nSecond annotation",
                mh.getHistory()
        );
    }
}