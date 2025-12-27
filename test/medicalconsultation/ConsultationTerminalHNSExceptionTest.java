package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import interfaces.ConsultationTerminalFlowTestInterface;
import mocks.HNSAnyCurrentPrescriptionExceptionStub;
import mocks.HNSConnectExceptionStub;
import mocks.HNSNotCompletedMedicalPrescriptionStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsultationTerminalHNSExceptionTest {

    private HealthCardID cip;
    private int membShipNumb;
    private String illness;

    private MedicalHistory history;
    private MedicalPrescription prescription;

    @BeforeEach
    void setUp() {
        cip = new HealthCardID("ABCD123456789012");
        membShipNumb = 1234;
        illness = "Hypertension";

        history = new MedicalHistory(cip, membShipNumb);
        prescription = new MedicalPrescription(cip, membShipNumb, illness);
    }

    @Test
    void initRevisionConnectExceptionTest() {
        ConsultationTerminal ct = new ConsultationTerminal();
        ct.setHealthNationalService(new HNSConnectExceptionStub());

        assertThrows(ConnectException.class, () -> ct.initRevision(cip, illness));
    }

    @Test
    void currentPrescriptionExceptionTest() {
        ConsultationTerminal ct = new ConsultationTerminal();
        ct.setHealthNationalService(new HNSAnyCurrentPrescriptionExceptionStub(history));

        assertThrows(AnyCurrentPrescriptionException.class, () -> ct.initRevision(cip, illness));
    }

    @Test
    void notCompletedPrescriptionTest() throws Exception {
        ConsultationTerminal ct = new ConsultationTerminal();
        ct.setHealthNationalService(new HNSNotCompletedMedicalPrescriptionStub(history, prescription));
        ct.setESignature(new DigitalSignature("SIGNED".getBytes()));

        ct.initRevision(cip, illness);
        ct.initMedicalPrescriptionEdition();

        // fija fecha fin para pasar el check interno (treatmentPeriodEstablished)
        Date end = new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000);
        ct.enterTreatmentEndingDate(end);

        assertThrows(NotCompletedMedicalPrescription.class, ct::sendHistoryAndPrescription);
    }
}
