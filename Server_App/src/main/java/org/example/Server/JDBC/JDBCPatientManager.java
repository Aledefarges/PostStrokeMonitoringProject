package org.example.Server.JDBC;


import org.example.POJOS.Exceptions;
import org.example.Server.IFaces.PatientManager;
import org.example.POJOS.Patient;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class JDBCPatientManager implements PatientManager {
    private JDBCManager manager;


    public JDBCPatientManager(JDBCManager manager){
        this.manager = manager;
    }


    @Override
    public void addPatient(Patient patient) {
        String sql = "INSERT INTO Patients (name, surname, dob, email, sex, phone, medicalHistory, password, doctor_id) VALUES (?,?,?,?,?,?,?,?,?)";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, patient.getName());
            ps.setString(2, patient.getSurname());

            LocalDate dob = patient.getDob().toLocalDate();
            java.sql.Date sqlDate = java.sql.Date.valueOf(
                    dob.atStartOfDay(ZoneId.systemDefault()).toLocalDate()
            );
            ps.setDate(3, sqlDate);
            ps.setString(4, patient.getEmail());
            ps.setString(5, patient.getSex().toString().trim());
            ps.setInt(6, patient.getPhone());
            ps.setString(7, patient.getMedicalhistory());
            ps.setString(8, patient.getPassword());
            ps.setInt(9,patient.getDoctor().getDoctor_id());
            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    patient.setPatient_id(generatedId);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean deletePatient (String email){
        String sql = "DELETE FROM Patients WHERE email =  ? ";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, email);
            int rowsAffected = ps.executeUpdate(); //executeUpdate devuelve cuanats filas fueron eliminadas por el sql, el resultado puede ser 0 (no exsiet este mail), 1 se elimino paciente (ya que el mail es UNIQUE y no puede haber más de uno)
            return  rowsAffected == 1; //False si no se elimino ningun paciente (no existia ese email), true si se elimino un paciente
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Patient> getListOfPatients(){
        List<Patient> patients = new ArrayList<Patient>();
        String sql = "SELECT patient_id, password, name, surname, dob, email, phone, medicalHistory, sex, doctor_id, feedback FROM Patients";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();){
            while(rs.next())
            {
                int patient_id = rs.getInt("patient_id");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                Date dob = new Date(rs.getLong("dob"));
                String email = rs.getString("email");
                int phone = rs.getInt("phone");
                String medicalHistory = rs.getString("medicalHistory");
                Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));
                String feedback = rs.getString("feedback");

                //List<Recording> recordings = jdbcRecordingManager.getRecordingOfPatient(patient_id);
                //TODO cuando este hecho getRecordingOfPatient usarlo en este metodo para que aparezcan los recordings cuando se muestra a los pacientes

                Patient p= new Patient(patient_id, password, name, surname, dob, email, phone, medicalHistory, sex, feedback);
                //cuando este el getRecordingOfPatient añadir aqui tambien el atributo recording
                patients.add(p);
            }
        }catch(Exception e) {
            e.printStackTrace();}

        return patients;
    }

    @Override
    public Patient getPatientById(int patient_id) {
        Patient patient = null;
        String sql = "SELECT * FROM Patients WHERE patient_id = ?";

        try(Connection c = manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, patient_id);
            // The SQL query uses a placeholder (?) for the patient_id value, it is needed to replace that
            // placeholder before executing the query.
            // 'setInt(1, patient_id)' inserts the value into the first '?' in the SQL.
            // If we don't set it, the query remains incomplete and will fail.
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    String password = rs.getString("password");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    Date dob = new Date(rs.getLong("dob"));

                    int phone = rs.getInt("phone");
                    String medicalHistory = rs.getString("medicalHistory");
                    Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));
                    int doctor_id = rs.getInt("doctor_id");
                    String email = rs.getString("email");
                    String feedback = rs.getString("feedback");

                    patient = new Patient(patient_id, password, name,  surname, dob, email, phone, medicalHistory, sex, feedback);
                }
            } catch (Exceptions e) {
                throw new RuntimeException(e);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return patient;
    }


    @Override
    public Patient getPatientByEmail(String email) {
        Patient patient = null;

        String sql = "SELECT * FROM Patients WHERE email = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    int patient_id = rs.getInt("patient_id");
                    String name = rs.getString("name");
                    String surname = rs.getString("surname");
                    Date dob = new Date(rs.getLong("dob"));
                    int phone = rs.getInt("phone");
                    String medicalhistory = rs.getString("medicalHistory");
                    Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));
                    String password = rs.getString("password");
                    String feedback = rs.getString("feedback");
                    patient = new Patient(patient_id, password, name, surname, dob, email, phone, medicalhistory, sex, feedback);

                }
            } catch (Exceptions e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patient;
    }

    @Override
    public String getMedicalHistoryById(int patient_id) {
        String medicalHistory = null;
        String sql = "SELECT medicalHistory FROM Patients WHERE patient_id = ?";

        try(PreparedStatement ps = manager.getConnection().prepareStatement(sql)){
            ps.setInt(1, patient_id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    medicalHistory = rs.getString("medicalHistory");
                }
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return medicalHistory;
    }

    @Override
    public void updateName(int patient_id, String name) {
        String sql = "UPDATE Patients SET name = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSurName(int patient_id, String surname) {
        String sql = "UPDATE Patients SET surname = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, surname);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmail(int patient_id, String email) {
        String sql = "UPDATE Patients SET email = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateSex(int patient_id, Patient.Sex sex) {
        String sql = "UPDATE Patients SET sex = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, sex.name());
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDob(int patient_id, Date dob) {
        String sql = "UPDATE Patients SET dob = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setDate(1, dob);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePhone(int patient_id, int phone) {
        String sql = "UPDATE Patients SET phone = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setInt(1, phone);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMedicalHistory(int patient_id, String medicalHistory) {

        String sql = "UPDATE Patients SET medicalHistory = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, medicalHistory);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   @Override
   public List<Patient> getListOfPatientsOfDoctor(Integer doctor_id){
       List<Patient> patients = new ArrayList<>();
       String sql = "SELECT * FROM Patients WHERE doctor_id = ?";
       try(Connection c=manager.getConnection();
        PreparedStatement ps=c.prepareStatement(sql)){

           ps.setInt(1, doctor_id);

           try(ResultSet rs= ps.executeQuery()){
               while(rs.next()){
                   int patient_id = rs.getInt("patient_id");
                   String password = rs.getString("password");
                   String name = rs.getString("name");
                   String surname = rs.getString("surname");
                   Date dob = new Date(rs.getLong("dob"));
                   String email = rs.getString("email");
                   int phone = rs.getInt("phone");
                   String medicalHistory = rs.getString("medicalHistory");
                   Patient.Sex sex = Patient.Sex.valueOf(rs.getString("sex"));
                   String feedback = rs.getString("feedback");

                   Patient p = new Patient(patient_id, password, name, surname, dob, email, phone, medicalHistory, sex, feedback);
                   patients.add(p);
               }
           } catch (Exceptions e) {
               throw new RuntimeException(e);
           }
       }catch(SQLException e){
            e.printStackTrace();
        }
        return patients;
   }

   @Override
    public void updatePassword(int patient_id, String newPassword){
        String sql = "UPDATE Patients SET password = ? WHERE patient_id = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)){

            ps.setString(1, newPassword);
            ps.setInt(2, patient_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e2){
            e2.printStackTrace();
        }
    }

    @Override
    public boolean checkPassword(String email, String password) {
        String sql = "SELECT password FROM Patients WHERE email = ?";
        try(Connection c=manager.getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1, email);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    String pass = rs.getString("password");

                    return pass.equalsIgnoreCase(password);

                }

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return false;
    }
    @Override
    public void addFeedback(String email, String feedback){
        String sql = "UPDATE Patients SET feedback = ? WHERE email = ?";
        try (PreparedStatement ps = manager.getConnection().prepareStatement(sql)) {
            ps.setString(1, feedback);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



