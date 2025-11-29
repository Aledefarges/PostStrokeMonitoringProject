/*
 * Created by JFormDesigner on Sat Nov 29 19:18:49 CET 2025
 */

package GUI.Panels;

import org.example.Connection.Connection_Doctor;
import org.example.POJOS.Patient;

import java.awt.*;
import javax.swing.*;


public class PatientOptionPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    private Patient patient;
    public PatientOptionPanel(AppFrameDoctor appFrame, Connection_Doctor connection, Patient patient) {
        this.connection = connection;
        this.appFrame = appFrame;
        this.patient = patient;
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(60,80,40,40));

        label1.setFont(new Font("Arial", Font.BOLD, 16));
        recordings_button.setFont(new Font("Arial", Font.PLAIN, 14));
        update_button.setFont(new Font("Arial", Font.PLAIN, 14));
        back_button.setFont(new Font("Arial", Font.PLAIN, 14));

        recordings_button.setBackground(new Color(70,130,180));
        recordings_button.setForeground(Color.WHITE);
        update_button.setBackground(new Color(70,130,180));
        update_button.setForeground(Color.WHITE);
        back_button.setBackground(new Color(62, 156, 118));
        back_button.setForeground(Color.WHITE);

        recordings_button.addActionListener(e-> goToRecordings());
        update_button.addActionListener(e-> goToUpdate());
        back_button.addActionListener(e-> backToMenu());

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label1 = new JLabel();
        recordings_button = new JButton();
        update_button = new JButton();
        separator1 = new JSeparator();
        back_button = new JButton();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border. EmptyBorder
        ( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border. TitledBorder. CENTER, javax. swing. border
        . TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog" ,java .awt .Font .BOLD ,12 ), java. awt
        . Color. red) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void
        propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException( )
        ; }} );
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

        //---- update_button ----
        update_button.setText("2. Update medical history of patient ");
        add(update_button, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
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
        appFrame.switchPanel(new RecordingsPanel(appFrame, connection, patient));
    }
    private void goToUpdate() {
        appFrame.switchPanel(new UpdatePanel(appFrame, connection, patient));
    }
    private void backToMenu() {
        appFrame.switchPanel(new DoctorMenuPanel(appFrame, connection));
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JButton recordings_button;
    private JButton update_button;
    private JSeparator separator1;
    private JButton back_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
