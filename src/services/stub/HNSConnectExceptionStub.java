package services.stub;

import data.HealthCardID;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import services.HealthNationalService;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;

public class HNSConnectExceptionStub implements HealthNationalService {

    @Override
    public MedicalHistory getMedicalHistory(HealthCardID cip) throws ConnectException {
        throw new ConnectException("Simulated connection error");
    }

    @Override
    public MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, AnyCurrentPrescriptionException {
        throw new ConnectException("Simulated connection error");
    }

    @Override
    public MedicalPrescription sendHistoryAndPrescription(HealthCardID cip,
                                                          medicalconsultation.MedicalHistory hce,
                                                          String illness,
                                                          MedicalPrescription mPresc)
            throws ConnectException, NotCompletedMedicalPrescription {
        throw new ConnectException("Simulated connection error");
    }

    @Override
    public MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc) throws ConnectException {
        throw new ConnectException("Simulated connection error");
    }
}
