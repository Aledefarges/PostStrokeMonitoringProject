package org.example.Server.Utilities;

import org.example.Server.POJOS.Recording;
import Patien_App;

import java.util.List;
import java.util.logging.Logger;

public class BitalinoService {
    private static final Logger LOGGER = Logger.getLogger(BitalinoService.class.getName());

    //MAC del Bitalino
    private static final String BITALINO_MAC = "20:17:11:20:50:77";

    //Acquire a signal and return the recordings
    public List<Integer> acquireSignal(Recording.Type type, int samplingRate, int durationSecs) throws BitalinoException{
        BITalino bitalino = null;
    }
}
