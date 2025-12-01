package org.example.Server.IFaces;



import org.example.POJOS.Patient;

import java.sql.Date;
import java.util.List;

public interface PatientManager {
    public void addPatient(Patient patient);
    public boolean deletePatient (String email);
    //public void deletePatientByEmail(String email);
    public List<Patient> getListOfPatients();
    public Patient getPatientById(int patient_id);
    public Patient getPatientByEmail(String email);
    public void updateName(int patient_id, String surname);
    public void updateSurName(int patient_id, String surname);
    public void updateEmail(int patient_id, String email);
    public void updateSex(int patient_id, Patient.Sex sex);
    public void updateDob(int patient_id, Date dob);
    public void updatePhone(int patient_id, int phone);
    public void updateMedicalHistory(int patient_id, String medicalHistory);
    public List<Patient> getListOfPatientsOfDoctor(Integer doctor_id);
    public void updatePassword(int patient_id, String newPassword);
    public boolean checkPassword(String email, String password);
    public String getMedicalHistoryById(int patient_id);
    public void addFeedback(String email, String feedback);
}

