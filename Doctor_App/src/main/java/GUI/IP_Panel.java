/*
 * Created by JFormDesigner on Fri Nov 28 17:53:58 CET 2025
 */

package GUI;

import org.example.Connection.Connection_Doctor;

import javax.swing.*;
import java.awt.*;

public class IP_Panel extends JPanel {
    private Connection_Doctor connection;
    private AppFrameDoctor appFrame;

    public IP_Panel(AppFrameDoctor appFrame, Connection_Doctor connection) {
        this.appFrame = appFrame;
        this.connection = connection;
        initComponents();
        setBorder(BorderFactory.createEmptyBorder(60,100,40,40));

        label1.setFont(new Font("Arial", Font.BOLD, 14));
        label2.setFont(new Font("Arial", Font.BOLD, 14));

        done_button.setFont(new Font("Arial", Font.PLAIN, 14));

        done_button.setBackground(new Color(70,130,180));
        done_button.setForeground(Color.WHITE);



        introIP();
    }



    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - Nerea Leria
        label1 = new JLabel();
        label2 = new JLabel();
        ip_field = new JTextField();
        done_button = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {292, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //---- label1 ----
        label1.setText("Indicate the IP of server");
        add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- label2 ----
        label2.setText("(Example: 192.168.001.001)");
        add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        add(ip_field, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- done_button ----
        done_button.setText("DONE");
        add(done_button, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    public void  introIP(){
        done_button.addActionListener(e ->{
            String ip = ip_field.getText().trim();

            if(ip.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter IP address");
                return;
            }
            connection = new Connection_Doctor();

            if (connection.connection(ip, 9000)) {

                // Aquí se guarda la conexión en AppFrame
                //appFrame.setConnection(connection);
                appFrame.switchPanel(new MenuPanel(appFrame, connection));
            } else {
                JOptionPane.showMessageDialog(this,
                        "Could not connect to server."
                );
            }
        });
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - Nerea Leria
    private JLabel label1;
    private JLabel label2;
    private JTextField ip_field;
    private JButton done_button;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
