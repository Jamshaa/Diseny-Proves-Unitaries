package medicalconsultation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewPosologyTest {

    private Posology posology;

    @BeforeEach
    void setUp() {
        posology = new Posology(1.0f, 24.0f, FqUnit.HOUR);
    }

    @Test
    void posologyCorrectlyCreatedTest() {
        assertEquals(1.0f, posology.getDose(), 0.0001);
        assertEquals(24.0f, posology.getFreq(), 0.0001);
        assertEquals(FqUnit.HOUR, posology.getFreqUnit());
    }

    @Test
    void setDoseTest() {
        posology.setDose(2.5f);
        assertEquals(2.5f, posology.getDose(), 0.0001);
    }

    @Test
    void setFreqTest() {
        posology.setFreq(2.0f);
        assertEquals(2.0f, posology.getFreq(), 0.0001);
    }

    @Test
    void setFreqUnitTest() {
        posology.setFreqUnit(FqUnit.DAY);
        assertEquals(FqUnit.DAY, posology.getFreqUnit());
    }
}