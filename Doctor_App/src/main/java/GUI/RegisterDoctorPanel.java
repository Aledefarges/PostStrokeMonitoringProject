/*
 * Created by JFormDesigner on Fri Nov 28 08:58:27 CET 2025
 */

package GUI;

import org.example.Connection.Connection_Doctor;
import org.example.POJOS.Doctor;
import org.example.POJOS.Patient;

import java.awt.*;
import java.sql.Date;
import javax.swing.*;

public class RegisterDoctorPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    public RegisterDoctorPanel(AppFrameDoctor appFrame) {
        this.connection = appFrame.getConnection();
        this.appFrame = appFrame;

        initComponents();

        setBorder(BorderFactory.createEmptyBorder(60,80,40,40));

        label1.setFont(new Font("Arial", Font.BOLD, 20));
        name_label.setFont(new Font("Arial", Font.BOLD,14));
        surname_label.setFont(new Font("Arial", Font.BOLD,14));
        email_label.setFont(new Font("Arial", Font.BOLD, 14));
        phone_label.setFont(new Font("Arial", Font.BOLD, 14));
        password_label.setFont(new Font("Arial", Font.BOLD, 14));
        register_button.setFont(new Font("Arial", Font.PLAIN, 14));

        register_button.setBackground(new Color(70,130,180));
        register_button.setForeground(Color.WHITE);


        register_button.addActionListener(e-> registerDoctor());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label1 = new JLabel();
        name_label = new JLabel();
        name_field = new JTextField();
        surname_label = new JLabel();
        surname_field = new JTextField();
        phone_label = new JLabel();
        phone_field = new JTextField();
        email_label = new JLabel();
        email_field = new JTextField();
        password_label = new JLabel();
        password_field = new JTextField();
        register_button = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {139, 252, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {29, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Complete your doctor information: ");
        add(label1, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
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

        //---- phone_label ----
        phone_label.setText("Phone:");
        add(phone_label, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(phone_field, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
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

        //---- password_label ----
        password_label.setText("Create password:");
        add(password_label, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(password_field, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- register_button ----
        register_button.setText("REGISTER");
        add(register_button, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void registerDoctor(){
        try{
            String name = name_field.getText().trim();  // trim() se encarga de quitar las espacios que el usuario puede dejar al escribir
            String surname = surname_field.getText().trim();
            String phone = phone_field.getText().trim();
            int phone_1 = Integer.parseInt(phone);
            String email = email_field.getText().trim();
            String password = password_field.getText().trim();
            //Encrypted contraseña:
            String encryptedPassword = connection.encryptDoctorPassword(password);

            if(name.isEmpty() || surname.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill all the fields");
                return;
            }

            //En el paciente, ahora va encryptedPassword donde antes para Nerea iba password
            Doctor doctor = new Doctor(name,surname, phone_1, email,encryptedPassword);
            boolean ok_register = connection.sendRegisterDoctor(doctor);
            if(ok_register){
                JOptionPane.showMessageDialog(this, "Register successful");
                appFrame.switchPanel(new MenuPanel(appFrame));
            }else{
                JOptionPane.showMessageDialog(this, "Something went wrong, error register");
            }

        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Invalid data: " +e.getMessage());
        }
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JLabel name_label;
    private JTextField name_field;
    private JLabel surname_label;
    private JTextField surname_field;
    private JLabel phone_label;
    private JTextField phone_field;
    private JLabel email_label;
    private JTextField email_field;
    private JLabel password_label;
    private JTextField password_field;
    private JButton register_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
