
package GUI.Panels;

import Connection.Connection_Doctor;
import org.example.POJOS.Patient;

import java.awt.*;
import javax.swing.*;


public class PatientOptionPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    private Patient patient;
    public PatientOptionPanel(AppFrameDoctor appFrame, Patient patient) {
        this.connection = appFrame.getConnection();
        this.appFrame = appFrame;
        this.patient = patient;
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(60,80,40,40));

        label1.setFont(new Font("Arial", Font.BOLD, 16));
        recordings_button.setFont(new Font("Arial", Font.PLAIN, 14));
        feedback_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));

        recordings_button.setBackground(new Color(70,130,180));
        recordings_button.setForeground(Color.WHITE);
        feedback_button.setBackground(new Color(70,130,180));
        feedback_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);

        recordings_button.addActionListener(e-> goToRecordings());
        feedback_button.addActionListener(e-> goToFeedback());
        back_button.addActionListener(e-> backToMenu());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label1 = new JLabel();
        recordings_button = new JButton();
        feedback_button = new JButton();
        separator1 = new JSeparator();
        back_button = new JButton();

        //======== this ========
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax
        .swing.border.EmptyBorder(0,0,0,0), "JF\u006frmD\u0065sig\u006eer \u0045val\u0075ati\u006fn",javax.swing
        .border.TitledBorder.CENTER,javax.swing.border.TitledBorder.BOTTOM,new java.awt.
        Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.red
        ), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override
        public void propertyChange(java.beans.PropertyChangeEvent e){if("\u0062ord\u0065r".equals(e.getPropertyName(
        )))throw new RuntimeException();}});
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {335, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Select one option:");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- recordings_button ----
        recordings_button.setText("1. See recordings of patient");
        add(recordings_button, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- feedback_button ----
        feedback_button.setText("2. Send feedback to patient ");
        add(feedback_button, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        add(separator1, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- back_button ----
        back_button.setText("BACK TO DOCTOR MENU");
        add(back_button, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private void goToRecordings() {
        appFrame.switchPanel(new RecordingsPanel(appFrame, patient));
    }
    private void goToFeedback() {
        appFrame.switchPanel(new FeedbackPanel(appFrame, patient));
    }
    private void backToMenu() {
        appFrame.switchPanel(new DoctorMenuPanel(appFrame));
    }
    
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JButton recordings_button;
    private JButton feedback_button;
    private JSeparator separator1;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
