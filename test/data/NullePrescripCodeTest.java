package data;

import org.junit.jupiter.api.Test;
import data.exceptions.NullePrescripCodeException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NullePrescripCodeTest {

    @Test
    void NullePrescripCodeExceptionTest() {
        assertThrows(NullePrescripCodeException.class, () -> new data.ePrescripCode(null));
    }
}