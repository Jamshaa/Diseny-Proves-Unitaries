package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import data.exceptions.HealthCardIDException;
import medicalconsultation.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.DecisionMakingAI;
import services.HealthNationalService;
import services.exceptions.*;

import java.net.ConnectException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultationTerminalTest {

    private ConsultationTerminal terminal;

    @Mock
    private HealthNationalService hns;

    @Mock
    private DecisionMakingAI ai;

    @BeforeEach
    void setUp() {
        terminal = new ConsultationTerminal();
        terminal.setHealthNationalService(hns);
        terminal.setDecisionMakingAI(ai);
    }

    @Test
    void testProceduralExceptionWhenNotInitialized() {
        assertThrows(ProceduralException.class, () -> terminal.initMedicalPrescriptionEdition());
    }

    @Test
    void testHappyPathSequence() throws Exception {
        HealthCardID cip = new HealthCardID("1234567890ABCDEF");
        String illness = "Flu";

        MedicalHistory history = mock(MedicalHistory.class);
        MedicalPrescription prescription = mock(MedicalPrescription.class);

        when(hns.getMedicalHistory(cip)).thenReturn(history);
        when(hns.getMedicalPrescription(cip, illness)).thenReturn(prescription);

        // 1. initRevision
        terminal.initRevision(cip, illness);

        // 2. initMedicalPrescriptionEdition
        terminal.initMedicalPrescriptionEdition();

        // 3. enterMedicineWithGuidelines
        String[] instruc = { "BEFORELUNCH", "15", "1", "1", "DAY", "With water" };
        ProductID prodID = new ProductID("PROD123");
        terminal.enterMedicineWithGuidelines(prodID, instruc);
        verify(prescription).addLine(prodID, instruc);

        // 4. enterTreatmentEndingDate
        Date futureDate = new Date(System.currentTimeMillis() + 1000000);
        terminal.enterTreatmentEndingDate(futureDate);
        verify(prescription).setEndDate(any(Date.class));

        // 5. stampeeSignature
        terminal.stampeeSignature();

        // 6. sendHistoryAndPrescription
        when(hns.sendHistoryAndPrescription(eq(cip), eq(history), eq(illness), eq(prescription)))
                .thenReturn(prescription);

        MedicalPrescription result = terminal.sendHistoryAndPrescription();
        assertNotNull(result);
        verify(hns).sendHistoryAndPrescription(eq(cip), eq(history), eq(illness), eq(prescription));
    }

    @Test
    void testAIIntegration() throws Exception {
        HealthCardID cip = new HealthCardID("ABCDEF1234567890");
        String illness = "Diabetes";
        when(hns.getMedicalHistory(cip)).thenReturn(mock(MedicalHistory.class));
        when(hns.getMedicalPrescription(cip, illness)).thenReturn(mock(MedicalPrescription.class));

        terminal.initRevision(cip, illness);
        terminal.initMedicalPrescriptionEdition();

        terminal.callDecisionMakingAI();
        verify(ai).initDecisionMakingAI();

        terminal.askAIForSuggest("What is the best treatment?");
        verify(ai).getSuggestions("What is the best treatment?");

        terminal.extractGuidelinesFromSugg();
    }

    @Test
    void testProceduralErrors() throws Exception {
        HealthCardID cip = new HealthCardID("1111222233334444");
        String illness = "Cold";

        // Cannot enter assessment before edition starts
        terminal.initRevision(cip, illness);
        assertThrows(ProceduralException.class, () -> terminal.enterMedicalAssessmentInHistory("Patient feels weak"));

        // Cannot sign before edition starts
        assertThrows(ProceduralException.class, () -> terminal.stampeeSignature());

        // Cannot send before sign
        terminal.initMedicalPrescriptionEdition();
        assertThrows(ProceduralException.class, () -> terminal.sendHistoryAndPrescription());
    }
}
