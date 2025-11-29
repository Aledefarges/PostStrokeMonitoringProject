/*
 * Created by JFormDesigner on Tue Nov 25 23:16:23 CET 2025
 */

package GUI;


import org.example.Connection.Connection_Doctor;


import javax.swing.*;
import java.awt.*;


public class MenuPanel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;

    public MenuPanel(AppFrameDoctor appFrame) {
        this.appFrame = appFrame;
        this.connection = appFrame.getConnection();
        initComponents();
        
        title.setFont(new Font("Arial", Font.BOLD, 20));
        login_button.setFont(new Font("Arial", Font.PLAIN, 14));
        register_button.setFont(new Font("Arial", Font.PLAIN, 14));
        exit_button.setFont(new Font("Arial", Font.PLAIN, 14));
        
        login_button.setBackground(new Color(70,130,180));
        login_button.setForeground(Color.WHITE);
        register_button.setBackground(new Color(70, 130, 180));
        register_button.setForeground(Color.WHITE);
        exit_button.setBackground(new Color(62, 156, 118));
        exit_button.setForeground(Color.WHITE);
        separator1.setForeground(new Color(70,130,180));

        setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        login_button.addActionListener(e-> goToLogin());
        register_button.addActionListener(e-> goToRegister());
        exit_button.addActionListener(e-> System.exit(0));
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        title = new JLabel();
        separator1 = new JSeparator();
        login_button = new JButton();
        register_button = new JButton();
        exit_button = new JButton();

        //======== this ========

        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {404, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- title ----
        title.setText("WELCOME DOCTOR TO POST-STROKE MONITORING APP");
        add(title, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        add(separator1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- login_button ----
        login_button.setText("1. LOG IN");
        add(login_button, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- register_button ----
        register_button.setText("2. REGISTER");
        add(register_button, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- exit_button ----
        exit_button.setText("3. EXIT");
        add(exit_button, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }
    private void goToLogin(){
        appFrame.switchPanel(new LoginPanel(appFrame));
    }
    private void goToRegister(){
        appFrame.switchPanel(new RegisterDoctorPanel(appFrame));
    }
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel title;
    private JComponent separator1;
    private JButton login_button;
    private JButton register_button;
    private JButton exit_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
