package interfaces;

import data.DigitalSignature;
import data.HealthCardID;
import medicalconsultation.ConsultationTerminal;
import medicalconsultation.exceptions.ProceduralException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface ConsultationTerminalProceduralTestInterface {

    @Test
    default void proceduralIfEnterMedicineBeforeInitEditionTest() {
        ConsultationTerminal ct = new ConsultationTerminal();
        assertThrows(ProceduralException.class,
                () -> ct.enterMedicineWithGuidelines(null, null));
    }

    @Test
    default void proceduralIfCallAIBeforeInitEditionTest() {
        ConsultationTerminal ct = new ConsultationTerminal();
        assertThrows(ProceduralException.class, ct::callDecisionMakingAI);
    }

    @Test
    default void proceduralIfStampBeforeEndingDateTest() {
        ConsultationTerminal ct = new ConsultationTerminal();
        ct.initMedicalPrescriptionEdition();
        ct.setESignature(new DigitalSignature("X".getBytes()));
        assertThrows(ProceduralException.class, ct::stampeESignature);
    }

    @Test
    default void notCompletedIfSendWithoutCompletionTest() {
        ConsultationTerminal ct = new ConsultationTerminal();
        assertThrows(services.exceptions.NotCompletedMedicalPrescription.class,
                ct::sendHistoryAndPrescription);
    }
}

