package test.data;
import data.ePrescripCode;
import data.ePrescripCodeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests exhaustivos para ePrescripCode.
 * 
 * COBERTURA:
 * - Constructor: válido, null, longitud, alfanuméricos
 * - equals(): sí/no, null, otra clase
 * - hashCode(): consistencia, HashSet
 * - toString(): legible
 * 
 * TOTAL: 12 tests
 */
@DisplayName("ePrescripCode Tests")
class ePrescripCodeTest {
    private static final String VALID_CODE = "ABCD123456";
    private static final String ANOTHER_VALID_CODE = "XYZ9876543";
    @Nested
    @DisplayName("Constructor validation")
    class ConstructorTests {
        @Test
        @DisplayName("Constructor válido con código de 10 caracteres alfanuméricos")
        void testConstructorValidWithValidCode() throws ePrescripCodeException {
            ePrescripCode epc = new ePrescripCode(VALID_CODE);
            assertEquals(VALID_CODE, epc.getCode());
            assertNotNull(epc);
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando code es null")
        void testConstructorThrowsExceptionWhenCodeIsNull() {
            assertThrows(ePrescripCodeException.class, () -> new ePrescripCode(null),
                    "ePrescripCode debe lanzar excepción si code es null");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código tiene menos de 10 caracteres")
        void testConstructorThrowsExceptionWhenCodeTooShort() {
            String invalidCode = "ABC12345";
            assertThrows(ePrescripCodeException.class, () -> new ePrescripCode(invalidCode),
                    "ePrescripCode debe lanzar excepción si código < 10 caracteres");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código tiene más de 10 caracteres")
        void testConstructorThrowsExceptionWhenCodeTooLong() {
            String invalidCode = "ABCD12345678";
            assertThrows(ePrescripCodeException.class, () -> new ePrescripCode(invalidCode),
                    "ePrescripCode debe lanzar excepción si código > 10 caracteres");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código contiene caracteres especiales")
        void testConstructorThrowsExceptionWhenCodeHasSpecialChars() {
            String invalidCode = "ABCD12345@";
            assertThrows(ePrescripCodeException.class, () -> new ePrescripCode(invalidCode),
                    "ePrescripCode debe lanzar excepción si código contiene caracteres especiales");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código contiene espacios")
        void testConstructorThrowsExceptionWhenCodeHasSpaces() {
            String invalidCode = "ABCD 12345";
            assertThrows(ePrescripCodeException.class, () -> new ePrescripCode(invalidCode),
                    "ePrescripCode debe lanzar excepción si código contiene espacios");
        }
    }
    @Nested
    @DisplayName("equals() contract")
    class EqualsTests {
        @Test
        @DisplayName("equals() retorna true para dos ePrescripCode con mismo código")
        void testEqualsReturnsTrueForSameCodes() throws ePrescripCodeException {
            ePrescripCode epc1 = new ePrescripCode(VALID_CODE);
            ePrescripCode epc2 = new ePrescripCode(VALID_CODE);
            assertEquals(epc1, epc2, "Dos ePrescripCode con mismo código deben ser iguales");
        }
        @Test
        @DisplayName("equals() retorna false para dos ePrescripCode con códigos diferentes")
        void testEqualsReturnsFalseForDifferentCodes() throws ePrescripCodeException {
            ePrescripCode epc1 = new ePrescripCode(VALID_CODE);
            ePrescripCode epc2 = new ePrescripCode(ANOTHER_VALID_CODE);
            assertNotEquals(epc1, epc2, "Dos ePrescripCode con códigos diferentes NO deben ser iguales");
        }
        @Test
        @DisplayName("equals() retorna false cuando se compara con null")
        void testEqualsReturnsFalseWhenComparingWithNull() throws ePrescripCodeException {
            ePrescripCode epc = new ePrescripCode(VALID_CODE);
            assertNotEquals(epc, null, "equals() debe retornar false al comparar con null");
        }
        @Test
        @DisplayName("equals() retorna false cuando se compara con objeto de otra clase")
        void testEqualsReturnsFalseWhenComparingWithDifferentClass() throws ePrescripCodeException {
            ePrescripCode epc = new ePrescripCode(VALID_CODE);
            String other = VALID_CODE;
            assertNotEquals(epc, other, "equals() debe retornar false al comparar con otra clase");
        }
    }
    @Nested
    @DisplayName("hashCode() contract")
    class HashCodeTests {
        @Test
        @DisplayName("hashCode() retorna el mismo valor para dos ePrescripCode iguales")
        void testHashCodeSameForEqualObjects() throws ePrescripCodeException {
            ePrescripCode epc1 = new ePrescripCode(VALID_CODE);
            ePrescripCode epc2 = new ePrescripCode(VALID_CODE);
            assertEquals(epc1.hashCode(), epc2.hashCode(),
                    "hashCode() debe ser igual para objetos iguales");
        }
        @Test
        @DisplayName("hashCode() puede usarse en HashSet")
        void testHashCodeCanBeUsedInHashSet() throws ePrescripCodeException {
            ePrescripCode epc1 = new ePrescripCode(VALID_CODE);
            ePrescripCode epc2 = new ePrescripCode(VALID_CODE);
            java.util.Set<ePrescripCode> set = new java.util.HashSet<>();
            set.add(epc1);
            set.add(epc2);
            assertEquals(1, set.size(), "HashSet debe contener solo 1 elemento");
        }
    }
    @Nested
    @DisplayName("toString() representation")
    class ToStringTests {
        @Test
        @DisplayName("toString() retorna descripción readable")
        void testToStringReturnsReadableDescription() throws ePrescripCodeException {
            ePrescripCode epc = new ePrescripCode(VALID_CODE);
            String str = epc.toString();
            assertTrue(str.contains("ePrescripCode"), "toString() debe contener nombre de clase");
            assertTrue(str.contains(VALID_CODE), "toString() debe contener el código");
        }
    }
}