package org.example.Server.IFaces;


import org.example.POJOS.Doctor;

import java.util.List;

public interface DoctorManager {
   public void addDoctor(Doctor doctor);
   public void assingDoctorToPatient(Integer patient_id, Integer doctor_id);
   public void updatePassword(int doctor_id, String newPassword);
   public boolean checkPassword(String email, String password);
   public void updateEmail(int doctor_id, String newEmail);
   public Doctor searchDoctorById(int doctor_id);
   public List<Doctor> getAllDoctors();
   public Doctor getDoctorByEmail(String email);
}
