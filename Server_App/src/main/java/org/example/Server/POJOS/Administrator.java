package org.example.Server.POJOS;

public class Administrator extends User{
    private String name;
    private String surname;
    private int phone;

    public Administrator(int id, String email, String password, String name, String surname, int phone) {
        super(id, email, password, Role.ADMINISTRATOR);
        this.name = name;
        this.surname = surname;
        this.phone = phone;
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

    @Override
    public String toString() {
        return "Administrator{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone=" + phone +
                '}';
    }
}
