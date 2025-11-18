package org.example.Server.POJOS;
import java.util.List;

public class Doctor extends User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private int phone;
    private List<Patient> patients;

    public Doctor(int id, String name, String surname, String email, int phone, List<Patient> patients) {
        //¿Hay que meter super()
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.patients = patients;
    }

    public Doctor(int id, String name, String surname, String email, int phone) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone=phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", patients=" + patients +
                '}';
    }
}
