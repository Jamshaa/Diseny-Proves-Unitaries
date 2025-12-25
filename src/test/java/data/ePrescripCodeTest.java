package data;

import data.exceptions.ePrescripCodeException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ePrescripCodeTest {

    @Test
    public void testValidConstruction() throws ePrescripCodeException {
        ePrescripCode code = new ePrescripCode("ABC1234567");
        assertEquals("ABC1234567", code.getCode());
    }

    @Test
    public void testNullCode() {
        assertThrows(ePrescripCodeException.class, () -> new ePrescripCode(null));
    }

    @Test
    public void testInvalidCode() {
        assertThrows(ePrescripCodeException.class, () -> new ePrescripCode("Lower12345"));
    }
}
