package data;

/**
 * Electronic prescription code.
 * Immutable value object representing a prescription identifier.
 */
public final class ePrescripCode {

    private final String code;

    /**
     * Constructor for ePrescripCode.
     * @param code the prescription code (10 alphanumeric characters)
     * @throws ePrescripCodeException if code is null or malformed
     */
    public ePrescripCode(String code) throws ePrescripCodeException {
        if (code == null) {
            throw new ePrescripCodeException("ePrescripCode cannot be null");
        }
        if (!isValidFormat(code)) {
            throw new ePrescripCodeException(
                    "ePrescripCode must be 10 alphanumeric characters"
            );
        }
        this.code = code;
    }

    private static boolean isValidFormat(String code) {
        return code.length() == 10 && code.matches("^[a-zA-Z0-9]{10}$");
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ePrescripCode that = (ePrescripCode) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "ePrescripCode{code='" + code + "'}";
    }
}
