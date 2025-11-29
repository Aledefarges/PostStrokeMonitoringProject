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
        JPanel wrapper = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        wrapper.add(newPanel,gbc);   // Sirve para centrar todo el contenido
        setContentPane(newPanel);
        revalidate();
        repaint();
    }
}
