package UI;

import Connection.Connection_With_Server;

import javax.swing.*;

public class AppFrame extends JFrame {
    private Connection_With_Server connection;
    public AppFrame() {
        connection = new Connection_With_Server();
        boolean ok = connection.connection("localhost",9000);

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
