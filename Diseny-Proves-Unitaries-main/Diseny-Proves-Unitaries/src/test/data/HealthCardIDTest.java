package test.data;
import data.HealthCardID;
import data.HealthCardIDException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests exhaustivos para HealthCardID.
 * 
 * COBERTURA:
 * - Constructor: válido, null, longitud, caracteres
 * - equals(): sí/no, null, otra clase, reflexivo
 * - hashCode(): consistencia, HashSet, HashMap
 * - toString(): contiene nombre clase y valor
 * 
 * TOTAL: 13 tests
 */
@DisplayName("HealthCardID Tests")
class HealthCardIDTest {
    private static final String VALID_CODE = "1234ABCD5678EFGH";
    private static final String ANOTHER_VALID_CODE = "HGFE8765DCBA4321";
    // ============= CONSTRUCTOR TESTS =============
    @Nested
    @DisplayName("Constructor validation")
    class ConstructorTests {
        @Test
        @DisplayName("Constructor válido con código correcto")
        void testConstructorValidWithValidCode() throws HealthCardIDException {
            HealthCardID hcid = new HealthCardID(VALID_CODE);
            assertEquals(VALID_CODE, hcid.getPersonalID());
            assertNotNull(hcid);
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando code es null")
        void testConstructorThrowsExceptionWhenCodeIsNull() {
            assertThrows(HealthCardIDException.class, () -> new HealthCardID(null),
                    "HealthCardID debe lanzar excepción si code es null");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código tiene menos de 16 caracteres")
        void testConstructorThrowsExceptionWhenCodeTooShort() {
            String invalidCode = "1234ABC";
            assertThrows(HealthCardIDException.class, () -> new HealthCardID(invalidCode),
                    "HealthCardID debe lanzar excepción si código < 16 caracteres");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código tiene más de 16 caracteres")
        void testConstructorThrowsExceptionWhenCodeTooLong() {
            String invalidCode = "1234ABCD5678EFGH12345";
            assertThrows(HealthCardIDException.class, () -> new HealthCardID(invalidCode),
                    "HealthCardID debe lanzar excepción si código > 16 caracteres");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código contiene caracteres especiales")
        void testConstructorThrowsExceptionWhenCodeHasSpecialChars() {
            String invalidCode = "1234ABCD5678EF@#";
            assertThrows(HealthCardIDException.class, () -> new HealthCardID(invalidCode),
                    "HealthCardID debe lanzar excepción si código contiene caracteres especiales");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando código contiene espacios")
        void testConstructorThrowsExceptionWhenCodeHasSpaces() {
            String invalidCode = "1234ABC D5678EFG";
            assertThrows(HealthCardIDException.class, () -> new HealthCardID(invalidCode),
                    "HealthCardID debe lanzar excepción si código contiene espacios");
        }
    }
    // ============= EQUALS TESTS =============
    @Nested
    @DisplayName("equals() contract")
    class EqualsTests {
        @Test
        @DisplayName("equals() retorna true para dos HealthCardID con mismo código")
        void testEqualsReturnsTrueForSameCodes() throws HealthCardIDException {
            HealthCardID hcid1 = new HealthCardID(VALID_CODE);
            HealthCardID hcid2 = new HealthCardID(VALID_CODE);
            assertEquals(hcid1, hcid2, "Dos HealthCardID con mismo código deben ser iguales");
        }
        @Test
        @DisplayName("equals() retorna false para dos HealthCardID con códigos diferentes")
        void testEqualsReturnsFalseForDifferentCodes() throws HealthCardIDException {
            HealthCardID hcid1 = new HealthCardID(VALID_CODE);
            HealthCardID hcid2 = new HealthCardID(ANOTHER_VALID_CODE);
            assertNotEquals(hcid1, hcid2, "Dos HealthCardID con códigos diferentes NO deben ser iguales");
        }
        @Test
        @DisplayName("equals() retorna false cuando se compara con null")
        void testEqualsReturnsFalseWhenComparingWithNull() throws HealthCardIDException {
            HealthCardID hcid = new HealthCardID(VALID_CODE);
            assertNotEquals(hcid, null, "equals() debe retornar false al comparar con null");
        }
        @Test
        @DisplayName("equals() retorna false cuando se compara con objeto de otra clase")
        void testEqualsReturnsFalseWhenComparingWithDifferentClass() throws HealthCardIDException {
            HealthCardID hcid = new HealthCardID(VALID_CODE);
            String other = VALID_CODE;
            assertNotEquals(hcid, other, "equals() debe retornar false al comparar con otra clase");
        }
        @Test
        @DisplayName("equals() es reflexivo: a.equals(a)")
        void testEqualsIsReflexive() throws HealthCardIDException {
            HealthCardID hcid = new HealthCardID(VALID_CODE);
            assertEquals(hcid, hcid, "equals() debe ser reflexivo");
        }
    }
    // ============= HASHCODE TESTS =============
    @Nested
    @DisplayName("hashCode() contract")
    class HashCodeTests {
        @Test
        @DisplayName("hashCode() retorna el mismo valor para dos HealthCardID iguales")
        void testHashCodeSameForEqualObjects() throws HealthCardIDException {
            HealthCardID hcid1 = new HealthCardID(VALID_CODE);
            HealthCardID hcid2 = new HealthCardID(VALID_CODE);
            assertEquals(hcid1.hashCode(), hcid2.hashCode(),
                    "hashCode() debe ser igual para objetos iguales");
        }
        @Test
        @DisplayName("hashCode() puede usarse en HashSet (no duplicados)")
        void testHashCodeCanBeUsedInHashSet() throws HealthCardIDException {
            HealthCardID hcid1 = new HealthCardID(VALID_CODE);
            HealthCardID hcid2 = new HealthCardID(VALID_CODE);
            java.util.Set<HealthCardID> set = new java.util.HashSet<>();
            set.add(hcid1);
            set.add(hcid2);
            assertEquals(1, set.size(), "HashSet debe contener solo 1 elemento (no duplicados)");
        }
    }
    // ============= TOSTRING TESTS =============
    @Nested
    @DisplayName("toString() representation")
    class ToStringTests {
        @Test
        @DisplayName("toString() retorna descripción readable")
        void testToStringReturnsReadableDescription() throws HealthCardIDException {
            HealthCardID hcid = new HealthCardID(VALID_CODE);
            String str = hcid.toString();
            assertTrue(str.contains("HealthCardID"), "toString() debe contener nombre de clase");
            assertTrue(str.contains(VALID_CODE), "toString() debe contener el código");
        }
    }
}