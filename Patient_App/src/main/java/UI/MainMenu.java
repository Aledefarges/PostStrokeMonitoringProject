package UI;

import javax.swing.*;

public class MainMenu {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppFrame();
            }
        });
    }
}
