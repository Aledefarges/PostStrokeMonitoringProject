
package GUI.Panels;

import java.awt.*;
import javax.swing.*;

import Connection.Connection_Patient;
import com.jgoodies.forms.factories.*;




public class PatientMenuPanel extends JPanel {
    private Connection_Patient connection;
    private AppFrame appFrame;
    
    public PatientMenuPanel(AppFrame appFrame) {
        this.appFrame = appFrame;
        this.connection = appFrame.getConnection();
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(40, 110, 30, 30));

        title1.setText("Patient Menu");
        title1.setFont(new Font("Arial", Font.BOLD, 20)); 
        select_label.setFont(new Font("Arial", Font.BOLD, 16));
        recording_button.setFont(new Font("Arial", Font.PLAIN, 14));
        feedback_button.setFont(new Font("Arial", Font.PLAIN, 14));
        email_button.setFont(new Font("Arial", Font.PLAIN, 14));
        password_button.setFont(new Font("Arial", Font.PLAIN, 14));
        update_button.setFont(new Font("Arial", Font.PLAIN, 14));
        delete_button.setFont(new Font("Arial", Font.PLAIN, 14));
        exit_button.setFont(new Font("Arial", Font.PLAIN, 14));
        
        separator1.setBackground(new Color(70,130,180));
        recording_button.setBackground(new Color(70,130,180));
        recording_button.setForeground(Color.white);
        feedback_button.setBackground(new Color(70,130,180));
        feedback_button.setForeground(Color.white);
        email_button.setBackground(new Color(70,130,180));
        email_button.setForeground(Color.white);
        password_button.setBackground(new Color(70,130,180));
        password_button.setForeground(Color.white);
        update_button.setBackground(new Color(70,130,180));
        update_button.setForeground(Color.white);
        delete_button.setBackground(new Color(70,130,180));
        delete_button.setForeground(Color.white);
        exit_button.setBackground(new Color(62,156,118));
        exit_button.setForeground(Color.white);

        recording_button.addActionListener(e-> goToRecording());
        feedback_button.addActionListener(e-> goToFeedback());
        email_button.addActionListener(e-> goToChangeEmail());
        password_button.addActionListener(e-> goToChangePassword());
        update_button.addActionListener(e-> goToUpdate());
        delete_button.addActionListener(e-> goToDelete());
        exit_button.addActionListener(e->{
            connection.close();
            System.exit(0);
        } );
        
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        DefaultComponentFactory compFactory = DefaultComponentFactory.getInstance();
        title1 = compFactory.createTitle("Patient Menu");
        separator1 = compFactory.createSeparator("");
        select_label = new JLabel();
        recording_button = new JButton();
        feedback_button = new JButton();
        email_button = new JButton();
        password_button = new JButton();
        update_button = new JButton();
        delete_button = new JButton();
        exit_button = new JButton();

        //======== this ========
        setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax
        . swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing
        . border. TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .
        Font ("D\u0069alog" ,java .awt .Font .BOLD ,12 ), java. awt. Color. red
        ) , getBorder( )) );  addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override
        public void propertyChange (java .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName (
        ) )) throw new RuntimeException( ); }} );
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {327, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 25, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        add(title1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        add(separator1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- select_label ----
        select_label.setText("Select an option");
        add(select_label, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- recording_button ----
        recording_button.setText("1. Start recording");
        add(recording_button, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- feedback_button ----
        feedback_button.setText("2. Observe feedback from doctor");
        add(feedback_button, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- email_button ----
        email_button.setText("3.  Change email");
        add(email_button, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- password_button ----
        password_button.setText("4. Change password");
        add(password_button, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- update_button ----
        update_button.setText("5. Update information");
        add(update_button, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- delete_button ----
        delete_button.setText("6. Delete account ");
        add(delete_button, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- exit_button ----
        exit_button.setText("EXIT");
        add(exit_button, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void goToChangeEmail() {
        appFrame.switchPanel(new EmailPanel(appFrame));
    }
    private void goToChangePassword() {
       appFrame.switchPanel(new PasswordPanel(appFrame));
    }
    private void goToUpdate() {
       appFrame.switchPanel(new UpdatePanel(appFrame));
    }
    private void goToFeedback(){appFrame.switchPanel(new FeedbackPatientPanel(appFrame));}
    private void goToDelete() {
       connection.deletePatientFromServer();
        appFrame.switchPanel(new MenuPanel(appFrame));
    }
    private void goToRecording() {
        appFrame.switchPanel(new RecordingPanel(appFrame));
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel title1;
    private JComponent separator1;
    private JLabel select_label;
    private JButton recording_button;
    private JButton feedback_button;
    private JButton email_button;
    private JButton password_button;
    private JButton update_button;
    private JButton delete_button;
    private JButton exit_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
