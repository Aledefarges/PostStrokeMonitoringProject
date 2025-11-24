package Connection;

import Bitalino.BITalino;

import javax.bluetooth.RemoteDevice;
import java.util.Vector;

/*public class bluetoothTest {
    public static void main(String[] args) throws InterruptedException {
        BITalino bita = new BITalino();
        Vector<RemoteDevice> devices = bita.findDevices();

        System.out.println("Found: " + devices.size());
        for (RemoteDevice d : devices) {
            System.out.println("Device: " + d.getBluetoothAddress());
        }

    }
}

 */


import javax.bluetooth.LocalDevice;


import Bitalino.BITalino;
import Bitalino.Frame;
import javax.bluetooth.RemoteDevice;
import java.util.Vector;

public class bluetoothTest {

    // ←--- PON AQUÍ TU MAC DEL BITALINO
    private static final String BITALINO_MAC = "20:17:11:20:50:77";

    public static void main(String[] args) {

        try {
            System.out.println("=== BITalino Bluetooth Test ===");

            // 1. Discover Bluetooth devices
            BITalino bitalino = new BITalino();
            System.out.println("Searching for Bluetooth devices...");
            Vector<RemoteDevice> devices = bitalino.findDevices();
            Thread.sleep(3000);

            if (devices.size() == 0) {
                System.out.println("No devices found.");
            } else {
                System.out.println("\nDevices found:");
                for (RemoteDevice d : devices) {
                    System.out.println(" • " + d.getBluetoothAddress());
                }
            }

            // 2. Try to connect directly to your MAC
            System.out.println("\nConnecting to BITalino at MAC: " + BITALINO_MAC);
            bitalino.open(BITALINO_MAC, 100);    // open at 100Hz

            // Read channel A1 (EMG) = index 0
            int[] channels = {0};
            bitalino.start(channels);

            System.out.println("Connected! Reading 10 samples:\n");

            // 3. Read 10 frames
            for (int i = 0; i < 30; i++) {
                Frame[] fs = bitalino.read(1);
                Frame f = fs[0];

                System.out.println(
                        "Seq: " + f.seq +
                                "  | EMG (A1): " + f.analog[0] +
                                "  | Digital: " + f.digital[0] + "," + f.digital[1] + "," + f.digital[2] + "," + f.digital[3]
                );
            }

            // 4. Stop and close
            bitalino.stop();
            bitalino.close();

            System.out.println("\n=== DONE ===");

        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
