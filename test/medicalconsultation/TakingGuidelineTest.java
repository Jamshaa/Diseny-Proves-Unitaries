package medicalconsultation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TakingGuidelineTest {

    @Test
    void constructor_validValues_ok() {
        TakingGuideline tg = new TakingGuideline(DayMoment.BEFORELUNCH, 7.0f, 1.0f, 2.0f, FqUnit.DAY, "With water");

        assertNotNull(tg);
        assertEquals(DayMoment.BEFORELUNCH, tg.getdMoment());
        assertEquals(7.0f, tg.getDuration());
        assertEquals("With water", tg.getInstructions());
        assertNotNull(tg.getPosology());
    }

    @Test
    void constructor_nullInstructions_allowsOrThrows() {

        TakingGuideline tg =
                new TakingGuideline(DayMoment.BEFORELUNCH, 7.0f, 1.0f, 2.0f, FqUnit.DAY, null);

        assertNotNull(tg);
        assertNull(tg.getInstructions());
    }

    @Test
    void constructor_nullDayMoment_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(null, 7.0f, 1.0f, 2.0f, FqUnit.DAY, "x"));
    }

    @Test
    void constructor_negativeDuration_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(DayMoment.BEFORELUNCH, -1.0f, 1.0f, 2.0f, FqUnit.DAY, "x"));
    }

    @Test
    void constructor_nullUnit_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> new TakingGuideline(DayMoment.BEFORELUNCH, 7.0f, 1.0f, 2.0f, null, "x"));
    }

    @Test
    void constructor_nullInstructions_allowedBecauseOptional() {
        assertDoesNotThrow(() ->
                new TakingGuideline(DayMoment.BEFORELUNCH, 7.0f, 1.0f, 2.0f, FqUnit.DAY, null));
    }
}



