package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import data.DigitalSignature;
import medicalconsultation.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import services.HealthNationalService;
import services.DecisionMakingAI;
import services.exceptions.*;

import java.net.ConnectException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsultationTerminalTest {

    @Mock
    private HealthNationalService healthService;

    @Mock
    private DecisionMakingAI aiService;

    @InjectMocks
    private ConsultationTerminal terminal;

    private HealthCardID testCIP;
    private String testIllness = "TestIllness";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        testCIP = new HealthCardID("1234567890ABCDEF");
    }

    @Test
    void testHappyPathFlow() throws Exception {
        // Prepare mocks
        MedicalHistory mockHistory = mock(MedicalHistory.class);
        MedicalPrescription mockPrescription = mock(MedicalPrescription.class);

        when(healthService.getMedicalHistory(testCIP)).thenReturn(mockHistory);
        when(healthService.getMedicalPrescription(testCIP, testIllness)).thenReturn(mockPrescription);

        // 1. initRevision
        terminal.initRevision(testCIP, testIllness);
        verify(healthService).getMedicalHistory(testCIP);
        verify(healthService).getMedicalPrescription(testCIP, testIllness);

        // 2. initMedicalPrescriptionEdition
        terminal.initMedicalPrescriptionEdition();

        // 3. enterMedicineWithGuidelines
        ProductID prodID = new ProductID("123456789012");
        String[] instruc = { "BEFORELUNCH", "10", "1", "1", "DAY", "Test instructions" };
        terminal.enterMedicineWithGuidelines(prodID, instruc);
        verify(mockPrescription).addLine(prodID, instruc);

        // 4. enterTreatmentEndingDate
        Date futureDate = new Date(System.currentTimeMillis() + 86400000); // tomorrow
        terminal.enterTreatmentEndingDate(futureDate);
        verify(mockPrescription).setEndDate(futureDate);
        verify(mockPrescription).setPrescDate(any(Date.class));

        // 5. finishMedicalPrescriptionEdition
        terminal.finishMedicalPrescriptionEdition();

        // 6. stampeeSignature
        terminal.stampeeSignature();
        verify(mockPrescription).setESign(any(DigitalSignature.class));

        // 7. sendHistoryAndPrescription
        when(healthService.sendHistoryAndPrescription(eq(testCIP), eq(mockHistory), eq(testIllness),
                eq(mockPrescription)))
                .thenReturn(mockPrescription);
        MedicalPrescription result = terminal.sendHistoryAndPrescription();
        assertNotNull(result);
        verify(healthService).sendHistoryAndPrescription(eq(testCIP), eq(mockHistory), eq(testIllness),
                eq(mockPrescription));
    }

    @Test
    void testProceduralErrors() throws Exception {
        // Try to start edition without initiation
        assertThrows(ProceduralException.class, () -> terminal.initMedicalPrescriptionEdition());

        // Initiation
        when(healthService.getMedicalHistory(any())).thenReturn(mock(MedicalHistory.class));
        when(healthService.getMedicalPrescription(any(), any())).thenReturn(mock(MedicalPrescription.class));
        terminal.initRevision(testCIP, testIllness);

        // Try to sign without starting edition
        assertThrows(ProceduralException.class, () -> terminal.stampeeSignature());

        // Start edition
        terminal.initMedicalPrescriptionEdition();

        // Try to sign without setting period
        assertThrows(ProceduralException.class, () -> terminal.stampeeSignature());

        // Set period
        terminal.enterTreatmentEndingDate(new Date(System.currentTimeMillis() + 86400000));

        // Now should be able to sign
        assertDoesNotThrow(() -> terminal.stampeeSignature());

        // Try to edit again after signing? (Usually not allowed)
        assertThrows(ProceduralException.class,
                () -> terminal.enterMedicineWithGuidelines(new ProductID("111111111111"), new String[] {}));
    }

    @Test
    void testAIIntegration() throws Exception {
        terminal.initRevision(testCIP, testIllness);
        terminal.initMedicalPrescriptionEdition();

        terminal.callDecisionMakingAI();
        verify(aiService).initDecisionMakingAI();

        terminal.askAIForSuggest("test prompt");
        verify(aiService).getSuggestions("test prompt");

        terminal.extractGuidelinesFromSugg(); // Just verify state check passes
    }
}
