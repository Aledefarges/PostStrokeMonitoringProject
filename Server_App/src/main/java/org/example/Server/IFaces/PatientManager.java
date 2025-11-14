package org.example.Server.IFaces;

import org.example.POJOS.Patient;

import java.util.List;

public interface PatientManager {
    public void addPatient(Patient patient);
    public void deletePatient (Integer patient_id);
    public void deletePatientByEmail(String email);
    public List<Patient> getListOfPatients();
    public Patient getPatientById(Integer patient_id);
    public Patient getPatientByEmail(String email);
}

