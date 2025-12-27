package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import data.exceptions.HealthCardIDException;
import medicalconsultation.exceptions.ProceduralException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ConsultationTerminalPreconditionsTest {

    private ConsultationTerminal terminal;

    @BeforeEach
    void setUp() {
        terminal = new ConsultationTerminal(null, null);
    }

    @Test
    void initRevision_withoutHNS_throwsProceduralException() {
        // tu código valida primero que exista el servicio
        assertThrows(ProceduralException.class,
                () -> terminal.initRevision(null, "flu"));
    }

    @Test
    void initRevision_blankIllness_throwsIllegalArgumentException_orProceduralDepends() throws Exception {
        assertThrows(ProceduralException.class,
                () -> terminal.initRevision(new HealthCardID("1234567890ABCDEF"), "   "));
    }

    @Test
    void initRevision_invalidCipFormat_throwsHealthCardIDException() {
        // Aquí comprobamos la validación del VO HealthCardID (no ConsultationTerminal)
        assertThrows(HealthCardIDException.class,
                () -> new HealthCardID("123")); // no 16 chars
    }

    @Test
    void enterMedicalAssessment_beforeInitRevision_throwsProceduralException() {
        assertThrows(ProceduralException.class,
                () -> terminal.enterMedicalAssessmentInHistory("Assessment"));
    }

    @Test
    void enterMedicine_beforeInitRevision_throwsProceduralException() throws Exception {
        assertThrows(ProceduralException.class,
                () -> terminal.enterMedicineWithGuidelines(
                        new ProductID("111111111111"),
                        new String[]{"AFTERLUNCH","10","1","2","DAY","ok"}
                ));
    }

    @Test
    void enterTreatmentEndingDate_beforeInitRevision_throwsProceduralException() {
        assertThrows(ProceduralException.class,
                () -> terminal.enterTreatmentEndingDate(new Date()));
    }

    @Test
    void stampeeSignature_beforeInitRevision_throwsProceduralException() {
        assertThrows(ProceduralException.class, terminal::stampeeSignature);
    }
}
