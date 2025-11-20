package org.example.Server.IFaces;


import org.example.POJOS.Doctor;

public interface DoctorManager {
   public void addDoctor(Doctor doctor);
   public void deleteDoctor(String email);
   public void assingDoctorToPatient(Integer patient_id, Integer doctor_id);
   public Doctor searchDoctorByEmail(String email);
}
