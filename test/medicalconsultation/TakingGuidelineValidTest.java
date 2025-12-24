package medicalconsultation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TakingGuidelineValidTest {

    private DayMoment dm;
    private float duration;
    private float dose;
    private float freq;
    private FqUnit unit;
    private String instr;

    @BeforeEach
    void setUp() {
        dm = DayMoment.BEFORELUNCH;
        duration = 7.0f;
        dose = 1.0f;
        freq = 2.0f;
        unit = FqUnit.DAY;
        instr = "With water";
    }

    @Test
    void constructor_validValues_ok() {
        TakingGuideline tg =
                new TakingGuideline(dm, duration, dose, freq, unit, instr);

        assertNotNull(tg);
    }

    @Test
    void constructor_nullInstructions_allowed() {
        assertDoesNotThrow(() ->
                new TakingGuideline(dm, duration, dose, freq, unit, null));
    }
}

