package data;
/**
 * Value Object para código de prescripción electrónica.
 * Immutable, final class.
 * 
 * Formato: 10 caracteres alfanuméricos [A-Za-z0-9]
 * 
 * Significado: Código único de prescripción generado por SNS.
 * Se asigna cuando la prescripción se registra en el sistema remoto.
 */
public final class ePrescripCode {
    private final String code;
    /**
     * Constructor con validación exhaustiva.
     * 
     * @param code Código de prescripción de 10 caracteres alfanuméricos
     * @throws ePrescripCodeException Si code es null, longitud != 10, o contiene caracteres inválidos
     */
    public ePrescripCode(String code) throws ePrescripCodeException {
        if (code == null) {
            throw new ePrescripCodeException("ePrescripCode cannot be null");
        }
        if (code.length() != 10) {
            throw new ePrescripCodeException("ePrescripCode must be exactly 10 characters");
        }
        if (!code.matches("^[A-Za-z0-9]+$")) {
            throw new ePrescripCodeException("ePrescripCode must contain only alphanumeric characters");
        }
        this.code = code;
    }
    /**
     * Obtiene el código de prescripción.
     * 
     * @return código de 10 caracteres
     */
    public String getCode() {
        return code;
    }
    /**
     * Compara dos ePrescripCode por valor.
     * 
     * @param o Objeto a comparar
     * @return true si ambos tienen el mismo código
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ePrescripCode)) {
            return false;
        }
        ePrescripCode that = (ePrescripCode) o;
        return code.equals(that.code);
    }
    /**
     * Calcula hash basado en el código.
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
     * @return "ePrescripCode{code}"
     */
    @Override
    public String toString() {
        return "ePrescripCode{" + code + '}';
    }
}