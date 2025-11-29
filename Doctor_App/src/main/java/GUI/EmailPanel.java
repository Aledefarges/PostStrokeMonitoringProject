/*
 * Created by JFormDesigner on Wed Nov 26 09:56:34 CET 2025
 */

package GUI;


import javax.swing.*;
import java.awt.*;
import org.example.Connection.Connection_Doctor;

public class EmailPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;
    
    public EmailPanel(AppFrameDoctor appFrame) {
        this.appFrame = appFrame;
        this.connection = appFrame.getConnection();
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        old_label.setFont(new Font("Arial", Font.BOLD, 14));
        new_label.setFont(new Font("Arial", Font.BOLD, 14));
        ok_button.setFont(new Font("Arial", Font.PLAIN, 14));

        ok_button.setBackground(new Color(70,130,180));
        ok_button.setForeground(Color.WHITE);

        ok_button.addActionListener(e -> changeEmail());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        old_label = new JLabel();
        old_field = new JTextField();
        new_label = new JLabel();
        new_field = new JTextField();
        ok_button = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {121, 238, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- old_label ----
        old_label.setText("Old email:");
        add(old_label, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(old_field, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- new_label ----
        new_label.setText("New email:");
        add(new_label, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
        add(new_field, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- ok_button ----
        ok_button.setText("OK");
        add(ok_button, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void changeEmail() {

        try{
            String old_email = old_field.getText().trim();
            String new_email = new_field.getText().trim();

            if(old_email.isEmpty() || new_email.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
                return;
            }
            if(new_email.equals(old_email)){
                JOptionPane.showMessageDialog(null, "Please enter a different email from the old one");
            }

            boolean emailOK = connection.sendChangeEmail(old_email, new_email);
            if (emailOK) {
                JOptionPane.showMessageDialog(this, "Email change successful");
                appFrame.switchPanel(new DoctorMenuPanel(appFrame));
            }else{
                JOptionPane.showMessageDialog(this, "Invalid email");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Error" +e.getMessage());
        }

        
        
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel old_label;
    private JTextField old_field;
    private JLabel new_label;
    private JTextField new_field;
    private JButton ok_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    }


