package medicalconsultation;

import data.HealthCardID;
import data.ProductID;
import data.ePrescripCode;
import data.DigitalSignature;
import medicalconsultation.exceptions.IncorrectTakingGuidelinesException;
import medicalconsultation.exceptions.ProductAlreadyInPrescriptionException;
import medicalconsultation.exceptions.ProductNotInPrescriptionException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MedicalPrescription { // Represents medical prescription

    private HealthCardID cip;            // the healthcard ID of the patient
    private int membShipNumb;            // the membership number of the family doctor
    private String illness;              // illness associated
    private ePrescripCode prescCode;     // the prescription code
    private Date prescDate;              // the current date
    private Date endDate;                // the date when the new treatment ends
    private DigitalSignature eSign;      // the eSignature of the doctor

    // Its components: the set of medical prescription lines
    private final Map<ProductID, TakingGuideline> lines;

    // Makes some initialization
    public MedicalPrescription(HealthCardID cip, int membShipNumb, String illness) {
        this.cip = cip;
        this.membShipNumb = membShipNumb;
        this.illness = illness;
        this.prescDate = new Date();
        this.lines = new HashMap<>();
    }

    /**
     * Adds a new medical prescription line
     */
    public void addLine(ProductID prodID, String[] instruc)
            throws ProductAlreadyInPrescriptionException,
            IncorrectTakingGuidelinesException {

        if (lines.containsKey(prodID)) {
            throw new ProductAlreadyInPrescriptionException(
                    "Product with ID " + prodID.getProductCode()
                            + " is already present in the medical prescription"
            );
        }

        try {
            DayMoment dm = DayMoment.valueOf(instruc[0]);
            float duration = Float.parseFloat(instruc[1]);
            float dose = Float.parseFloat(instruc[2]);
            float freq = Float.parseFloat(instruc[3]);
            FqUnit fu = FqUnit.valueOf(instruc[4]);
            String instructions = instruc.length > 5 ? instruc[5] : null;

            TakingGuideline tg =
                    new TakingGuideline(dm, duration, dose, freq, fu, instructions);

            lines.put(prodID, tg);

        } catch (Exception e) {
            throw new IncorrectTakingGuidelinesException(
                    "Incorrect or incomplete taking guidelines for product "
                            + prodID.getProductCode()
            );
        }
    }

    /**
     * Modifies dose in an existing prescription line
     */
    public void modifyDoseInLine(ProductID prodID, float newDose)
            throws ProductNotInPrescriptionException {

        TakingGuideline tg = lines.get(prodID);
        if (tg == null) {
            throw new ProductNotInPrescriptionException(
                    "Product with ID " + prodID.getProductCode()
                            + " is not present in the medical prescription"
            );
        }

        tg.getPosology().setDose(newDose);
    }

    /**
     * Removes a prescription line
     */
    public void removeLine(ProductID prodID)
            throws ProductNotInPrescriptionException {

        if (!lines.containsKey(prodID)) {
            throw new ProductNotInPrescriptionException(
                    "Product with ID " + prodID.getProductCode()
                            + " is not present in the medical prescription"
            );
        }

        lines.remove(prodID);
    }


    public HealthCardID getCip() {
        return cip;
    }

    public int getMembShipNumb() {
        return membShipNumb;
    }

    public String getIllness() {
        return illness;
    }

    public Map<ProductID, TakingGuideline> getLines() {
        return lines;
    }

    public void setPrescCode(ePrescripCode prescCode) {
        this.prescCode = prescCode;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setESign(DigitalSignature eSign) {
        this.eSign = eSign;
    }

    //for testing
    public Date getPrescDate(){return prescDate;}
}