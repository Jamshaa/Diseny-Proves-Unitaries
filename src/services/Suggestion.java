package services;

import data.ProductID;

public final class Suggestion {

    public enum Type { I, E, M }

    private final Type type;
    private final ProductID productID;

    private final String  moment;       // BEFORELUNCH, AFTERDINNER, ...
    private final Integer duration;     // días
    private final Integer dose;         // unidades
    private final Integer interval;     // cada X
    private final String  intervalUnit; // DAY, WEEK, ...
    private final String  instructions; // texto libre

    public Suggestion(Type type, ProductID productID,
                      String moment, Integer duration, Integer dose,
                      Integer interval, String intervalUnit,
                      String instructions) {
        this.type = type;
        this.productID = productID;
        this.moment = moment;
        this.duration = duration;
        this.dose = dose;
        this.interval = interval;
        this.intervalUnit = intervalUnit;
        this.instructions = instructions;
    }

    public Type getType() { return type; }
    public ProductID getProductID() { return productID; }
    public String getMoment() { return moment; }
    public Integer getDuration() { return duration; }
    public Integer getDose() { return dose; }
    public Integer getInterval() { return interval; }
    public String getIntervalUnit() { return intervalUnit; }
    public String getInstructions() { return instructions; }
}