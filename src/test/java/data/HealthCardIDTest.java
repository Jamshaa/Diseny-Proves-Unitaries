package data;

import data.exceptions.HealthCardIDException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HealthCardIDTest {

    @Test
    public void testValidConstruction() throws HealthCardIDException {
        HealthCardID id = new HealthCardID("A123456789012345");
        assertEquals("A123456789012345", id.getPersonalID());
    }

    @Test
    public void testNullHealthCardID() {
        assertThrows(HealthCardIDException.class, () -> new HealthCardID(null));
    }

    @Test
    public void testInvalidLength() {
        assertThrows(HealthCardIDException.class, () -> new HealthCardID("A123"));
    }

    @Test
    public void testInvalidCharacters() {
        assertThrows(HealthCardIDException.class, () -> new HealthCardID("A123@#%6789012345"));
    }

    @Test
    public void testEqualsAndHashCode() throws HealthCardIDException {
        HealthCardID id1 = new HealthCardID("A123456789012345");
        HealthCardID id2 = new HealthCardID("A123456789012345");
        HealthCardID id3 = new HealthCardID("B123456789012345");

        assertEquals(id1, id2);
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1, id3);
    }
}
