package data;

import org.junit.jupiter.api.Test;
import data.exceptions.NullProductIDException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NullProductIDTest {

    @Test
    public void NullProductIDExceptionTest() {
        assertThrows(NullProductIDException.class, () -> new ProductID(null));
    }
}