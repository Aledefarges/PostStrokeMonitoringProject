package org.example.POJOS;



import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Patient  {
    private int patient_id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Sex sex; //he cambiado el enumerado de sex a string para que sea compatible con sql
    private Date dob; //he cambiado el Localdate a Date para que sea compatible con sql
    private int phone;
    private List<Recording> recordings;
    private String medicalhistory;
//private Doctor doctor;

    public Patient(String name, String surname, Date dob, String email, Integer phone, String medicalHistory, Sex sex, String password) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.phone = phone;
        this.medicalhistory = medicalHistory;
        this.sex = sex;
        this.email = email;
        this.password = password;
    }

    public Patient(int patient_id, String name, String surname, Date dob, String email, Integer phone, String medicalHistory, Sex sex) {
         // para los métodos sin password
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.phone = phone;
        this.medicalhistory = medicalHistory;
        this.sex = sex;
        this.email = email;
        this.password = null;
    }

    public Patient(int patient_id, String password, String name, String surname, Date dob, String email,  Integer phone, String medicalHistory, Sex sex) {
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.dob=dob;
        this.phone = phone;
        this.medicalhistory = medicalHistory;
        this.sex=sex;
        this.email = email;
        this.password = password;
        //this.doctor.getUser_id()=doctorId;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Doctor getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(Doctor doctor) {
//        this.doctor = doctor;
//    }

    public String getMedicalhistory() {
        return medicalhistory;
    }

    public void setMedicalhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory;
    }

    public List<Recording> getRecordings() {
        return recordings;
    }

    public void setRecordings(List<Recording> recordings) {
        this.recordings = recordings;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) return false;
//        Patient patient = (Patient) o;
//        return patient_id == patient.patient_id && phone == patient.phone && Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(email, patient.email) && Objects.equals(password, patient.password) && sex == patient.sex && Objects.equals(dob, patient.dob) && Objects.equals(recordings, patient.recordings) && Objects.equals(medicalhistory, patient.medicalhistory) && Objects.equals(doctor, patient.doctor);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(patient_id, name, surname, email, password, sex, dob, phone, recordings, medicalhistory, doctor);
//    }

    @Override
    public String toString() {
        return "Patient{" +
                "patient_id=" + patient_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", sex=" + sex +
                ", dob=" + dob +
                ", phone=" + phone +
                ", recordings=" + recordings +
                ", medicalhistory='" + medicalhistory + '\'' +
              //  ", doctor=" + doctor +
                '}';
    }

    public enum Sex {
        M,F;
    } //
}
