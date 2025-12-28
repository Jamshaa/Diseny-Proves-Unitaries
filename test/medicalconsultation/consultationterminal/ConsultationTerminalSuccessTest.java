package medicalconsultation.consultationterminal;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import interfaces.ConsultationTerminalTestInterface;
import medicalconsultation.*;
import medicalconsultation.exceptions.IncorrectEndingDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.stub.DecisionMakingAIStub;
import services.stub.HealthNationalServiceStub;

import javax.swing.*;
import java.net.ConnectException;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

public class ConsultationTerminalSuccessTest implements ConsultationTerminalTestInterface {
    private ConsultationTerminal cT;
    private HealthCardID cip;
    private String illness;
    private MedicalHistory history;
    private MedicalPrescription prescription;
    private HealthNationalServiceStub hnsStub;
    private DecisionMakingAIStub aiStub;

    @BeforeEach
    public void setUp(){

        cT = new ConsultationTerminal();

        cip = new HealthCardID("ABCD123456789012");
        int membShipNumb = 1234;
        illness = "Hypertension";

        history = new MedicalHistory(cip, membShipNumb);
        prescription = new MedicalPrescription(cip, membShipNumb, illness);

        hnsStub = new HealthNationalServiceStub(history, prescription);
        cT.setHealthNationalService(hnsStub);

        aiStub = new DecisionMakingAIStub();
        cT.setDecisionMakingAI(aiStub);

        cT.setESignature(new DigitalSignature("SIGNED".getBytes()));
    }

    @Test
    public void InitRevisionTest() {

        assertDoesNotThrow(() -> cT.initRevision(cip,illness));
    }

    @Test
    public void enterMedicalAssessmentInHistoryTest() throws ConnectException {

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        cT.enterMedicalAssessmentInHistory("assess");

        assertEquals("assess", history.getHistory());
    }

    @Test
    public void enterMedicineWithGuidelinesTest() throws ConnectException {

        String[] instruct = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};
        ProductID productID = new ProductID("123456789012");
        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();

        cT.enterMedicineWithGuidelines(productID, instruct);

        assertEquals(DayMoment.BEFORELUNCH,prescription.getLines().get(productID).getdMoment());
        assertEquals(15,prescription.getLines().get(productID).getDuration());
        assertEquals(1,prescription.getLines().get(productID).getPosology().getDose());
        assertEquals(1,prescription.getLines().get(productID).getPosology().getFreq());
        assertEquals(FqUnit.DAY,prescription.getLines().get(productID).getPosology().getFreqUnit());
        assertEquals("Take with water",prescription.getLines().get(productID).getInstructions());
    }

    @Test
    public void callDecisionMakingAITest() throws ConnectException {

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        cT.callDecisionMakingAI();

        assertTrue(aiStub.initAI);
    }

    @Test
    public void askAIForSuggestTest() throws ConnectException {

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        cT.callDecisionMakingAI();
        String prompt = "Review current treatment and suggest improvements for chronic patient";
        cT.askAIForSuggest(prompt);

        assertEquals(aiStub.aiAnswer, aiStub.getSuggestions(prompt));
    }

    @Test
    public void extractGuidelinesFromSuggTest() throws ConnectException{

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        cT.callDecisionMakingAI();
        cT.askAIForSuggest("");

        cT.extractGuidelinesFromSugg();
        assertTrue(aiStub.parseCalled);
        assertNotNull(aiStub.receivedAiAnswer);
    }

    @Test
    public void modifyDoseInLine() throws ConnectException{

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        String[] instruct = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};
        ProductID productID = new ProductID("123456789012");
        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();

        cT.enterMedicineWithGuidelines(productID, instruct);
        cT.modifyDoseInLine(productID, 14.00f);

        assertEquals(14.00f,prescription.getLines().get(productID).getPosology().getDose() );
    }

    @Test
    public void removeLineTest() throws ConnectException{

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        ProductID productID = new ProductID("123456789012");
        String[] instruct = {"BEFORELUNCH", "15", "1", "1", "DAY", "Take with water"};
        prescription.addLine(productID, instruct);
        cT.removeLine(productID);

        assertFalse(hnsStub.getMedicalPrescription(cip,illness).getLines().containsKey(productID));
    }

    @Test
    public void enterTreatmentEndingDateTest()
            throws IncorrectEndingDateException, ConnectException{

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        Date endDate = new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000);

        cT.enterTreatmentEndingDate(endDate);

        assertEquals(new Date(),prescription.getPrescDate());
        assertEquals(endDate, prescription.getEndDate());
    }

    @Test
    public void stampeESignatureTest() throws ConnectException{

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        Date endDate = new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000);
        cT.enterTreatmentEndingDate(endDate);
        DigitalSignature eSign = new DigitalSignature("SIGNATURE".getBytes());
        cT.setESignature(eSign);

        cT.stampeESignature();

        assertEquals(eSign, prescription.geteSign());
    }

    @Test
    public void sendHistoryAndPrescriptionTest() throws ConnectException{

        cT.initRevision(cip,illness);
        cT.initMedicalPrescriptionEdition();
        Date endDate = new Date(System.currentTimeMillis() + 2L * 24 * 60 * 60 * 1000);
        cT.enterTreatmentEndingDate(endDate);
        DigitalSignature eSign = new DigitalSignature("SIGNATURE".getBytes());
        cT.setESignature(eSign);
        cT.stampeESignature();

        assertEquals(prescription, cT.sendHistoryAndPrescription());
        assertTrue(hnsStub.sent);


    }
}
