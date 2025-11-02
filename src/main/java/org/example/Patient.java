package org.example;

import jdk.jfr.Recording;

import java.time.LocalDate;
import java.util.List;

public class Patient {
    private int id;
    private String name;
    private String surname;
    private Sex sex;
    private LocalDate dob;
    private String email;
    private int phone;
    private List<Recording> recordings;
    private String password;
    private String medicalHistory;


    public Patient(int id, String name, String surname, Sex sex, LocalDate dob, String email, int phone, List<Recording> recordings, String medicalHistory) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.recordings = recordings;
        this.medicalHistory = medicalHistory;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setRecordings(List<Recording> recordings) {
        this.recordings = recordings;
    }

    public int getId() {
        return id;
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

    public LocalDate getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public List<Recording> getRecordings() {
        return recordings;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex=" + sex +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", recordings=" + recordings +
                '}';
    }

    public enum Sex {
        MALE,FEMALE;
    }
}
