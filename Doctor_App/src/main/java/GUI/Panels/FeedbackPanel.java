
package GUI.Panels;

import Connection.Connection_Doctor;
import org.example.POJOS.Patient;

import java.awt.*;
import javax.swing.*;


public class FeedbackPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    private Patient patient;

    public FeedbackPanel(AppFrameDoctor appFrame, Patient patient) {
        this.connection = appFrame.getConnection();
        this.appFrame = appFrame;
        this.patient = patient;
        initComponents();


        // This allows the doctor to read and modify already existing feedback
        if (patient.getFeedback()!= null){
            text_field.setText(patient.getFeedback());
        }

        setBorder(BorderFactory.createEmptyBorder(110,110,30,30));

        label1.setFont(new Font("Arial", Font.BOLD, 18));
        feedback_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));

        feedback_button.setBackground(new Color(70, 130, 180));
        feedback_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);

        feedback_button.addActionListener(e-> addFeedbackPatient());
        back_button.addActionListener(e-> backToMenu());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        scrollPane1 = new JScrollPane();
        text_field = new JTextPane();
        feedback_button = new JButton();
        back_button = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {304, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Insert feedback about the patient's health");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(text_field);
        }
        add(scrollPane1, new GridBagConstraints(0, 1, 1, 2, 0.5, 0.5,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- feedback_button ----
        feedback_button.setText("SEND FEEDBACK");
        add(feedback_button, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- back_button ----
        back_button.setText("BACK");
        add(back_button, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void addFeedbackPatient() {
        String feedback = text_field.getText().trim();

        if(feedback.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please fill out the field", "WARNING",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean ok = connection.sendFeedback(patient.getEmail(), feedback);
        if(ok){
            patient.setFeedback(feedback);
            JOptionPane.showMessageDialog(this, "The feedback has been sent",
                    "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            appFrame.switchPanel(new PatientOptionPanel(appFrame, patient));
        }else{
            JOptionPane.showMessageDialog(this, "Feedback not sent", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

    }
    private void backToMenu() {
        appFrame.switchPanel(new PatientOptionPanel(appFrame, patient));
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JScrollPane scrollPane1;
    private JTextPane text_field;
    private JButton feedback_button;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
