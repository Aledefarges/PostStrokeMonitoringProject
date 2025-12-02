package GUI.Panels;

import Connection.Connection_Doctor;

import javax.swing.*;

public class AppFrameDoctor extends JFrame{
    private Connection_Doctor connection;
    public AppFrameDoctor() {

        setTitle("Doctor App");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new IP_Panel(this));
        setVisible(true);
    }

    public Connection_Doctor getConnection(){
        return connection;
    }
    public void setConnection(Connection_Doctor connection){
        this.connection = connection;
    }


    public void switchPanel(JPanel newPanel){
        setContentPane(newPanel);
        revalidate();
        repaint();
    }
}
