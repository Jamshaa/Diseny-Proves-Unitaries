package interfaces;

import medicalconsultation.ConsultationTerminal;
import medicalconsultation.exceptions.ProceduralException;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface ConsultationTerminalTestInterface {

    @Test
    void enterTreatmentEndingDateTest() throws ConnectException;
    // Checks the enterTreatmentEndingDate(Date date) method

    @Test
    void stampeESignatureTest() throws ConnectException;
    // Checks the stampeESignature() method

    @Test
    default void eventOutOfOrderThrowsProceduralException() {
        ConsultationTerminal ct = new ConsultationTerminal();

        assertThrows(ProceduralException.class, () ->
                ct.enterMedicalAssessmentInHistory("some assessment")
        );
    }
}
