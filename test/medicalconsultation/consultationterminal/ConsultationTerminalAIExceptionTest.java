package medicalconsultation.consultationterminal;

import data.DigitalSignature;
import data.HealthCardID;
import medicalconsultation.ConsultationTerminal;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import mocks.AIBadPromptStub;
import mocks.AIExceptionStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.AIException;
import services.exceptions.BadPromptException;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsultationTerminalAIExceptionTest {

    private ConsultationTerminal ct;
    private HealthCardID cip;
    private int membShipNumb;
    private String illness;

    private MedicalHistory history;
    private MedicalPrescription prescription;

    @BeforeEach
    void setUp() throws Exception {

        ct = new ConsultationTerminal();

        cip = new HealthCardID("ABCD123456789012");
        membShipNumb = 1234;
        illness = "Hypertension";

        history = new MedicalHistory(cip, membShipNumb);
        prescription = new MedicalPrescription(cip, membShipNumb, illness);

        ct.setHealthNationalService(
                new mocks.HNSStubHappyPath(history, prescription)
        );

        ct.setESignature(new DigitalSignature("SIGNED".getBytes()));

        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        Date end = new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(end);
    }

    @Test
    void callAIExceptionTest() {
        ct.setDecisionMakingAI(new AIExceptionStub());
        assertThrows(AIException.class, ct::callDecisionMakingAI);
    }

    @Test
    void badPromptTest() throws Exception {
        ct.setDecisionMakingAI(new AIBadPromptStub());

        ct.callDecisionMakingAI(); // init OK
        assertThrows(BadPromptException.class,
                () -> ct.askAIForSuggest("bad prompt"));
    }
}
