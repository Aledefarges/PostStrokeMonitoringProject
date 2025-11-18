package org.example.Server.POJOS;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Patient extends User{

    private String name;
    private String surname;
    private Sex sex; //he cambiado el enumerado de sex a string para que sea compatible con sql
    private Date dob; //he cambiado el Localdate a Date para que sea compatible con sql
    private int phone;
    private List<Recording> recordings;
    private String medicalhistory;
    private Doctor doctor;


    public Patient(int id, String password, String name, String surname, Date dob, String email, Integer phone, String medicalhistory, Sex sex, List<Recording> recordings, Doctor doctor) {
        super(id, email, password, Role.PATIENT);
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.recordings = recordings;
        this.medicalhistory = medicalhistory;
        this.doctor = doctor;
    }

   public Patient(int id, String password, int patient_id, String name, String surname, Date dob, String email, Integer phone, String medicalhistory, Sex sex) {
       super(id, email, password, Role.PATIENT);
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.medicalhistory = medicalhistory;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }


    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setRecordings(List<Recording> recordings) {
        this.recordings = recordings;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Sex getSex() {
        return sex;
    }

    public Date getDob() {
        return dob;
    }


    public int getPhone() {
        return phone;
    }

    public List<Recording> getRecordings() {
        return recordings;
    }

    //public String getPassword() {
        //return password;
    //}

    //public void setPassword(String password) {
        //this.password = password;
    //}

    public String getMedicalhistory() {
        return medicalhistory;
    }

    public void setMedicalhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory;
    }
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return  phone == patient.phone && Objects.equals(name, patient.name)
                && Objects.equals(surname, patient.surname) && Objects.equals(sex, patient.sex)
                && Objects.equals(dob, patient.dob)
                && Objects.equals(recordings, patient.recordings) && Objects.equals(medicalhistory, patient.medicalhistory)
                && Objects.equals(doctor, patient.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, sex, dob, phone, recordings, medicalhistory, doctor);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex=" + sex +
                ", dob=" + dob +
                ", phone=" + phone +
                ", recordings=" + recordings +
                ", medicalhistory='" + medicalhistory + '\'' +
                ", doctor=" + doctor +
                ", id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    public enum Sex {
        MALE,FEMALE;
    }
}
