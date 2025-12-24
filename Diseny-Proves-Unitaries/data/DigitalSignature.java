package data;

import data.exceptions.EmptyDigitalSignatureException;
import data.exceptions.NullDigitalSignatureException;

import java.util.Arrays;

public final class DigitalSignature {

    private final byte[] signature;

    public DigitalSignature(byte[] sign)
            throws NullDigitalSignatureException, EmptyDigitalSignatureException {

        if (sign == null) {
            throw new NullDigitalSignatureException(
                    "Digital signature cannot be null"
            );
        }

        if (sign.length == 0) {
            throw new EmptyDigitalSignatureException(
                    "Digital signature cannot be empty"
            );
        }

        this.signature = sign;
    }

    public byte[] getSignature() {
        return signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DigitalSignature)) return false;
        DigitalSignature that = (DigitalSignature) o;
        return Arrays.equals(signature, that.signature);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(signature);
    }

    @Override
    public String toString() {
        return "DigitalSignature{" + "signature='" + Arrays.toString(signature) + '\'' + '}';
    }
}