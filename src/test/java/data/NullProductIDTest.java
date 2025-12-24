package data;

import org.junit.jupiter.api.Test;
import data.Exceptions.NullProductIDException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NullProductIDTest {

    @Test
    void NullProductIDExceptionTest() {
        assertThrows(NullProductIDException.class, () -> new ProductID(null));
    }
}