package medicalconsultation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class TakingGuidelineInvalidTest {

    private DayMoment dm;
    private float duration;
    private float dose;
    private float freq;
    private FqUnit unit;

    @BeforeEach
    void setUp() {
        dm = DayMoment.BEFORELUNCH;
        duration = 7.0f;
        dose = 1.0f;
        freq = 2.0f;
        unit = FqUnit.DAY;
    }

    @Test
    void nullDayMoment_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(null, duration, dose, freq, unit, "x"));
    }

    @Test
    void zeroDuration_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(dm, 0.0f, dose, freq, unit, "x"));
    }

    @Test
    void negativeDose_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(dm, duration, -1.0f, freq, unit, "x"));
    }
}

