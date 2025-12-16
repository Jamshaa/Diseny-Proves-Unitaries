package data;

/**
 * Universal Product Code (UPC) for medicines.
 * Immutable value object representing a product identifier.
 */
public final class ProductID {

    private final String code;

    /**
     * Constructor for ProductID.
     * @param code the product UPC code (12 numeric digits)
     * @throws ProductIDException if code is null or malformed
     */
    public ProductID(String code) throws ProductIDException {
        if (code == null) {
            throw new ProductIDException("ProductID code cannot be null");
        }
        if (!isValidFormat(code)) {
            throw new ProductIDException(
                    "ProductID code must be 12 numeric digits"
            );
        }
        this.code = code;
    }

    private static boolean isValidFormat(String code) {
        return code.length() == 12 && code.matches("^[0-9]{12}$");
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID productID = (ProductID) o;
        return code.equals(productID.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "ProductID{code='" + code + "'}";
    }
}
