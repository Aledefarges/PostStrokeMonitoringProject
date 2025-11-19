package org.example.Server.POJOS;

public class Administrator extends User{
    private int admin_id;
    private String name;
    private String surname;
    private int phone;

    public Administrator(int admin_id, String email, String password, Role role, String name, String surname, String email, int phone) {
        super(email, password, role);
        this.admin_id = admin_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
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

    @Override
    public String toString() {
        return "Administrator{" +
                "id=" + admin_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
