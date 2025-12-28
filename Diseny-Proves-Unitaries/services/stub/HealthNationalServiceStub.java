package services.stub;

import data.HealthCardID;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import services.HealthNationalService;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.HealthCardIDException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;

public class HealthNationalServiceStub implements HealthNationalService {
    private final MedicalHistory history;
    private final MedicalPrescription prescription;

    public HealthNationalServiceStub(MedicalHistory history, MedicalPrescription prescription) {
        this.history = history;
        this.prescription = prescription;
    }

    @Override
    public MedicalHistory getMedicalHistory(HealthCardID cip) throws ConnectException, HealthCardIDException {
        return history;
    }

    @Override
    public MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness) throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException {
        return prescription ;
    }

    @Override
    public MedicalPrescription sendHistoryAndPrescription(HealthCardID cip, MedicalHistory hce, String illness, MedicalPrescription mPresc) throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException, NotCompletedMedicalPrescription {
        return null;
    }

    @Override
    public MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc) throws ConnectException {
        return null;
    }
}
