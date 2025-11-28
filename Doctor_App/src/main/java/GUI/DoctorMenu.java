package GUI;



import org.example.POJOS.Patient;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class DoctorMenu {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppFrameDoctor();
            }
        });
    }
}





