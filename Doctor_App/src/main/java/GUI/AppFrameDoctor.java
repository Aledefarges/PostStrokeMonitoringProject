package GUI;

import org.example.Connection.Connection_With_Server;

import javax.swing.*;

public class AppFrameDoctor extends JFrame{
    private Connection_With_Server connection;
    public AppFrameDoctor() {
        connection = new Connection_With_Server();
        boolean ok = connection.connection("10.60.118.134",9000);

        setTitle("Patient App");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // setContentPane(new MenuPanel(this,connection));
        setVisible(true);
    }

    public void switchPanel(JPanel newPanel){
        setContentPane(newPanel);
        revalidate();
        repaint();
    }
}
