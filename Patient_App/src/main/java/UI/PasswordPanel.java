/*
 * Created by JFormDesigner on Wed Nov 26 10:24:14 CET 2025
 */

package UI;

import Connection.Connection_With_Server;

import java.awt.*;
import javax.swing.*;


public class PasswordPanel extends JPanel {
    private Connection_With_Server connection;
    private AppFrame appFrame;
    public PasswordPanel(AppFrame appFrame, Connection_With_Server connection) {
        this.appFrame = appFrame;
        this.connection = connection;
        initComponents();

        setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        old_label.setFont(new Font("Arial", Font.BOLD, 14));
        new_label.setFont(new Font("Arial", Font.BOLD, 14));
        ok_button.setFont(new Font("Arial", Font.PLAIN, 14));

        ok_button.setBackground(new Color(70,130,180));
        ok_button.setForeground(Color.WHITE);

        ok_button.addActionListener(e -> changePassword());
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
        setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder
        (0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder.CENTER,javax.swing.border
        .TitledBorder.BOTTOM,new java.awt.Font("D\u0069alog",java.awt.Font.BOLD,12),java.awt
        .Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void
        propertyChange(java.beans.PropertyChangeEvent e){if("\u0062order".equals(e.getPropertyName()))throw new RuntimeException()
        ;}});
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
        add(ok_button, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    private void changePassword() {

        try{
            String old_password = old_field.getText().trim();
            String new_password = new_field.getText().trim();

            if(old_password.isEmpty() || new_password.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
                return;
            }
            if(new_password.equals(old_password)){
                JOptionPane.showMessageDialog(null, "Please enter a different password from the old one");
            }

            boolean emailOK = connection.sendChangePassword(old_password, new_password);
            if (emailOK) {
                JOptionPane.showMessageDialog(this, "Password change successful");
                appFrame.switchPanel(new PatientMenuPanel(appFrame, connection));
            }else{
                JOptionPane.showMessageDialog(this, "Invalid password");
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
