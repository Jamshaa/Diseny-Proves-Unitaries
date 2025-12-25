package data;

import data.exceptions.ProductIDException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductIDTest {

    @Test
    public void testValidConstruction() throws ProductIDException {
        ProductID id = new ProductID("123456789012");
        assertEquals("123456789012", id.getProductCode());
    }

    @Test
    public void testNullProductID() {
        assertThrows(ProductIDException.class, () -> new ProductID(null));
    }

    @Test
    public void testInvalidProductID() {
        assertThrows(ProductIDException.class, () -> new ProductID("abc"));
    }
}
