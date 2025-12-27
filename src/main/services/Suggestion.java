package services;

import data.ProductID;

public class Suggestion {
    // According to the prompt: <I, 243516578917, BEFORELUNCH, 15, 1, 1, DAY, Tomar
    // con abundante agua>
    // It seems to contain everything needed for a prescription line
    // modification/addition.
    // Ideally this should map to the logic later.
    // But the prompt says "Definid vosotros la clase Suggestion".
    // I will store the raw fields or a structured object.

    // Let's use specific fields based on the example.
    private String operationType; // I, E, M
    private ProductID productID;
    private String dayMoment;
    private Float duration;
    private Float dose;
    private Float frequency;
    private String freqUnit;
    private String instructions;

    public Suggestion(String operationType, ProductID productID, String dayMoment, Float duration, Float dose,
            Float frequency, String freqUnit, String instructions) {
        this.operationType = operationType;
        this.productID = productID;
        this.dayMoment = dayMoment;
        this.duration = duration;
        this.dose = dose;
        this.frequency = frequency;
        this.freqUnit = freqUnit;
        this.instructions = instructions;
    }

    // Getters
    public String getOperationType() {
        return operationType;
    }

    public ProductID getProductID() {
        return productID;
    }

    public String getDayMoment() {
        return dayMoment;
    }

    public Float getDuration() {
        return duration;
    }

    public Float getDose() {
        return dose;
    }

    public Float getFrequency() {
        return frequency;
    }

    public String getFreqUnit() {
        return freqUnit;
    }

    public String getInstructions() {
        return instructions;
    }
}
