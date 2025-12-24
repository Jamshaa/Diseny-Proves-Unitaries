package data;

import org.junit.jupiter.api.Test;
import data.exceptions.InvalidePrescripCodeFormatException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidePrescripCodeFormatTest {

    @Test
    void constructor_throwsInvalidePrescripCodeFormatException_whenWrongLength() {
        assertThrows(InvalidePrescripCodeFormatException.class, () -> new data.ePrescripCode("ABC123"));
    }

    @Test
    void constructor_throwsInvalidePrescripCodeFormatException_whenHasLowercaseOrSymbols() {
        assertThrows(InvalidePrescripCodeFormatException.class, () -> new data.ePrescripCode("ab12CD34!!"));
    }
}