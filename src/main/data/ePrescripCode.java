package data;

import data.exceptions.ePrescripCodeException;

public final class ePrescripCode {

    private final String code;

    public ePrescripCode(String code) throws ePrescripCodeException {

        if (code == null) {
            throw new ePrescripCodeException(
                    "ePrescripCode cannot be null");
        }

        if (!code.matches("^[A-Z0-9]{10}$")) {
            throw new ePrescripCodeException(
                    "ePrescripCode must contain exactly 10 alphanumeric uppercase characters");
        }

        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ePrescripCode that = (ePrescripCode) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "ePrescripCode{" + "code='" + code + '\'' + '}';
    }
}