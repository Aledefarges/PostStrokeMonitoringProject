package org.example.Server.IFaces;


import org.example.POJOS.Doctor;

public interface DoctorManager {
   public void addDoctor(Doctor doctor);
   public boolean deleteDoctor(String email);
   public void assingDoctorToPatient(Integer patient_id, Integer doctor_id);
   public Doctor searchDoctorByEmail(String email);
   public void updatePassword(int doctor_id, String newPassword);
   public boolean checkPassword(String email, String password);
   public void updateEmail(int doctor_id, String email);
   public void updatePhone(int doctor_id, int phone);
   public Doctor searchDoctorById(int doctor_id);
}
