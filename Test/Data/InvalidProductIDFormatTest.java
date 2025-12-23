package data;

import org.junit.jupiter.api.Test;
import data.Exceptions.InvalidProductIDFormatException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidProductIDFormatTest {

    @Test
    void InvalidProductIDFormatLengthTest() {
        assertThrows(InvalidProductIDFormatException.class, () -> new ProductID("123"));
    }

    @Test
    void InvalidProductIDFormatNonDigitsTest() {
        assertThrows(InvalidProductIDFormatException.class, () -> new ProductID("12345678901A"));
    }
}