package data;

import org.junit.jupiter.api.Test;
import data.exceptions.InvalidePrescripCodeFormatException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidePrescripCodeFormatTest {

    @Test
    public void InvalidePrescripCodeFormatExceptionWrongLengthTest() {
        assertThrows(InvalidePrescripCodeFormatException.class, () -> new data.ePrescripCode("ABC123"));
    }

    @Test
    public void InvalidePrescripCodeFormatExceptionLowercaseOrSymbolsTest() {
        assertThrows(InvalidePrescripCodeFormatException.class, () -> new data.ePrescripCode("ab12CD34!!"));
    }
}