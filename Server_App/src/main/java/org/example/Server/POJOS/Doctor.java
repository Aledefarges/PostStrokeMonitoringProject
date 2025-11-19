package org.example.Server.POJOS;
import java.util.List;
import java.util.Objects;

public class Doctor extends User {
    private int doctor_id;
    private String name;
    private String surname;
    private int phone;
    private List<Patient> patients;

    public Doctor(int doctor_id, String password, String name, String surname, String email, int phone, List<Patient> patients) {
        super(email, password, Role.DOCTOR);
        this.doctor_id = doctor_id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.patients = patients;
    }

    public Doctor(int doctor_id, String password ,String name, String surname, String email, int phone) {
        super(email, password, Role.DOCTOR);
        this.doctor_id = doctor_id;
        this.name = name;
        this.surname = surname;
        this.phone=phone;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return phone == doctor.phone && Objects.equals(name, doctor.name) && Objects.equals(surname, doctor.surname) && Objects.equals(patients, doctor.patients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, phone, patients);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone=" + phone +
                ", patients=" + patients +
                ", id=" + doctor_id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
