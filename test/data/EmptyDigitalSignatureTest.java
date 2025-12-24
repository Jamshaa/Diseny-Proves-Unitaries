package data;

import org.junit.jupiter.api.Test;
import data.exceptions.EmptyDigitalSignatureException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EmptyDigitalSignatureTest {

    @Test
    void EmptyDigitalSignatureExceptionTest() {
        assertThrows(EmptyDigitalSignatureException.class, () -> new data.DigitalSignature(new byte[0]));
    }
}
