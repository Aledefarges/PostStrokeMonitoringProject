package GUI.Panels;

import Connection.Connection_Patient;
import org.example.POJOS.Patient;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    private Connection_Patient connection;
    private Patient loggedPatient;
    public AppFrame() {

        setTitle("Patient App");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new IP_Panel(this));
        setVisible(true);
    }

    public Connection_Patient getConnection(){
        return connection;
    }
    public void setConnection(Connection_Patient connection){
        this.connection = connection;
    }
    public void setLoggedPatient(Patient loggedPatient){this.loggedPatient = loggedPatient;}
    public void switchPanel(JPanel newPanel){
        setContentPane(newPanel);
        revalidate();
        repaint();
    }
}
