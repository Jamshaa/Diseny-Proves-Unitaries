package medicalconsultation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PosologyTest {

    private float doseOk;
    private float freqOk;
    private FqUnit unitOk;

    @BeforeEach
    void setUp() {
        doseOk = 1.0f;
        freqOk = 2.0f;
        unitOk = FqUnit.DAY;
    }

    @Test
    void constructor_validValues_ok() {
        Posology p = new Posology(doseOk, freqOk, unitOk);
        assertNotNull(p);
    }

    @Test
    void constructor_negativeDose_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Posology(-1.0f, freqOk, unitOk));
    }

    @Test
    void constructor_zeroFrequency_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Posology(doseOk, 0.0f, unitOk));
    }

    @Test
    void constructor_nullUnit_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Posology(doseOk, freqOk, null));
    }
}
