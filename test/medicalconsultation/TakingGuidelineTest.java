package medicalconsultation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TakingGuidelineTest {

    private DayMoment dmOk;
    private float durationOk;
    private float doseOk;
    private float freqOk;
    private FqUnit unitOk;
    private String instrOk;

    @BeforeEach
    void setUp() {
        dmOk = DayMoment.BEFORELUNCH; // usa uno que exista en tu enum
        durationOk = 7.0f;
        doseOk = 1.0f;
        freqOk = 2.0f;
        unitOk = FqUnit.DAY;
        instrOk = "With water";
    }

    @Test
    void constructor_validValues_ok() {
        TakingGuideline tg = new TakingGuideline(dmOk, durationOk, doseOk, freqOk, unitOk, instrOk);
        assertNotNull(tg);
    }

    @Test
    void constructor_nullDayMoment_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(null, durationOk, doseOk, freqOk, unitOk, instrOk));
    }

    @Test
    void constructor_zeroDuration_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(dmOk, 0.0f, doseOk, freqOk, unitOk, instrOk));
    }

    @Test
    void constructor_nullUnit_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(dmOk, durationOk, doseOk, freqOk, null, instrOk));
    }

    @Test
    void constructor_negativeDose_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(dmOk, durationOk, -1.0f, freqOk, unitOk, instrOk));
    }

    @Test
    void constructor_zeroFrequency_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(dmOk, durationOk, doseOk, 0.0f, unitOk, instrOk));
    }

    @Test
    void constructor_nullInstructions_allowedBecauseOptional() {
        assertDoesNotThrow(() ->
                new TakingGuideline(dmOk, durationOk, doseOk, freqOk, unitOk, null));
    }
}
