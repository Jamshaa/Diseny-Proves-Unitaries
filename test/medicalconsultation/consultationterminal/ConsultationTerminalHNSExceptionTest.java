package medicalconsultation.consultationterminal;

import data.DigitalSignature;
import data.HealthCardID;
import medicalconsultation.ConsultationTerminal;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import services.exceptions.HealthCardIDException;
import services.stub.HNSAnyCurrentPrescriptionExceptionStub;
import services.stub.HNSConnectExceptionStub;
import services.stub.HNSHealthCardIDExceptionStub;
import services.stub.HNSNotCompletedMedicalPrescriptionStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsultationTerminalHNSExceptionTest {

    private HealthCardID cip;
    private String illness;
    private ConsultationTerminal cT;

    private MedicalHistory history;
    private MedicalPrescription prescription;

    @BeforeEach
    void setUp() {
        cip = new HealthCardID("ABCD123456789012");
        int membShipNumb = 1234;
        illness = "Hypertension";
        cT = new ConsultationTerminal();

        history = new MedicalHistory(cip, membShipNumb);
        prescription = new MedicalPrescription(cip, membShipNumb, illness);
    }

    @Test
    void connectExceptionTest() {

        cT.setHealthNationalService(new HNSConnectExceptionStub());

        assertThrows(ConnectException.class, () -> cT.initRevision(cip, illness));
    }

    @Test
    void healthCardIDExceptionTest() {

        cT.setHealthNationalService(new HNSHealthCardIDExceptionStub());

        assertThrows(HealthCardIDException.class, () -> cT.initRevision(cip, illness));
    }

    @Test
    void anyCurrentPrescriptionExceptionTest() {

        cT.setHealthNationalService(new HNSAnyCurrentPrescriptionExceptionStub());

        assertThrows(AnyCurrentPrescriptionException.class, () -> cT.initRevision(cip, illness));
    }

    @Test
    void notCompletedMedicalPrescriptionExceptionTest() throws ConnectException {

        cT.setHealthNationalService(new HNSNotCompletedMedicalPrescriptionStub(history,prescription));

        cT.initRevision(cip, illness);
        cT.initMedicalPrescriptionEdition();

        // fija fecha fin para pasar el check interno (treatmentPeriodEstablished)
        Date end = new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000);
        cT.enterTreatmentEndingDate(end);

        cT.setESignature(new DigitalSignature("SIGNED".getBytes()));
        cT.stampeESignature();

        assertThrows(NotCompletedMedicalPrescription.class, ()-> cT.sendHistoryAndPrescription());
    }
}
