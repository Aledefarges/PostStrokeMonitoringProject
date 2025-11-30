package GUI.Panels;

import Connection.Connection_Patient;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {
    private Connection_Patient connection;
    public AppFrame() {

        setTitle("Patient App");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new IP_Panel(this, connection));
        setVisible(true);
    }

    public void switchPanel(JPanel newPanel){
        setContentPane(newPanel);
        revalidate();
        repaint();
    }
}
