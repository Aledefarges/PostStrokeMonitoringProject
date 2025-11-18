package POJOS;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Patient extends User{
    private int patient_id;
    private String name;
    private String surname;
    private Sex sex; //he cambiado el enumerado de sex a string para que sea compatible con sql
    private Date dob; //he cambiado el Localdate a Date para que sea compatible con sql
    private String email;
    private int phone;
    //private List<Recording> recordings;
    //private String password;
    private String medicalhistory;
    // private Doctor doctor;


   /* public Patient(int patient_id, String name, String surname, Date dob, String email, Integer phone, String medicalhistory, Sex sex, List<Recording> recordings, Doctor doctor) {
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.recordings = recordings;
        this.medicalhistory = medicalhistory;
        this.doctor = doctor;
    }*/

    public Patient(String name, String surname, Date dob, String email,
                   int phone, String medicalhistory, Sex sex) {

        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.medicalhistory = medicalhistory;
        this.sex = sex;
    }

   public Patient(int patient_id, String name, String surname, Date dob, String email, Integer phone, String medicalhistory, Sex sex) {
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.phone = phone;
        this.medicalhistory = medicalhistory;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
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

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    //public void setRecordings(List<Recording> recordings) {
     //   this.recordings = recordings;
    //}

    public int getPatient_id() {
        return patient_id;
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

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    //public List<Recording> getRecordings() {
     //   return recordings;
    //}

    //public String getPassword() {
        //return password;
    //}

    //public void setPassword(String password) {
        //this.password = password;
    //}

    public String getMedicalhistory() {
        return medicalhistory;
    }

    public void setMedicalhistory(String medicalhistory) {
        this.medicalhistory = medicalhistory;
    }
   // public Doctor getDoctor() {
     //   return doctor;
    //}
    //public void setDoctor(Doctor doctor) {
     //   this.doctor = doctor;
    //}

    @Override
    /*public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patient_id == patient.patient_id && phone == patient.phone && Objects.equals(name, patient.name)
                && Objects.equals(surname, patient.surname) && Objects.equals(sex, patient.sex)
                && Objects.equals(dob, patient.dob) && Objects.equals(email, patient.email)
                && Objects.equals(recordings, patient.recordings) && Objects.equals(medicalhistory, patient.medicalhistory)
                && Objects.equals(doctor, patient.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient_id, name, surname, sex, dob, email, phone, recordings, medicalhistory, doctor);
    }

*/
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + patient_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex=" + sex +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                //", recordings=" + recordings +
                '}';
    }

    public enum Sex {
        MALE,FEMALE;
    }
}
