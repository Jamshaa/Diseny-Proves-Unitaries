package test.data;
import data.DigitalSignature;
import data.DigitalSignatureException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests exhaustivos para DigitalSignature.
 * 
 * COBERTURA ESPECIAL:
 * - Constructor: válido, null, array vacío, defensive copy entrada
 * - getSignature(): defensive copy salida (cambios externos no afectan)
 * - equals(): comparación de contenido byte array
 * - hashCode(): consistencia con Arrays.hashCode()
 * - toString(): legible sin mostrar contenido
 * 
 * TOTAL: 11 tests
 * 
 * NOTA IMPORTANTE: Este test valida que los cambios al array devuelto
 * por getSignature() NO afecten el estado interno del objeto.
 * Este es el patrón "defensive copy" crítico para seguridad.
 */
@DisplayName("DigitalSignature Tests")
class DigitalSignatureTest {
    @Nested
    @DisplayName("Constructor validation")
    class ConstructorTests {
        @Test
        @DisplayName("Constructor válido con byte array no vacío")
        void testConstructorValidWithNonEmptyByteArray() throws DigitalSignatureException {
            byte[] validSignature = "firma_digital_valida".getBytes();
            DigitalSignature ds = new DigitalSignature(validSignature);
            assertNotNull(ds.getSignature());
            assertEquals(validSignature.length, ds.getSignature().length);
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando signature es null")
        void testConstructorThrowsExceptionWhenSignatureIsNull() {
            assertThrows(DigitalSignatureException.class, () -> new DigitalSignature(null),
                    "DigitalSignature debe lanzar excepción si signature es null");
        }
        @Test
        @DisplayName("Constructor lanza excepción cuando signature es array vacío")
        void testConstructorThrowsExceptionWhenSignatureIsEmpty() {
            byte[] emptySignature = new byte[0];
            assertThrows(DigitalSignatureException.class, () -> new DigitalSignature(emptySignature),
                    "DigitalSignature debe lanzar excepción si signature está vacío");
        }
    }
    @Nested
    @DisplayName("Defensive copy pattern")
    class DefensiveCopyTests {
        @Test
        @DisplayName("Constructor crea copia defensiva del array de entrada")
        void testConstructorCreatesDefensiveCopyOfInput() throws DigitalSignatureException {
            byte[] originalInput = "original_firma".getBytes();
            DigitalSignature ds = new DigitalSignature(originalInput);
            // Modificar el array original (después de pasar al constructor)
            originalInput[0] = (byte) 'X';
            // La firma interna NO debe cambiar
            assertEquals("original_firma".getBytes()[0], ds.getSignature()[0],
                    "Constructor debe crear copia defensiva: cambios al input no afectan el objeto");
        }
        @Test
        @DisplayName("getSignature() retorna copia del array (no referencia)")
        void testGetSignatureReturnsCopy() throws DigitalSignatureException {
            byte[] originalSignature = "original".getBytes();
            DigitalSignature ds = new DigitalSignature(originalSignature);
            byte[] retrieved = ds.getSignature();
            retrieved[0] = (byte) 'X';
            // El original dentro del objeto no debe cambiar
            assertEquals("original".getBytes()[0], ds.getSignature()[0],
                    "getSignature() debe retornar copia, no referencia: cambios al retorno no afectan el objeto");
        }
        @Test
        @DisplayName("Múltiples llamadas a getSignature() devuelven copias independientes")
        void testMultipleGetSignatureCallsReturnIndependentCopies() throws DigitalSignatureException {
            byte[] signature = "firma".getBytes();
            DigitalSignature ds = new DigitalSignature(signature);
            byte[] copy1 = ds.getSignature();
            byte[] copy2 = ds.getSignature();
            copy1[0] = (byte) 'X';
            // copy2 no debe ser afectado por cambios a copy1
            assertNotEquals(copy1[0], copy2[0],
                    "Cada llamada a getSignature() debe devolver copia independiente");
            // Y el objeto interno tampoco
            assertEquals("firma".getBytes()[0], ds.getSignature()[0],
                    "Cambios a ninguna copia deben afectar el objeto interno");
        }
    }
    @Nested
    @DisplayName("equals() contract")
    class EqualsTests {
        @Test
        @DisplayName("equals() retorna true para dos DigitalSignature con mismo contenido")
        void testEqualsReturnsTrueForSameContent() throws DigitalSignatureException {
            byte[] sig = "misma_firma".getBytes();
            DigitalSignature ds1 = new DigitalSignature(sig);
            DigitalSignature ds2 = new DigitalSignature(sig.clone());
            assertEquals(ds1, ds2, "Dos DigitalSignature con mismo contenido deben ser iguales");
        }
        @Test
        @DisplayName("equals() retorna false para dos DigitalSignature con diferente contenido")
        void testEqualsReturnsFalseForDifferentContent() throws DigitalSignatureException {
            DigitalSignature ds1 = new DigitalSignature("firma1".getBytes());
            DigitalSignature ds2 = new DigitalSignature("firma2".getBytes());
            assertNotEquals(ds1, ds2, "Dos DigitalSignature con diferente contenido NO deben ser iguales");
        }
        @Test
        @DisplayName("equals() retorna false cuando se compara con null")
        void testEqualsReturnsFalseWhenComparingWithNull() throws DigitalSignatureException {
            DigitalSignature ds = new DigitalSignature("firma".getBytes());
            assertNotEquals(ds, null, "equals() debe retornar false al comparar con null");
        }
        @Test
        @DisplayName("equals() retorna false cuando se compara con objeto de otra clase")
        void testEqualsReturnsFalseWhenComparingWithDifferentClass() throws DigitalSignatureException {
            DigitalSignature ds = new DigitalSignature("firma".getBytes());
            byte[] otherArray = "firma".getBytes();
            assertNotEquals(ds, otherArray, "equals() debe retornar false al comparar con otra clase");
        }
    }
    @Nested
    @DisplayName("hashCode() contract")
    class HashCodeTests {
        @Test
        @DisplayName("hashCode() retorna el mismo valor para dos DigitalSignature iguales")
        void testHashCodeSameForEqualObjects() throws DigitalSignatureException {
            byte[] sig = "misma_firma".getBytes();
            DigitalSignature ds1 = new DigitalSignature(sig);
            DigitalSignature ds2 = new DigitalSignature(sig.clone());
            assertEquals(ds1.hashCode(), ds2.hashCode(),
                    "hashCode() debe ser igual para objetos iguales");
        }
        @Test
        @DisplayName("hashCode() puede usarse en HashSet")
        void testHashCodeCanBeUsedInHashSet() throws DigitalSignatureException {
            byte[] sig = "firma".getBytes();
            DigitalSignature ds1 = new DigitalSignature(sig);
            DigitalSignature ds2 = new DigitalSignature(sig.clone());
            java.util.Set<DigitalSignature> set = new java.util.HashSet<>();
            set.add(ds1);
            set.add(ds2);
            assertEquals(1, set.size(), "HashSet debe contener solo 1 elemento (duplicados eliminados)");
        }
    }
    @Nested
    @DisplayName("toString() representation")
    class ToStringTests {
        @Test
        @DisplayName("toString() retorna descripción readable")
        void testToStringReturnsReadableDescription() throws DigitalSignatureException {
            byte[] sig = "test_signature".getBytes();
            DigitalSignature ds = new DigitalSignature(sig);
            String str = ds.toString();
            assertTrue(str.contains("DigitalSignature"), "toString() debe contener nombre de clase");
            assertFalse(str.isEmpty(), "toString() no debe estar vacío");
            assertTrue(str.contains("length"), "toString() debe mostrar longitud del array");
        }
    }
}