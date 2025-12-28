package services.stub;

import data.HealthCardID;
import data.ePrescripCode;
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
    public boolean sent = false;

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
        sent = true;
        generateTreatmCodeAndRegister(mPresc);
        return mPresc;
    }

    @Override
    public MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc) throws ConnectException {
        ePresc.setPrescCode(new ePrescripCode("ABC4567890"));
        return ePresc;
    }
}
