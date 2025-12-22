package data;
/**
 * Value Object para identificador de tarjeta sanitaria del paciente.
 * Immutable, final class que encapsula validación de CIP.
 * 
 * Formato: 16 caracteres alfanuméricos [A-Za-z0-9]
 * 
 * PRINCIPIOS APLICADOS:
 * - Value Object: No tiene identidad, solo valor
 * - Immutable: todos los campos final, sin setters
 * - Encapsulation: Validación centralizada en constructor
 * - No Primitive Obsession: String encapsulado en objeto tipado
 */
public final class HealthCardID {
    private final String personalID;
    /**
     * Constructor con validación exhaustiva.
     * 
     * @param code Código CIP de 16 caracteres alfanuméricos
     * @throws HealthCardIDException Si code es null, longitud != 16, o contiene caracteres inválidos
     */
    public HealthCardID(String code) throws HealthCardIDException {
        if (code == null) {
            throw new HealthCardIDException("HealthCardID code cannot be null");
        }
        if (code.length() != 16) {
            throw new HealthCardIDException("HealthCardID code must be exactly 16 characters");
        }
        if (!code.matches("^[A-Za-z0-9]+$")) {
            throw new HealthCardIDException("HealthCardID code must contain only alphanumeric characters (A-Z, a-z, 0-9)");
        }
        this.personalID = code;
    }
    /**
     * Obtiene el código personal de identificación.
     * 
     * @return el CIP de 16 caracteres
     */
    public String getPersonalID() {
        return personalID;
    }
    /**
     * Compara dos HealthCardID por valor (no por referencia).
     * 
     * CONTRATO: Reflexivo, Simétrico, Transitivo
     * 
     * @param o Objeto a comparar
     * @return true si ambos tienen el mismo CIP
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true; // Reflexivo
        }
        if (!(o instanceof HealthCardID)) {
            return false; // No es HealthCardID
        }
        HealthCardID that = (HealthCardID) o;
        return personalID.equals(that.personalID);
    }
    /**
     * Calcula hash basado en el valor del CIP.
     * 
     * CONTRATO: Si equals() == true, entonces hashCode() debe ser igual
     * 
     * @return hash del CIP
     */
    @Override
    public int hashCode() {
        return personalID.hashCode();
    }
    /**
     * Representación en String legible.
     * 
     * @return "HealthCardID{XXX...}"
     */
    @Override
    public String toString() {
        return "HealthCardID{" + personalID + '}';
    }
}