package UI.GUI;

import Connection.Connection_Patient;

import javax.swing.*;

public class AppFrame extends JFrame {
    private Connection_Patient connection;
    public AppFrame() {
        connection = new Connection_Patient();
        connection.connection("10.60.102.67",9000);

        setTitle("Patient App");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new MenuPanel(this,connection));
        setVisible(true);
    }

    public void switchPanel(JPanel newPanel){
        setContentPane(newPanel);
        revalidate();
        repaint();
    }
}
