package org.example.POJOS;

import javax.swing.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Patient {

    private int patient_id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private Sex sex;
    private Date dob;
    private int phone;
    private List<Recording> recordings;
    private String medicalhistory;
    private Doctor doctor;
    private String feedback;

    public Patient(String name, String surname, Date dob, String email, Sex sex , String medicalHistory, int phone,String password, String feedback) throws Exceptions{
        if(!checkNameFormat(name)){
            throw new Exceptions(ValidationError.INVALID_NAME_FORMAT);
        }
        if(!checkSurnameFormat(surname)){
            throw new Exceptions(ValidationError.INVALID_SURNAME_FORMAT);
        }
        if(!checkEmailFormat(email)){
            throw new Exceptions(ValidationError.INVALID_EMAIL_FORMAT);
        }
        if(!checkPhoneFormat(phone)){
            throw new Exceptions(ValidationError.INVALID_PHONE_NUMBER);
        }
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.phone = phone;
        this.medicalhistory = medicalHistory;
        this.sex = sex;
        this.email = email;
        this.password = password;
        this.feedback = feedback;
    }

    public Patient(int patient_id, String name, String surname, Date dob, String email, int phone, String medicalHistory, Sex sex, String feedback) throws Exceptions{
        if(!checkNameFormat(name)){
            throw new Exceptions(ValidationError.INVALID_NAME_FORMAT);
        }
        if(!checkSurnameFormat(surname)){
            throw new Exceptions(ValidationError.INVALID_SURNAME_FORMAT);
        }
        if(!checkEmailFormat(email)){
            throw new Exceptions(ValidationError.INVALID_EMAIL_FORMAT);
        }
        if(!checkPhoneFormat(phone)){
            throw new Exceptions(ValidationError.INVALID_PHONE_NUMBER);
        }
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.phone = phone;
        this.medicalhistory = medicalHistory;
        this.sex = sex;
        this.email = email;
        this.password = null;
        this.feedback = feedback;
    }

    public Patient(int patient_id, String password, String name, String surname, Date dob, String email,  int phone, String medicalHistory, Sex sex, String feedback) throws Exceptions{
        if(!checkNameFormat(name)){
            throw new Exceptions(ValidationError.INVALID_NAME_FORMAT);
        }
        if(!checkSurnameFormat(surname)){
            throw new Exceptions(ValidationError.INVALID_SURNAME_FORMAT);
        }
        if(!checkEmailFormat(email)){
            throw new Exceptions(ValidationError.INVALID_EMAIL_FORMAT);
        }
        if(!checkPhoneFormat(phone)){
            throw new Exceptions(ValidationError.INVALID_PHONE_NUMBER);
        }
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.dob=dob;
        this.phone = phone;
        this.medicalhistory = medicalHistory;
        this.sex=sex;
        this.email = email;
        this.password = password;
        this.feedback = feedback;
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

    public void setName(String name) throws Exceptions {
        if(!checkNameFormat(name)){
            throw new Exceptions(ValidationError.INVALID_NAME_FORMAT);
        }
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) throws Exceptions {
        if(!checkSurnameFormat(surname)){
            throw new Exceptions(ValidationError.INVALID_SURNAME_FORMAT);
        }
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exceptions {
        if(!checkEmailFormat(email)){
            throw new Exceptions(ValidationError.INVALID_EMAIL_FORMAT);
        }
        this.email = email;
    }

    public Doctor getDoctor() {
        return doctor;
     }

    public void setDoctor(Doctor doctor) {
          this.doctor = doctor;
    }

    public String getMedicalhistory() {
        return medicalhistory;
    }

    public void setMedicalhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) throws Exceptions{
        if(!checkPhoneFormat(phone)){
            throw new Exceptions(ValidationError.INVALID_PHONE_NUMBER);
        }
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patient_id=" + patient_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", dob=" + dob +
                ", phone=" + phone +
                ", recordings=" + recordings +
                ", medicalhistory='" + medicalhistory + '\'' +
                ", doctor=" + doctor + '\'' +
                ", feedback='" + feedback +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patient_id == patient.patient_id && phone == patient.phone && Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(email, patient.email) && Objects.equals(password, patient.password) && sex == patient.sex && Objects.equals(dob, patient.dob) && Objects.equals(recordings, patient.recordings) && Objects.equals(medicalhistory, patient.medicalhistory) && Objects.equals(doctor, patient.doctor) && Objects.equals(feedback, patient.feedback);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient_id, name, surname, email, password, sex, dob, phone, recordings, medicalhistory, doctor, feedback);
    }

    public enum Sex {
        M,F;
    }

    private static boolean checkEmailFormat(String email) {
        if(email == null || email.isEmpty()) {
            return false;
        }
        if (email.contains("@") && email.contains(".")) {
            return true;
        }
        return false;
    }
    private static boolean checkPhoneFormat(int phone) {
        if (phone < 600000000 || phone > 799999999) {
            return false;
        }
        else
            return true;
    }
    private static boolean checkNameFormat(String name) {
        for (char character : name.toCharArray()) {
            if (!Character.isLetter(character) && character != ' ') {
                return false;
            }
        }
        return true;
    }
    private static boolean checkSurnameFormat(String surname) {
        for (char character : surname.toCharArray()) {
            if (!Character.isLetter(character) && character != ' ' && character != '-' && character != '\'') {
                //Accepts compound surnames separated by a - and surnames including '
                return false;
            }
        }
        return true;
    }

}
