package data;

import java.util.Arrays;

public final class DigitalSignature {

    private final byte[] signature;

    public DigitalSignature(byte[] sign) {
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
        return "DigitalSignature{signature length=" +
                (signature != null ? signature.length : 0) + '}';
    }
}
