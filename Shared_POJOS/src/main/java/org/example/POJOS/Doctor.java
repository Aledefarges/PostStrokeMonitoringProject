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

    public Doctor(int doctor_id, String password, String name, String surname, String email, int phone, List<Patient> patients) {
        this.doctor_id = doctor_id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.patients = patients;
        this.email = email;
        this.password = password;
    }

    public Doctor(int id, String password ,String name, String surname, String email, int phone) {
        this.doctor_id = id;
        this.name = name;
        this.surname = surname;
        this.phone=phone;
        this.email = email;
        this.password = password;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
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

    public void setEmail(String email) {
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
                ", patients=" + patients +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}