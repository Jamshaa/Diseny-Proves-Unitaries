package test.data;
import data.ProductID;
import data.ProductIDException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests exhaustivos para ProductID.
 * 
 * COBERTURA:
 * - Constructor: válido, null, longitud, solo dígitos
 * - equals(): sí/no, null, otra clase
 * - hashCode(): consistencia, HashMap
 * - toString(): legible
 * 
 * TOTAL: 12 tests
 */
@DisplayName("ProductID Tests")
class ProductIDTest {
    private static final String VALID_CODE = "123456789012";
    private static final String ANOTHER_VALID_CODE = "987654321098";
    @Nested
    @DisplayName("Constructor validation")
    class ConstructorTests {
        @Test
        @DisplayName("Constructor válido con código UPC correcto")
        void testConstructorValidWithValidUPC() throws ProductIDException {
            ProductID pid = new ProductID(VALID_CODE);
            assertEquals(VALID_CODE, pid.getCode());
            assertNotNull(pid);
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando code es null")
        void testConstructorThrowsExceptionWhenCodeIsNull() {
            assertThrows(ProductIDException.class, () -> new ProductID(null),
                    "ProductID debe lanzar excepción si code es null");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código tiene menos de 12 dígitos")
        void testConstructorThrowsExceptionWhenCodeTooShort() {
            String invalidCode = "12345678901";
            assertThrows(ProductIDException.class, () -> new ProductID(invalidCode),
                    "ProductID debe lanzar excepción si código < 12 dígitos");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código tiene más de 12 dígitos")
        void testConstructorThrowsExceptionWhenCodeTooLong() {
            String invalidCode = "1234567890123";
            assertThrows(ProductIDException.class, () -> new ProductID(invalidCode),
                    "ProductID debe lanzar excepción si código > 12 dígitos");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código contiene letras")
        void testConstructorThrowsExceptionWhenCodeHasLetters() {
            String invalidCode = "12345678901A";
            assertThrows(ProductIDException.class, () -> new ProductID(invalidCode),
                    "ProductID debe lanzar excepción si código contiene letras");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código contiene caracteres especiales")
        void testConstructorThrowsExceptionWhenCodeHasSpecialChars() {
            String invalidCode = "123456789012-";
            assertThrows(ProductIDException.class, () -> new ProductID(invalidCode),
                    "ProductID debe lanzar excepción si código contiene caracteres especiales");
        }
    }
    @Nested
    @DisplayName("equals() contract")
    class EqualsTests {
        @Test
        @DisplayName("equals() retorna true para dos ProductID con mismo código")
        void testEqualsReturnsTrueForSameCodes() throws ProductIDException {
            ProductID pid1 = new ProductID(VALID_CODE);
            ProductID pid2 = new ProductID(VALID_CODE);
            assertEquals(pid1, pid2, "Dos ProductID con mismo código deben ser iguales");
        }
        @Test
        @DisplayName("equals() retorna false para dos ProductID con códigos diferentes")
        void testEqualsReturnsFalseForDifferentCodes() throws ProductIDException {
            ProductID pid1 = new ProductID(VALID_CODE);
            ProductID pid2 = new ProductID(ANOTHER_VALID_CODE);
            assertNotEquals(pid1, pid2, "Dos ProductID con códigos diferentes NO deben ser iguales");
        }
        @Test
        @DisplayName("equals() retorna false cuando se compara con null")
        void testEqualsReturnsFalseWhenComparingWithNull() throws ProductIDException {
            ProductID pid = new ProductID(VALID_CODE);
            assertNotEquals(pid, null, "equals() debe retornar false al comparar con null");
        }
        @Test
        @DisplayName("equals() retorna false cuando se compara con objeto de otra clase")
        void testEqualsReturnsFalseWhenComparingWithDifferentClass() throws ProductIDException {
            ProductID pid = new ProductID(VALID_CODE);
            String other = VALID_CODE;
            assertNotEquals(pid, other, "equals() debe retornar false al comparar con otra clase");
        }
    }
    @Nested
    @DisplayName("hashCode() contract")
    class HashCodeTests {
        @Test
        @DisplayName("hashCode() retorna el mismo valor para dos ProductID iguales")
        void testHashCodeSameForEqualObjects() throws ProductIDException {
            ProductID pid1 = new ProductID(VALID_CODE);
            ProductID pid2 = new ProductID(VALID_CODE);
            assertEquals(pid1.hashCode(), pid2.hashCode(),
                    "hashCode() debe ser igual para objetos iguales");
        }
        @Test
        @DisplayName("hashCode() puede usarse en HashMap (clave única)")
        void testHashCodeCanBeUsedInHashMap() throws ProductIDException {
            ProductID pid1 = new ProductID(VALID_CODE);
            ProductID pid2 = new ProductID(VALID_CODE);
            java.util.Map<ProductID, String> map = new java.util.HashMap<>();
            map.put(pid1, "Medicamento 1");
            map.put(pid2, "Medicamento 2");
            assertEquals(1, map.size(), "HashMap debe contener solo 1 clave (no duplicados)");
            assertEquals("Medicamento 2", map.get(pid1), "La segunda inserción debe sobrescribir");
        }
    }
    @Nested
    @DisplayName("toString() representation")
    class ToStringTests {
        @Test
        @DisplayName("toString() retorna descripción readable")
        void testToStringReturnsReadableDescription() throws ProductIDException {
            ProductID pid = new ProductID(VALID_CODE);
            String str = pid.toString();
            assertTrue(str.contains("ProductID"), "toString() debe contener nombre de clase");
            assertTrue(str.contains(VALID_CODE), "toString() debe contener el código");
        }
    }
}