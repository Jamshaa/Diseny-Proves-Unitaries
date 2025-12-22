package data;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DigitalSignature Tests")
class DigitalSignatureTest {

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

    @Test
    @DisplayName("getSignature() retorna copia del array (defensive copy)")
    void testGetSignatureReturnsCopy() throws DigitalSignatureException {
        byte[] originalSignature = "original".getBytes();
        DigitalSignature ds = new DigitalSignature(originalSignature);

        byte[] retrieved = ds.getSignature();
        retrieved[0] = (byte) 'X';

        // El original dentro del objeto no debe cambiar
        assertEquals("original".getBytes()[0], ds.getSignature()[0],
                "getSignature() debe retornar copia, no referencia");
    }

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

        assertEquals(1, set.size(), "HashSet debe contener solo 1 elemento");
    }

    @Test
    @DisplayName("toString() retorna descripción readable")
    void testToStringReturnsReadableDescription() throws DigitalSignatureException {
        byte[] sig = "test_signature".getBytes();
        DigitalSignature ds = new DigitalSignature(sig);
        String str = ds.toString();

        assertTrue(str.contains("DigitalSignature"), "toString() debe contener nombre de clase");
        assertFalse(str.isEmpty(), "toString() no debe estar vacío");
    }
}