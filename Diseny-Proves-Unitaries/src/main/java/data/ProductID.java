package data;

public final class ProductID {

    private final String productCode;

    public ProductID(String code) {
        this.productCode = code;
    }

    public String getProductCode() {
        return productCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductID productID = (ProductID) o;
        return productCode.equals(productID.productCode);
    }

    @Override
    public int hashCode() {
        return productCode.hashCode();
    }

    @Override
    public String toString() {
        return "ProductID{" + "productCode='" + productCode + '\'' + '}';
    }
}
