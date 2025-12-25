package medicalconsultation;

import data.DigitalSignature;
import data.HealthCardID;
import data.ProductID;
import data.ePrescripCode;
import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import medicalconsultation.exceptions.ProductNotInPrescriptionException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MedicalPrescription { // A class that represents medical prescription
    private HealthCardID cip; // the healthcard ID of the patient
    private int membShipNumb; // the membership number of the family doctor
    private String illness; // illness associated
    private ePrescripCode prescCode; // the prescription code
    private Date prescDate; // the current date
    private Date endDate; // the date when the new treatment ends
    private DigitalSignature eSign; // the eSignature of the doctor

    private Map<ProductID, MedicalPrescriptionLine> prescriptionLines; // Its components

    public MedicalPrescription(HealthCardID cip, int membShipNumb, String illness) {
        this.cip = cip;
        this.membShipNumb = membShipNumb;
        this.illness = illness;
        this.prescriptionLines = new HashMap<>();
    }

    public void addLine(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException, IncorrectTakingGuidelinesException {

        if (prescriptionLines.containsKey(prodID)) {
            throw new ProductAlreadyInPrescriptionException("Product " + prodID + " is already in the prescription.");
        }

        // Parse guidelines from instruc
        // instruc: [dayMoment, duration, dose, freq, freqUnit, instructions]
        // Example parsing logic (error handling omitted for brevity but exceptions
        // declared)
        try {
            if (instruc == null || instruc.length < 6) {
                throw new RuntimeException("Invalid instructions length");
            }

            dayMoment dMoment = dayMoment.valueOf(instruc[0]);
            float duration = Float.parseFloat(instruc[1]);
            float dose = Float.parseFloat(instruc[2]);
            float freq = Float.parseFloat(instruc[3]);
            FqUnit fqUnit = FqUnit.valueOf(instruc[4]);
            String instructions = instruc[5];

            TakingGuideline guideline = new TakingGuideline(dMoment, duration, dose, freq, fqUnit, instructions);
            MedicalPrescriptionLine line = new MedicalPrescriptionLine(prodID, guideline);

            prescriptionLines.put(prodID, line);

        } catch (Exception e) {
            throw new IncorrectTakingGuidelinesException("Failed to parse instructions: " + e.getMessage());
        }
    }

    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException {

        if (!prescriptionLines.containsKey(prodID)) {
            throw new ProductNotInPrescriptionException("Product " + prodID + " is not in the prescription.");
        }

        MedicalPrescriptionLine line = prescriptionLines.get(prodID);
        line.getTakingGuideline().getPosology().setDose(newDose);
    }

    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException {

        if (!prescriptionLines.containsKey(prodID)) {
            throw new ProductNotInPrescriptionException("Product " + prodID + " is not in the prescription.");
        }

        prescriptionLines.remove(prodID);
    }

    // Getters and Setters

    public HealthCardID getCip() {
        return cip;
    }

    public int getMembShipNumb() {
        return membShipNumb;
    }

    public String getIllness() {
        return illness;
    }

    public ePrescripCode getPrescCode() {
        return prescCode;
    }

    public void setPrescCode(ePrescripCode prescCode) {
        this.prescCode = prescCode;
    }

    public Date getPrescDate() {
        return prescDate;
    }

    public void setPrescDate(Date prescDate) {
        this.prescDate = prescDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public DigitalSignature geteSign() {
        return eSign;
    }

    public void seteSign(DigitalSignature eSign) {
        this.eSign = eSign;
    }

    public Map<ProductID, MedicalPrescriptionLine> getPrescriptionLines() {
        return prescriptionLines;
    }
}
