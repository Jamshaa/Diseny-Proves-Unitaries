package medicalconsultation.consultationterminal;

import data.HealthCardID;
import interfaces.ConsultationTerminalTestInterface;
import medicalconsultation.ConsultationTerminal;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import medicalconsultation.exceptions.IncorrectEndingDateException;
import medicalconsultation.exceptions.eSignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.stub.HealthNationalServiceStub;

import java.net.ConnectException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConsultationTerminalFailureTest implements ConsultationTerminalTestInterface {
    private ConsultationTerminal cT;
    private HealthCardID cip;
    private String illness;

    @BeforeEach
    void setUp(){

        cT = new ConsultationTerminal();
        cip = new HealthCardID("ABCD123456789012");
        int membShipNumb = 1234;
        illness = "Hypertension";

        MedicalHistory history = new MedicalHistory(cip, membShipNumb);
        MedicalPrescription prescription = new MedicalPrescription(cip, membShipNumb, illness);

        HealthNationalServiceStub hnsStub = new HealthNationalServiceStub(history, prescription);
        cT.setHealthNationalService(hnsStub);
    }

    @Test
    public void enterTreatmentEndingDateTest() throws ConnectException {

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        Date yesterday = new Date(System.currentTimeMillis() - 24L * 60 * 60 * 1000);
        Date tooCloseDate = new Date(System.currentTimeMillis() + 2L * 60 * 60 * 1000);

        assertThrows(IncorrectEndingDateException.class, ()-> cT.enterTreatmentEndingDate(null));
        assertThrows(IncorrectEndingDateException.class, ()-> cT.enterTreatmentEndingDate(yesterday));
        assertThrows(IncorrectEndingDateException.class, ()-> cT.enterTreatmentEndingDate(tooCloseDate));
    }

    @Test
    public void stampeESignatureTest() throws ConnectException{

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        Date endDate = new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000);
        cT.enterTreatmentEndingDate(endDate);


        assertThrows(eSignatureException.class, () -> cT.stampeESignature());
    }

}
