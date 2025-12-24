package data;

import org.junit.jupiter.api.Test;
import data.exceptions.InvalidHealthCardIDFormatException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidHealthCardIDFormatTest {

    @Test
    public void InvalidHealthCardIDFormatExceptionLengthTest() {
        assertThrows(InvalidHealthCardIDFormatException.class, () -> new HealthCardID("ABC123"));
    }

    @Test
    public void InvalidHealthCardIDFormatExceptionNonAlphanumericTest() {
        assertThrows(InvalidHealthCardIDFormatException.class, () -> new HealthCardID("ABCD1234EFGH567!"));
    }
}