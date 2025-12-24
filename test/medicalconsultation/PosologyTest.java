package medicalconsultation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PosologyTest {

    @Test
    void constructor_validValues_ok() {
        Posology p = new Posology(1.0f, 2, FqUnit.DAY);
        assertNotNull(p);
    }

    @Test
    void constructor_negativeDose_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Posology(-1.0f, 2.0f, FqUnit.DAY));
    }

    @Test
    void constructor_zeroFrequency_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Posology(1.0f, 0.0f, FqUnit.DAY));
    }

    @Test
    void constructor_nullUnit_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Posology(1.0f, 2.0f, null));
    }


}

