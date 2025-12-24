package medicalconsultation;

public class TakingGuideline { // Represents the taking guidelines for a medicine

    private DayMoment dMoment;
    private float duration;
    private Posology posology;
    private String instructions;

    // Initializes attributes
    public TakingGuideline(DayMoment dM, float du, float d, float f,
                           FqUnit fu, String i) {

        if (dM == null)
            throw new IllegalArgumentException("DayMoment no puede ser null");
        if (du <= 0)
            throw new IllegalArgumentException("Duration tiene que ser > 0");
        if (fu == null)
            throw new IllegalArgumentException("FqUnit no puede ser null");
        if (d <= 0)
            throw new IllegalArgumentException("Dose tiene que ser > 0");
        if (f <= 0)
            throw new IllegalArgumentException("Frequency tiene que ser > 0");

        this.dMoment = dM;
        this.duration = du;
        this.posology = new Posology(d, f, fu);
        this.instructions = i;
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