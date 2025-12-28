package medicalconsultation.consultationterminal;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import interfaces.ConsultationTerminalFlowTestInterface;
import interfaces.ConsultationTerminalProceduralTestInterface;
import medicalconsultation.ConsultationTerminal;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import mocks.AIStubHappyPath;
import mocks.HNSStubHappyPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewConsultationTerminalTest implements ConsultationTerminalFlowTestInterface, ConsultationTerminalProceduralTestInterface {

    private ConsultationTerminal ct;
    private HNSStubHappyPath hnsStub;
    private AIStubHappyPath aiStub;

    private HealthCardID cip;
    private int membShipNumb;
    private String illness;

    private MedicalHistory history;
    private MedicalPrescription prescription;

    @BeforeEach
    void setUp() {

        ct = new ConsultationTerminal();

        cip = new HealthCardID("ABCD123456789012");
        membShipNumb = 1234;
        illness = "Hypertension";

        history = new MedicalHistory(cip, membShipNumb);
        prescription = new MedicalPrescription(cip, membShipNumb, illness);

        hnsStub = new HNSStubHappyPath(history, prescription);
        ct.setHealthNationalService(hnsStub);

        aiStub = new AIStubHappyPath("AI_RESPONSE", List.of());
        ct.setDecisionMakingAI(aiStub);

        ct.setESignature(new DigitalSignature("SIGNED".getBytes()));
    }

    // FLOW TESTS

    @Override
    @Test
    public void initRevisionLoadsHistoryAndPrescriptionTest() throws Exception {

        ct.initRevision(cip, illness);
        assertNotNull(ct);
    }

    @Override
    @Test
    public void editPrescriptionAndSetEndingDateTest() throws Exception {

        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        ProductID pid = new ProductID("123456789012");
        String[] instr = {"BEFORELUNCH", "15", "1", "1", "DAY", "Tomar agua"};
        ct.enterMedicineWithGuidelines(pid, instr);

        Date end = new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(end);
    }

    @Override
    @Test
    public void stampESignatureAndSendHistoryAndPrescriptionTest() throws Exception {

        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        Date end = new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(end);

        ct.stampeESignature();

        MedicalPrescription returned = ct.sendHistoryAndPrescription();

        assertNotNull(returned);
        assertEquals(1, hnsStub.sendCalls);
    }
}
