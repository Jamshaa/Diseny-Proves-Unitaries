package data;
import java.util.Arrays;
/**
 * Value Object para firma digital criptográfica del médico.
 * Immutable, final class que encapsula byte array de firma.
 * 
 * Formato: byte[] no vacío
 * 
 * Significado: Firma digital del médico. Se estampa en la prescripción
 * antes de ser enviada al SNS como comprobante de autenticación.
 * 
 * PATRÓN DEFENSIVO: getSignature() devuelve COPIA del array, no referencia.
 * Así cambios externos no afectan el estado interno.
 */
public final class DigitalSignature {
    private final byte[] signature;
    /**
     * Constructor con validación exhaustiva.
     * 
     * @param signature Byte array de firma digital (no null, no vacío)
     * @throws DigitalSignatureException Si signature es null o array vacío
     */
    public DigitalSignature(byte[] signature) throws DigitalSignatureException {
        if (signature == null) {
            throw new DigitalSignatureException("Signature cannot be null");
        }
        if (signature.length == 0) {
            throw new DigitalSignatureException("Signature cannot be empty");
        }
        // Hacer COPIA defensiva del array de entrada
        this.signature = signature.clone();
    }
    /**
     * Obtiene copia del byte array de firma (defensive copy).
     * 
     * Cambios en el array devuelto NO afectan el estado interno del objeto.
     * 
     * @return copia del byte array de firma
     */
    public byte[] getSignature() {
        // Devolver COPIA, no referencia al campo privado
        return signature.clone();
    }
    /**
     * Compara dos DigitalSignature por valor (contenido del byte array).
     * 
     * @param o Objeto a comparar
     * @return true si ambos tienen el mismo byte array
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DigitalSignature)) {
            return false;
        }
        DigitalSignature that = (DigitalSignature) o;
        // Usar Arrays.equals para comparar byte arrays
        return Arrays.equals(signature, that.signature);
    }
    /**
     * Calcula hash basado en contenido del byte array.
     * 
     * @return hash del byte array
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(signature);
    }
    /**
     * Representación en String legible.
     * 
     * @return "DigitalSignature{length=XX}"
     */
    @Override
    public String toString() {
        return "DigitalSignature{" +
               "length=" + signature.length +
               '}';
    }
}