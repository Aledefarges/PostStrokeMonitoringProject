package GUI;



import GUI.Panels.AppFrameDoctor;

import javax.swing.*;

public class DoctorMenu {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppFrameDoctor();
            }
        });
    }
}





