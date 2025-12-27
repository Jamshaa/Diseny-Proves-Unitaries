package services;

import data.HealthCardID;
import data.exceptions.HealthCardIDException;
import medicalconsultation.MedicalHistory;
import medicalconsultation.MedicalPrescription;
import services.exceptions.AnyCurrentPrescriptionException;
import services.exceptions.NotCompletedMedicalPrescription;

import java.net.ConnectException;

/**
 * External services for managing and storing ePrescriptions from population and
 * IA support
 */
public interface HealthNationalService {
    MedicalHistory getMedicalHistory(HealthCardID cip)
            throws ConnectException, HealthCardIDException;

    MedicalPrescription getMedicalPrescription(HealthCardID cip, String illness)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException;

    MedicalPrescription sendHistoryAndPrescription(HealthCardID cip, MedicalHistory hce, String illness,
            MedicalPrescription mPresc)
            throws ConnectException, HealthCardIDException, AnyCurrentPrescriptionException,
            NotCompletedMedicalPrescription;

    // Internal operation
    MedicalPrescription generateTreatmCodeAndRegister(MedicalPrescription ePresc)
            throws ConnectException;
}
