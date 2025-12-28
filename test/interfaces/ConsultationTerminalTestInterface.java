package interfaces;

import medicalconsultation.ConsultationTerminal;
import medicalconsultation.exceptions.ProceduralException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface ConsultationTerminalTestInterface {

    //metodos test repetidos en escenario success y failure

    @Test
    default void eventOutOfOrderThrowsProceduralException() {
        ConsultationTerminal ct = new ConsultationTerminal();

        assertThrows(ProceduralException.class, () ->
                ct.enterMedicalAssessmentInHistory("some assessment")
        );
    }
}
