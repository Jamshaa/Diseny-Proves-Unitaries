package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import data.exceptions.HealthCardIDException;
import services.HealthNationalService;
import services.DecisionMakingAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConsultationTerminalTest {

    @Mock
    private HealthNationalService healthNationalService;

    @Mock
    private DecisionMakingAI decisionMakingAI;

    @InjectMocks
    private ConsultationTerminal terminal;

    private HealthCardID cip;
    private String illness = "Flu";
    private MedicalHistory mockHistory;
    private MedicalPrescription mockPrescription;

    @BeforeEach
    void setUp() throws Exception {
        cip = new HealthCardID("1234567890123456");
        mockHistory = new MedicalHistory(cip, 12345);
        mockPrescription = new MedicalPrescription(cip, 12345, illness);
    }

    @Test
    void testInitRevision() throws Exception {
        when(healthNationalService.getMedicalHistory(cip)).thenReturn(mockHistory);
        when(healthNationalService.getMedicalPrescription(cip, illness)).thenReturn(mockPrescription);

        terminal.initRevision(cip, illness);

        verify(healthNationalService).getMedicalHistory(cip);
        verify(healthNationalService).getMedicalPrescription(cip, illness);
    }

    @Test
    void testEnterMedicineWithGuidelines() throws Exception {
        // Init first
        when(healthNationalService.getMedicalHistory(cip)).thenReturn(mockHistory);
        when(healthNationalService.getMedicalPrescription(cip, illness)).thenReturn(mockPrescription);
        terminal.initRevision(cip, illness);

        ProductID pid = new ProductID("111111111111");
        String[] instructions = { "AFTERMEALS", "10", "1", "1", "DAY", "Take with water" };

        terminal.enterMedicineWithGuidelines(pid, instructions);

        assertTrue(mockPrescription.getPrescriptionLines().containsKey(pid));
    }

    @Test
    void testSendHistoryAndPrescription() throws Exception {
        // Init and setup
        when(healthNationalService.getMedicalHistory(cip)).thenReturn(mockHistory);
        when(healthNationalService.getMedicalPrescription(cip, illness)).thenReturn(mockPrescription);
        terminal.initRevision(cip, illness);

        // Stamp Signature
        // Needs end date first
        Date future = new Date(System.currentTimeMillis() + 10000000);
        terminal.enterTreatmentEndingDate(future);
        terminal.stampeeSignature();

        // Send
        when(healthNationalService.sendHistoryAndPrescription(eq(cip), any(), eq(illness), any()))
                .thenReturn(mockPrescription);

        terminal.sendHistoryAndPrescription();

        verify(healthNationalService).sendHistoryAndPrescription(eq(cip), any(), eq(illness), any());
    }
}
