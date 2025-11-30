package Bitalino;

import javax.bluetooth.RemoteDevice;
import java.util.Vector;

public class BITalinoCommunicationTest {
    private static final String BITALINO_MAC = "98:D3:51:FD:9C:72";

    public static void main(String[] args){
        BITalino bitalino = null;

        try{
           // 1. Discover Bluetooth devices
            bitalino = new BITalino();
            System.out.println("Searching Bluetooth devices...");
            Vector<RemoteDevice> devices = bitalino.findDevices();

            if (devices.size() == 0){
                System.out.println("No devices found.");
            }else{
                System.out.println("Devices found: ");
                for(RemoteDevice d : devices){
                    System.out.println("- " + d.getBluetoothAddress());
                }
            }

            //2. Connect to specific BITalino
            System.out.println("Connecting to BITalino: " + BITALINO_MAC);
            bitalino.open(BITALINO_MAC, 100); //100Hz
            System.out.println("BITalino connected.");

            //3. Start acquisition on A1 (EMG)
            int[] channels = {0};
            bitalino.start(channels);
            System.out.println("Acquisition started.");

            //4. Read 50 samples
            System.out.println("Reading 50 frames");
            for(int i = 0; i < 50; i++){
                Frame[] frame = bitalino.read(1);
                Frame f = frame[0];

                System.out.println("Seq: " + f.seq +
                        " | A1: "+ f.analog[0]+
                        " |Digital: " + f.digital[0] + "," +
                        f.digital[1] + "," + f.digital[2] + "," + f.digital[3]);
            }

            //5. Stop and close
            bitalino.stop();
            bitalino.close();

            System.out.println("Test finished.");
        }catch (Throwable e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
