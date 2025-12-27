package mocks;

import data.HealthCardID;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import services.HealthNationalService;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;

public class HNSAnyCurrentPrescriptionExceptionStub implements HealthNationalService {

    private final MedicalHistory history;

    public HNSAnyCurrentPrescriptionExceptionStub(MedicalHistory history) {
        this.history = history;
    }

    @Override
    public MedicalHistory getMedicalHistory(HealthCardID cip) throws ConnectException {
        return history;
    }

    @Override
    public MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, AnyCurrentPrescriptionException {
        throw new AnyCurrentPrescriptionException("Simulated: no current prescription");
    }

    @Override
    public MedicalPrescription sendHistoryAndPrescription(HealthCardID cip,
                                                          medicalconsultation.MedicalHistory hce,
                                                          String illness,
                                                          MedicalPrescription mPresc)
            throws ConnectException, NotCompletedMedicalPrescription {
        return mPresc;
    }

    @Override
    public MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc) throws ConnectException {
        return ePresc;
    }
}
