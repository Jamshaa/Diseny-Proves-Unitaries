package medicalconsultation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewTakingGuidelineTest {

    private TakingGuideline tg;

    @BeforeEach
    void setUp() {
        tg = new TakingGuideline(
                DayMoment.BEFORELUNCH,
                15.0f,
                1.0f,
                1.0f,
                FqUnit.DAY,
                "Take with water"
        );
    }

    @Test
    void takingGuidelineCorrectlyCreatedTest() {
        assertEquals(DayMoment.BEFORELUNCH, tg.getdMoment());
        assertEquals(15.0f, tg.getDuration(), 0.0001);
        assertEquals("Take with water", tg.getInstructions());

        assertNotNull(tg.getPosology());
        assertEquals(1.0f, tg.getPosology().getDose(), 0.0001);
        assertEquals(1.0f, tg.getPosology().getFreq(), 0.0001);
        assertEquals(FqUnit.DAY, tg.getPosology().getFreqUnit());
    }

    @Test
    void setDayMomentTest() {
        tg.setdMoment(DayMoment.AFTERDINNER);
        assertEquals(DayMoment.AFTERDINNER, tg.getdMoment());
    }

    @Test
    void setDurationTest() {
        tg.setDuration(30.0f);
        assertEquals(30.0f, tg.getDuration(), 0.0001);
    }

    @Test
    void setPosologyTest() {
        Posology newPosology = new Posology(3.0f, 2.0f, FqUnit.HOUR);
        tg.setPosology(newPosology);

        assertEquals(newPosology, tg.getPosology());
        assertEquals(3.0f, tg.getPosology().getDose(), 0.0001);
        assertEquals(2.0f, tg.getPosology().getFreq(), 0.0001);
        assertEquals(FqUnit.HOUR, tg.getPosology().getFreqUnit());
    }

    @Test
    void setInstructionsTest() {
        tg.setInstructions("New instructions");
        assertEquals("New instructions", tg.getInstructions());
    }
}