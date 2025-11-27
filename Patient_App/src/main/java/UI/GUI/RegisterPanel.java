/*
 * Created by JFormDesigner on Tue Nov 25 23:48:45 CET 2025
 */

package UI.GUI;

import Connection.Connection_With_Server;
import org.example.POJOS.Patient;

import java.awt.*;
import java.sql.Date;
import javax.swing.*;


public class RegisterPanel extends JPanel {
    private Connection_With_Server connection;
    private AppFrame appFrame;
    public RegisterPanel(AppFrame appFrame, Connection_With_Server connection) {
        this.appFrame = appFrame;
        this.connection = connection;
        initComponents();

        loadDoctorsIntoCombo();

        title.setFont(new Font("Arial", Font.BOLD, 20));
        name_label.setFont(new Font("Arial", Font.BOLD,14));
        surname_label.setFont(new Font("Arial", Font.BOLD,14));
        dob_label.setFont(new Font("Arial", Font.BOLD,14));
        email_label.setFont(new Font("Arial", Font.BOLD, 14));
        phone_label.setFont(new Font("Arial", Font.BOLD, 14));
        mh_label.setFont(new Font("Arial", Font.BOLD, 14));
        sex_label.setFont(new Font("Arial", Font.BOLD, 14));
        password_label.setFont(new Font("Arial", Font.BOLD, 14));
        doctor_label.setFont(new Font("Arial", Font.BOLD, 14));
        register_button.setFont(new Font("Arial", Font.PLAIN, 14));

        register_button.setBackground(new Color(70,130,180));
        register_button.setForeground(Color.WHITE);

        setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        register_button.addActionListener(e-> registerPatient());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        title = new JLabel();
        name_label = new JLabel();
        name_field = new JTextField();
        surname_label = new JLabel();
        surname_field = new JTextField();
        dob_label = new JLabel();
        dob_field = new JTextField();
        email_label = new JLabel();
        email_field = new JTextField();
        phone_label = new JLabel();
        phone_field = new JTextField();
        mh_label = new JLabel();
        mh_field = new JTextField();
        sex_label = new JLabel();
        sex_field = new JTextField();
        password_label = new JLabel();
        password_field = new JTextField();
        doctor_label = new JLabel();
        doctors_box = new JComboBox();
        register_button = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {166, 276, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- title ----
        title.setText("Complete your patient information");
        add(title, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- name_label ----
        name_label.setText("Name:");
        add(name_label, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(name_field, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- surname_label ----
        surname_label.setText("Surname:");
        add(surname_label, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(surname_field, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- dob_label ----
        dob_label.setText("Date of Birth (yyyy-mm-dd):");
        add(dob_label, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(dob_field, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- email_label ----
        email_label.setText("Email:");
        add(email_label, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(email_field, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- phone_label ----
        phone_label.setText("Phone:");
        add(phone_label, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(phone_field, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- mh_label ----
        mh_label.setText("Medical History:");
        add(mh_label, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(mh_field, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- sex_label ----
        sex_label.setText("Sex (M/F):");
        add(sex_label, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(sex_field, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- password_label ----
        password_label.setText("Create password:");
        add(password_label, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(password_field, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- doctor_label ----
        doctor_label.setText("Choose doctor:");
        add(doctor_label, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(doctors_box, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- register_button ----
        register_button.setText("REGISTER");
        add(register_button, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private void loadDoctorsIntoCombo() {
        try{
            String response = connection.requestAllDoctor();
            if (response == null || !response.startsWith("DOCTORS_LIST")){
                JOptionPane.showMessageDialog(this, "DOCTOR LIST NOT FOUND");
                return;
            }

            String[] parts = response.split("\\|");
            doctors_box.removeAllItems();
            for(int i=1; i<parts.length; i++){
                String[] doctor = parts[i].split(";");
                int doctor_id = Integer.parseInt(doctor[0]);
                String doctor_name = doctor[1] + " " + doctor[2];
                doctors_box.addItem(doctor_id + " - " + doctor_name);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error loading doctors" + e.getMessage());
        }
    }

    private void registerPatient(){
        try{
            String name = name_field.getText().trim();  // trim() se encarga de quitar las espacios que el usuario puede dejar al escribir
            String surname = surname_field.getText().trim();
            String dob = dob_field.getText().trim();
            Date dob_1 = Date.valueOf(dob);
            String email = email_field.getText().trim();
            String phone = phone_field.getText().trim();
            int phone_1 = Integer.parseInt(phone);
            String medicalHistory = mh_field.getText().trim();
            String sex = sex_field.getText().trim().toUpperCase();
            Patient.Sex sexEnum = Patient.Sex.valueOf(sex);
            String password = password_field.getText().trim();
            String doctor_selected = doctors_box.getSelectedItem().toString();
            int doctot_id = Integer.parseInt(doctor_selected.split(" - ")[0]);

            if(name.isEmpty() || surname.isEmpty() || dob.isEmpty() || email.isEmpty()||phone.isEmpty() || sex.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill all the fields");
                return;
            }

            Patient patient = new Patient(name,surname, dob_1,email,sexEnum,medicalHistory,phone_1,password);
            boolean ok_register = connection.sendPatientToServer(patient, doctot_id);
            if(ok_register){
                JOptionPane.showMessageDialog(this, "Register successful");
                appFrame.switchPanel(new MenuPanel(appFrame, connection));
            }else{
                JOptionPane.showMessageDialog(this, "Something went wrong, error register");
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Invalid data: " +e.getMessage());
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel title;
    private JLabel name_label;
    private JTextField name_field;
    private JLabel surname_label;
    private JTextField surname_field;
    private JLabel dob_label;
    private JTextField dob_field;
    private JLabel email_label;
    private JTextField email_field;
    private JLabel phone_label;
    private JTextField phone_field;
    private JLabel mh_label;
    private JTextField mh_field;
    private JLabel sex_label;
    private JTextField sex_field;
    private JLabel password_label;
    private JTextField password_field;
    private JLabel doctor_label;
    private JComboBox doctors_box;
    private JButton register_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
