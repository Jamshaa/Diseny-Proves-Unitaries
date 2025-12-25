package data;

import data.exceptions.DigitalSignatureException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DigitalSignatureTest {

    @Test
    public void testValidConstruction() throws DigitalSignatureException {
        byte[] bytes = { 1, 2, 3 };
        DigitalSignature signature = new DigitalSignature(bytes);
        assertArrayEquals(bytes, signature.getSignature());
    }

    @Test
    public void testNullSignature() {
        assertThrows(DigitalSignatureException.class, () -> new DigitalSignature(null));
    }

    @Test
    public void testEmptySignature() {
        assertThrows(DigitalSignatureException.class, () -> new DigitalSignature(new byte[0]));
    }
}
