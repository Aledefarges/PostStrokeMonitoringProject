/*
 * Created by JFormDesigner on Wed Nov 26 10:24:14 CET 2025
 */

package GUI.Panels;

import org.example.Connection.Connection_Doctor;

import javax.swing.*;
import java.awt.*;


public class PasswordPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    public PasswordPanel(AppFrameDoctor appFrame, Connection_Doctor connection) {
        this.appFrame = appFrame;
        this.connection = connection;
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(60,80,40,40));
        old_label.setFont(new Font("Arial", Font.BOLD, 14));
        new_label.setFont(new Font("Arial", Font.BOLD, 14));
        ok_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));

        ok_button.setBackground(new Color(70,130,180));
        ok_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);

        ok_button.addActionListener(e -> changePassword());
        back_button.addActionListener(e -> backToMenu());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        old_label = new JLabel();
        old_field = new JTextField();
        new_label = new JLabel();
        new_field = new JTextField();
        ok_button = new JButton();
        back_button = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {111, 258, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- old_label ----
        old_label.setText("Old password:");
        add(old_label, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(old_field, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- new_label ----
        new_label.setText("New password:");
        add(new_label, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(new_field, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- ok_button ----
        ok_button.setText("OK");
        add(ok_button, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- back_button ----
        back_button.setText("BACK TO DOCTOR MENU");
        add(back_button, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void changePassword() {

        try{
            String old_password = old_field.getText().trim();
            String new_password = new_field.getText().trim();

            if(old_password.isEmpty() || new_password.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all the fields", "FIELD EMPTY",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(new_password.equals(old_password)){
                JOptionPane.showMessageDialog(null, "Please enter a different password from the old one",
                        "SAME PASSWORD", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Función de prueba para encryptar la constraseña:
            String old_enc = connection.encryptDoctorPassword(old_password);
            String new_enc = connection.encryptDoctorPassword(new_password);

            boolean passwordOK = connection.sendChangePassword(old_enc,new_enc);
            
            if (passwordOK) {
                JOptionPane.showMessageDialog(this, "Password change successful", "SUCCESS",
                        JOptionPane.INFORMATION_MESSAGE);
                appFrame.switchPanel(new DoctorMenuPanel(appFrame, connection));
            }else{
                JOptionPane.showMessageDialog(this, "Invalid password", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error" +e.getMessage());
        }
    }

    private void backToMenu() {
        appFrame.switchPanel(new DoctorMenuPanel(appFrame, connection));
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel old_label;
    private JTextField old_field;
    private JLabel new_label;
    private JTextField new_field;
    private JButton ok_button;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
