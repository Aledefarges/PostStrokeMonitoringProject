package GUI;

import org.example.Connection.Connection_Doctor;

import javax.swing.*;

public class AppFrameDoctor extends JFrame{
    private Connection_Doctor connection;
    public AppFrameDoctor() {
        connection = new Connection_Doctor();
        boolean ok = connection.connection("10.60.102.67",9000);

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
