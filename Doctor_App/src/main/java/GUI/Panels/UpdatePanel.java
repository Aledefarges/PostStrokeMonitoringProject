/*
 * Created by JFormDesigner on Sat Nov 29 19:35:28 CET 2025
 */

package GUI.Panels;

import org.example.Connection.Connection_Doctor;
import org.example.POJOS.Patient;

import java.awt.*;
import javax.swing.*;


public class UpdatePanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    private Patient patient;

    public UpdatePanel(AppFrameDoctor appFrame, Connection_Doctor connection, Patient patient) {
        this.connection = connection;
        this.appFrame = appFrame;
        this.patient = patient;
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(110,110,30,30));

        label1.setFont(new Font("Arial", Font.BOLD, 16));
        update_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));

        update_button.setBackground(new Color(70, 130, 180));
        update_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);

        update_button.addActionListener(e-> updateHistory());
        back_button.addActionListener(e-> backToMenu());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        label1 = new JLabel();
        mh_button = new JTextField();
        update_button = new JButton();
        back_button = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {304, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Insert the new and updated medical history ");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        add(mh_button, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- update_button ----
        update_button.setText("UPDATE");
        add(update_button, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- back_button ----
        back_button.setText("BACK");
        add(back_button, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void updateHistory() {
        String newHistory = mh_button.getText().trim();
        if(newHistory.isEmpty()){
            JOptionPane.showMessageDialog(this, "The medical history cannot be empty");
            return;
        }

        boolean ok = connection.sendPatientHistory(patient.getEmail(), newHistory);
        if(ok){
            patient.setMedicalhistory(newHistory);
            JOptionPane.showMessageDialog(this, "The medical history updated successfully");
            appFrame.switchPanel(new PatientOptionPanel(appFrame, connection, patient));
        }else{
            JOptionPane.showMessageDialog(this, "Medical history not updated");
        }

    }
    private void backToMenu() {
        appFrame.switchPanel(new PatientOptionPanel(appFrame, connection,patient));
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JTextField mh_button;
    private JButton update_button;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
