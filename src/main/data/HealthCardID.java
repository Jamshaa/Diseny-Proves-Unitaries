package data;

import data.exceptions.HealthCardIDException;

/**
 * The personal identifying code in the National Health Service.
 */
final public class HealthCardID {

    private final String personalID;

    public HealthCardID(String code) throws HealthCardIDException {
        if (code == null) {
            throw new HealthCardIDException("HealthCardID cannot be null");
        }
        // pattern: 16 alphanumeric characters
        if (!code.matches("^[A-Za-z0-9]{16}$")) {
            throw new HealthCardIDException("HealthCardID must contain exactly 16 alphanumeric characters");
        }
        this.personalID = code;
    }

    public String getPersonalID() {
        return personalID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        HealthCardID hcardID = (HealthCardID) o;
        return personalID.equals(hcardID.personalID);
    }

    @Override
    public int hashCode() {
        return personalID.hashCode();
    }

    @Override
    public String toString() {
        return "HealthCardID{" + "personal code='" + personalID + '\'' + '}';
    }
}