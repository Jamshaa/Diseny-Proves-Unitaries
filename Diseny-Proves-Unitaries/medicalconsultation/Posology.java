package medicalconsultation;

public class Posology { // A class that represents the posology of a medicine

    private float dose;
    private float freq;
    private FqUnit freqUnit;

    // Initializes attributes
    public Posology(float d, float f, FqUnit u) {
        if (u == null)
            throw new IllegalArgumentException("FqUnit no puede ser null");
        if (d <= 0)
            throw new IllegalArgumentException("Dosi tiene que ser > 0");
        if (f <= 0)
            throw new IllegalArgumentException("Frecuencia tiene que ser > 0");

        this.dose = d;
        this.freq = f;
        this.freqUnit = u;
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