package GUI;

import org.example.Connection.Connection_Doctor;

import javax.swing.*;

public class AppFrameDoctor extends JFrame{
    private Connection_Doctor connection;
    public AppFrameDoctor() {

        setTitle("Doctor App");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setContentPane(new IP_Panel(this, connection));
        setVisible(true);
    }

    /*public void setConnection(Connection_Doctor connection) {
        this.connection = connection;
        // Con esta función el AppFrame guarda la conexión para los demás paneles
    }

    public Connection_Doctor getConnection() {
        return this.connection;
        // Sirve para dar la conexión que usa la app, la que se creó al introducir el IP
    }

     */

    public void switchPanel(JPanel newPanel){
        setContentPane(newPanel);
        revalidate();
        repaint();
    }
}
