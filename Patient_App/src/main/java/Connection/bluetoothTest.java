package Connection;

import Bitalino.BITalino;

import javax.bluetooth.RemoteDevice;
import java.util.Vector;

public class bluetoothTest {
    public static void main(String[] args) throws InterruptedException {
        BITalino bita = new BITalino();
        Vector<RemoteDevice> devices = bita.findDevices();

        System.out.println("Found: " + devices.size());
        for (RemoteDevice d : devices) {
            System.out.println("Device: " + d.getBluetoothAddress());
        }

    }
}
