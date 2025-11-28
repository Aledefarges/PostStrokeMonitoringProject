package org.example.Server.IFaces;


import org.example.POJOS.Recording;

import java.util.List;

public interface RecordingManager {
    public void addRecording(Recording recording);
    public boolean deleteRecording (int recording_id);
    public Recording getRecordingById(Integer recording_id);
    public List<Recording> getRecordingsByPatient(int patient_id);
}
