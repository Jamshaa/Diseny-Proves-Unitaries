package services.stub;

import data.HealthCardID;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import services.HealthNationalService;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.HealthCardIDException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;

public class HNSHealthCardIDExceptionStub implements HealthNationalService {


    @Override
    public MedicalHistory getMedicalHistory(HealthCardID cip) {
        throw new HealthCardIDException("Simulated: prescription not completed");
    }

    @Override
    public MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, AnyCurrentPrescriptionException {
        return null;
    }

    @Override
    public MedicalPrescription sendHistoryAndPrescription(HealthCardID cip,
                                                          medicalconsultation.MedicalHistory hce,
                                                          String illness,
                                                          MedicalPrescription mPresc)
            throws ConnectException, NotCompletedMedicalPrescription, HealthCardIDException, AnyCurrentPrescriptionException {
        return null;
    }

    @Override
    public MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc) throws ConnectException {
        return null;
    }
}
