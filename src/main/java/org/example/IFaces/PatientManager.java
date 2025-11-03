package org.example.IFaces;

import org.example.POJOS.Patient;

public interface PatientManager {
    public void addPatient(Patient patient);
    public void deletePatient (Integer patient_id);
}

