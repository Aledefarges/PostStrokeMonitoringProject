package org.example.POJOS;

import java.util.List;
import java.util.Objects;

public class Doctor  {
    private int doctor_id;
    private String name;
    private String surname;
    private int phone;
    private List<Patient> patients;
    private String email;
    private String password;

    public Doctor(){

    }

    public Doctor(String name, String surname, int phone, String email, String password, List<Patient> patients) throws Exceptions {
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
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.patients = patients;
    }

    public Doctor(String name, String surname, int phone, String email, String password) throws  Exceptions {
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
        this.phone=phone;
        this.email = email;
        this.password = password;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) throws Exceptions{
        if(!checkPhoneFormat(phone)){
            throw new Exceptions(ValidationError.INVALID_PHONE_NUMBER);
        }
        this.phone = phone;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return doctor_id == doctor.doctor_id && phone == doctor.phone && Objects.equals(name, doctor.name) && Objects.equals(surname, doctor.surname) && Objects.equals(patients, doctor.patients) && Objects.equals(email, doctor.email) && Objects.equals(password, doctor.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor_id, name, surname, phone, patients, email, password);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctor_id=" + doctor_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
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
                return false;
            }
        }
        return true;
    }
}