/*
 * Created by JFormDesigner on Tue Nov 25 23:28:01 CET 2025
 */

package GUI.Panels;

import org.example.Connection.Connection_Doctor;
import javax.swing.*;
import java.awt.*;


public class LoginPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    public LoginPanel(AppFrameDoctor appFrame,  Connection_Doctor connection) {
        this.appFrame = appFrame;
        this.connection = connection;
        initComponents();

        this.add(panel1);

        email_label.setFont(new Font("Arial", Font.BOLD, 14));
        password_label.setFont(new Font("Arial", Font.BOLD, 14));
        login_button.setFont(new Font("Arial", Font.PLAIN, 14));
        show_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));

        login_button.setBackground(new Color(70,130,180));
        login_button.setForeground(Color.WHITE);
        show_button.setBackground(new Color(70,130,180));
        show_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);

        setBorder(BorderFactory.createEmptyBorder(60,60,40,40));

        show_button.addActionListener(e-> togglePasswordVisible());
        login_button.addActionListener(e-> login());
        back_button.addActionListener(e -> backToMenu());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        panel1 = new JPanel();
        email_label = new JLabel();
        email_field = new JTextField();
        password_label = new JLabel();
        password_field = new JPasswordField();
        show_button = new JButton();
        login_button = new JButton();
        back_button = new JButton();

        //======== panel1 ========
        {
            panel1.setLayout(new GridBagLayout());
            ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {105, 214, 64, 0};
            ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
            ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
            ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

            //---- email_label ----
            email_label.setText("Email:");
            panel1.add(email_label, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            panel1.add(email_field, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- password_label ----
            password_label.setText("Password:");
            panel1.add(password_label, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            panel1.add(password_field, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- show_button ----
            show_button.setText("Show");
            panel1.add(show_button, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

            //---- login_button ----
            login_button.setText("Log In");
            panel1.add(login_button, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- back_button ----
            back_button.setText("BACK");
            panel1.add(back_button, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private boolean passwordVisble = false;

    private void togglePasswordVisible() {
        if(!passwordVisble) {
            // Enseñar la contraseña
            password_field.setEchoChar((char)0);
            show_button.setText("Hide");
            passwordVisble = true;
        }else{
            password_field.setEchoChar('•');
            show_button.setText("Show");
            passwordVisble = false;
        }
    }


    private void login()  {
        try{
            String email = email_field.getText().trim();
            String password = password_field.getText().trim();

            if(email.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill all the fields",
                        "FIELD EMPTY", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Encryptar contraseña:
            String encryptedPassword = connection.encryptDoctorPassword(password);

            //En esta función antes iba password de Nerea, y ahora va encryptedPassword por si acaso no funciona
            String response = connection.sendDoctorLogin(email, encryptedPassword);
            if (response.startsWith("OK|LOGIN_SUCCESS_DOCTOR")) {
                JOptionPane.showMessageDialog(this, "Log In successful", "SUCCESS",
                        JOptionPane.INFORMATION_MESSAGE);
                appFrame.switchPanel(new DoctorMenuPanel(appFrame, connection));
            }else{
                JOptionPane.showMessageDialog(this, "Invalid email or password", "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Invalid data: " +e.getMessage());
        }


    }

    private void backToMenu() {
        appFrame.switchPanel(new MenuPanel(appFrame, connection));
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JPanel panel1;
    private JLabel email_label;
    private JTextField email_field;
    private JLabel password_label;
    private JPasswordField password_field;
    private JButton show_button;
    private JButton login_button;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
