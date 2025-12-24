package medicalconsultation.exceptions;

public class ProductAlreadyInPrescriptionException extends RuntimeException {
    public ProductAlreadyInPrescriptionException(String message) {
        super(message);
    }
}
