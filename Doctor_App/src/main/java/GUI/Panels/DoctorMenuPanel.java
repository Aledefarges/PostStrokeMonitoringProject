
package GUI.Panels;

import Connection.Connection_Doctor;

import java.awt.*;
import javax.swing.*;


public class DoctorMenuPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;

    public DoctorMenuPanel(AppFrameDoctor appFrame) {
        this.appFrame = appFrame;
        this.connection = appFrame.getConnection();
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(40, 110, 30, 30));

        label1.setFont(new Font("Arial", Font.BOLD, 20));
        select_label.setFont(new Font("Arial", Font.BOLD, 18));
        patient_button.setFont(new Font("Arial", Font.PLAIN, 14));
        email_button.setFont(new Font("Arial", Font.PLAIN, 14));
        password_button.setFont(new Font("Arial", Font.PLAIN, 14));
        exit_button.setFont(new Font("Arial", Font.PLAIN, 14));

        patient_button.setBackground(new Color(70,130,180));
        patient_button.setForeground(Color.WHITE);
        email_button.setBackground(new Color(70, 130, 180));
        email_button.setForeground(Color.WHITE);
        password_button.setBackground(new Color(70, 130, 180));
        password_button.setForeground(Color.WHITE);
        exit_button.setBackground(new Color(62, 156, 118));
        exit_button.setForeground(Color.WHITE);
        separator1.setForeground(new Color(70,130,180));

        patient_button.addActionListener(e-> goToHandlePatient());
        email_button.addActionListener(e-> changeEmail());
        password_button.addActionListener(e-> changePassword());
        exit_button.addActionListener(e-> {
            connection.close();
            System.exit(0);
        });

    }

    private void initComponents() {

        label1 = new JLabel();
        separator1 = new JSeparator();
        select_label = new JLabel();
        patient_button = new JButton();
        email_button = new JButton();
        password_button = new JButton();
        exit_button = new JButton();


        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {271, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Doctor Menu");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        add(separator1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- select_label ----
        select_label.setText("Select an option:");
        add(select_label, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- patient_button ----
        patient_button.setText("1. Handle patients");
        add(patient_button, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- email_button ----
        email_button.setText("2. Change email");
        add(email_button, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- password_button ----
        password_button.setText("3. Change password");
        add(password_button, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- exit_button ----
        exit_button.setText("EXIT");
        add(exit_button, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void goToHandlePatient() {
        appFrame.switchPanel(new HandlePatientPanel(appFrame));
    }
    private void changeEmail() {
        appFrame.switchPanel(new EmailPanel(appFrame));
    }
    private void changePassword() {
        appFrame.switchPanel(new PasswordPanel(appFrame));
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JLabel label1;
    private JSeparator separator1;
    private JLabel select_label;
    private JButton patient_button;
    private JButton email_button;
    private JButton password_button;
    private JButton exit_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
