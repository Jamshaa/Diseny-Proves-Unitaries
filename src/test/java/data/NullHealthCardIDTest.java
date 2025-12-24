package data;

import org.junit.jupiter.api.Test;
import data.Exceptions.NullHealthCardIDException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NullHealthCardIDTest {

    @Test
    public void NullHealthCardIDExceptionTest() {
        assertThrows(NullHealthCardIDException.class, () -> new HealthCardID(null));
    }
}