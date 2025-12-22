package data;
/**
 * Value Object para identificador de medicamento (Código UPC).
 * Immutable, final class que encapsula validación UPC.
 * 
 * Formato: 12 dígitos exactos [0-9]
 * 
 * Significado: Universal Product Code - Identificador único global de medicamento
 * Ejemplo: "123456789012"
 */
public final class ProductID {
    private final String code;
    /**
     * Constructor con validación exhaustiva.
     * 
     * @param code Código UPC de 12 dígitos
     * @throws ProductIDException Si code es null, longitud != 12, o contiene caracteres no numéricos
     */
    public ProductID(String code) throws ProductIDException {
        if (code == null) {
            throw new ProductIDException("ProductID code cannot be null");
        }
        if (code.length() != 12) {
            throw new ProductIDException("ProductID code must be exactly 12 digits");
        }
        if (!code.matches("^[0-9]+$")) {
            throw new ProductIDException("ProductID code must contain only digits (0-9)");
        }
        this.code = code;
    }
    /**
     * Obtiene el código UPC del medicamento.
     * 
     * @return código de 12 dígitos
     */
    public String getCode() {
        return code;
    }
    /**
     * Compara dos ProductID por valor.
     * 
     * @param o Objeto a comparar
     * @return true si ambos tienen el mismo código UPC
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductID)) {
            return false;
        }
        ProductID that = (ProductID) o;
        return code.equals(that.code);
    }
    /**
     * Calcula hash basado en el código UPC.
     * 
     * @return hash del código
     */
    @Override
    public int hashCode() {
        return code.hashCode();
    }
    /**
     * Representación en String legible.
     * 
     * @return "ProductID{code}"
     */
    @Override
    public String toString() {
        return "ProductID{" + code + '}';
    }
}