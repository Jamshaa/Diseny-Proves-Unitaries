package medicalconsultation;

public class TakingGuideline { // Represents the taking guidelines for a medicine

    private DayMoment dMoment;
    private float duration;
    private Posology posology;
    private String instructions;

    // Initializes attributes
    public TakingGuideline(DayMoment dayMoment, float duration, float dose, float freq,
                           FqUnit fqUnit, String instruc) {

        this.dMoment = dayMoment;
        this.duration = duration;
        this.posology = new Posology(dose, freq, fqUnit);
        this.instructions = instruc;
    }

    // Getters and setters
    public DayMoment getdMoment() {
        return dMoment;
    }

    public void setdMoment(DayMoment dMoment) {
        this.dMoment = dMoment;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Posology getPosology() {
        return posology;
    }

    public void setPosology(Posology posology) {
        this.posology = posology;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}