package services; // Package for involved services

import data.HealthCardID;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import services.exceptions.HealthCardIDException;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;

public interface HealthNationalService {
    MedicalHistory getMedicalHistory (HealthCardID cip)
            throws ConnectException, HealthCardIDException;

    MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException,
                            AnyCurrentPrescriptionException;

    MedicalPrescription sendHistoryAndPrescription(HealthCardID cip,
                                                     MedicalHistory hce, String illness, MedicalPrescription mPresc)
             throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException, NotCompletedMedicalPrescription;

     // Internal operation

    MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc) throws ConnectException;
}