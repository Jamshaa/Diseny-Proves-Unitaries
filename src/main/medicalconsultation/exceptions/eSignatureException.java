package medicalconsultation.exceptions;

import data.exceptions.DigitalSignatureException;

public class eSignatureException extends Exception {
    public eSignatureException(String message, DigitalSignatureException e) {
        super(message);
    }
}
