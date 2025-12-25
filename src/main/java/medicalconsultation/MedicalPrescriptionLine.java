package medicalconsultation;

import data.ProductID;

public class MedicalPrescriptionLine {
    private ProductID productID;
    private TakingGuideline takingGuideline;

    public MedicalPrescriptionLine(ProductID productID, TakingGuideline takingGuideline) {
        this.productID = productID;
        this.takingGuideline = takingGuideline;
    }

    public ProductID getProductID() {
        return productID;
    }

    public TakingGuideline getTakingGuideline() {
        return takingGuideline;
    }

    public void setTakingGuideline(TakingGuideline takingGuideline) {
        this.takingGuideline = takingGuideline;
    }
}
