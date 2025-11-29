/*
 * Created by JFormDesigner on Fri Nov 28 13:01:45 CET 2025
 */

package org.example.Server.GUI;

import org.example.Server.Connection.Server;
import org.example.Server.JDBC.JDBCManager;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.*;

public class AdminPanel extends JPanel {

    private ServerSocket serverSocket;


    public AdminPanel(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        initComponents();

        label.setFont(new Font("Arial", Font.BOLD, 16));
        close_button.setFont(new Font("Arial", Font.BOLD, 16));

        close_button.setBackground(new Color(70,130,180));
        close_button.setForeground(Color.WHITE);

        setBorder(BorderFactory.createEmptyBorder(110,110,30,30));


        close_button.addActionListener(e -> {
            stopServer();
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY
        label = new JLabel();
        password_field = new JPasswordField();
        close_button = new JButton();

        //======== this ========
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {356, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

        //---- label ----
        label.setText("Enter password to close SERVER");
        add(label, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));
        add(password_field, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 0), 0, 0));

        //---- close_button ----
        close_button.setText("CLOSE");
        add(close_button, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        // JFormDesigner - End of component initialization
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY
    private JLabel label;
    private JPasswordField password_field;
    private JButton close_button;
    // JFormDesigner - End of variables declaration


    public void stopServer(){
        String password = getPassword();
        if(!password.equals("1234")){
            JOptionPane.showMessageDialog(this,"Wrong password");
            return;
        }

        int clients = Server.getActiveClientCount();
        if(clients > 0){
            int r = JOptionPane.showConfirmDialog(
                    this,
                    "There are "+clients+" clients connected.\n" +
                    "Do you want to close the server?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION);
            if(r != JOptionPane.YES_OPTION) return;
        }
        try {
            // Notify clients to close connection
            Server.broadcastShutdown();
            Thread.sleep(300);

            //Close server socket
            if(serverSocket != null && !serverSocket.isClosed()){
                serverSocket.close();
            }
            SwingUtilities.getWindowAncestor(this).dispose();

            //Force shutdown of the Server
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            //Force exit even w/ error
            System.exit(0);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPassword(){
        return new String(password_field.getText());
    }
}
