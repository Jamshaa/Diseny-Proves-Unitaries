package data;

import org.junit.jupiter.api.Test;
import data.Exceptions.NullDigitalSignatureException;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NullDigitalSignatureTest {

    @Test
    void NullDigitalSignatureExceptionTest() {
        assertThrows(NullDigitalSignatureException.class, () -> new data.DigitalSignature(null));
    }
}