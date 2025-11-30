package GUI;

import GUI.Panels.AppFrame;

import javax.swing.*;

public class RunnablePatient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppFrame();
            }
        });
    }
}
