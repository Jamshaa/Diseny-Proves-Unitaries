package data;

import data.exceptions.InvalidHealthCardIDFormatException;
import data.exceptions.NullHealthCardIDException;

public final class HealthCardID {

    private final String personalID;

    public HealthCardID(String code)
            throws NullHealthCardIDException, InvalidHealthCardIDFormatException {

        if (code == null) {
            throw new NullHealthCardIDException(
                    "HealthCardID cannot be null"
            );
        }

        if (!code.matches("^[A-Za-z0-9]{16}$")) {
            throw new InvalidHealthCardIDFormatException(
                    "HealthCardID must contain exactly 16 alphanumeric characters"
            );
        }

        this.personalID = code;
    }

    public String getPersonalID() {
        return personalID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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