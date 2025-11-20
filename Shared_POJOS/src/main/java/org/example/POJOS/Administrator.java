package org.example.POJOS;


import java.util.Objects;

public class Administrator  {
    private int admin_id;
    private String name;
    private String surname;
    private int phone;
    private String email;
    private String password;

    public Administrator(int id, String email, String password, String name, String surname, int phone) {
        this.admin_id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Administrator that = (Administrator) o;
        return admin_id == that.admin_id && phone == that.phone && Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(admin_id, name, surname, phone, email, password);
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "admin_id=" + admin_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
