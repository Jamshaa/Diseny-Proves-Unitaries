package data;

import java.util.Arrays;

/**
 * Digital signature of a medical professional.
 * Immutable value object representing the physician's electronic signature.
 */
public final class DigitalSignature {

    private final byte[] signature;

    /**
     * Constructor for DigitalSignature.
     * @param signature the digital signature as a byte array
     * @throws DigitalSignatureException if signature is null or empty
     */
    public DigitalSignature(byte[] signature) throws DigitalSignatureException {
        if (signature == null || signature.length == 0) {
            throw new DigitalSignatureException(
                    "DigitalSignature cannot be null or empty"
            );
        }
        this.signature = signature.clone();
    }

    public byte[] getSignature() {
        return signature.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigitalSignature that = (DigitalSignature) o;
        return Arrays.equals(signature, that.signature);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(signature);
    }

    @Override
    public String toString() {
        return "DigitalSignature{signature=" + Arrays.toString(signature) + "}";
    }
}
