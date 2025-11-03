package org.example.JDBC;

import org.example.IFaces.PatientManager;
import org.example.POJOS.Patient;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCPatientManager implements PatientManager {
    private JDBCManager manager;

    public JDBCPatientManager(JDBCManager manager){
        this.manager = manager;
    }

    @Override
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO Patients (name, surname, dob, email, phone, medicalhistory, sex) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            //ahora me da error pq tiene que estar creado getConnection en JDBCManager

            ps.setString(1, patient.getName());
            ps.setString(2, patient.getSurname());
            ps.setDate(3, patient.getDob()); //Da error pq lo tenemos puesto como LocalDate y no Data en POJO, pero no hay en JDBC local date creo
            ps.setString(4, patient.getEmail());
            ps.setInt(5, patient.getPhone());
            ps.setString(1, patient.getMedicalHistory());
            ps.setString(1, patient.getSex()); //Da error pq ns como ponerlo con un enumerado y no string pq no existe la funcion de enumerado en SQL

            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deletePatient (Integer patient_id){
        String sql = "DELETE FROM Patients WHERE patient_id = ?";
        try{
            PreparedStatement ps = manager.getConnection().prepareStatement(sql);
            //da error pq tiene que estar creada en clase JDBCManager la funcion getConnection
            ps.setInt(1, patient_id);

            ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
