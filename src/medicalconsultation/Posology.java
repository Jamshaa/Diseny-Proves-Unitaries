package medicalconsultation;

public class Posology { // A class that represents the posology of a medicine

    private float dose;
    private float freq;
    private FqUnit freqUnit;

    // Initializes attributes
    public Posology(float dose, float freq, FqUnit freqUnit) {
        this.dose = dose;
        this.freq = freq;
        this.freqUnit = freqUnit;
    }

    // Getters and setters
    public float getDose() {
        return dose;
    }

    public void setDose(float dose) {
        this.dose = dose;
    }

    public float getFreq() {
        return freq;
    }

    public void setFreq(float freq) {
        this.freq = freq;
    }

    public FqUnit getFreqUnit() {
        return freqUnit;
    }

    public void setFreqUnit(FqUnit freqUnit) {
        this.freqUnit = freqUnit;
    }
}