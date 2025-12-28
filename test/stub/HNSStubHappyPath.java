package mocks;

import data.HealthCardID;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import services.HealthNationalService;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;

public class HNSStubHappyPath implements HealthNationalService {

    private final MedicalHistory history;
    private final MedicalPrescription prescription;

    public int sendCalls = 0;

    public HNSStubHappyPath(MedicalHistory history, MedicalPrescription prescription) {
        this.history = history;
        this.prescription = prescription;
    }

    @Override
    public MedicalHistory getMedicalHistory(HealthCardID cip)
            throws ConnectException {
        return history;
    }

    @Override
    public MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, AnyCurrentPrescriptionException {
        return prescription;
    }

    @Override
    public MedicalPrescription sendHistoryAndPrescription(
            HealthCardID cip,
            medicalconsultation.MedicalHistory hce,
            String illness,
            MedicalPrescription mPresc)
            throws ConnectException, NotCompletedMedicalPrescription {

        sendCalls++;
        return mPresc;
    }

    @Override
    public MedicalPrescription generateTreatmCodeAndRegister(
            MedicalPrescription ePresc) throws ConnectException {
        return ePresc;
    }
}
