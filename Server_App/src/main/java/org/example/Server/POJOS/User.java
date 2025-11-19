package org.example.Server.POJOS;

public abstract class User {
    protected int user_id;
    protected String email;
    protected String password;
    protected Role role;


    public User(int user_id, String email, String password, Role role) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(int user_id, String email, String password) {
        this.user_id = user_id;
        this.email = email;
        this.password = password;
    }

    public User() {

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public enum Role{

    PATIENT, DOCTOR, ADMINISTRATOR;
    }
}