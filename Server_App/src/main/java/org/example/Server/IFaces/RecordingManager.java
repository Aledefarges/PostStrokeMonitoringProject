package org.example.Server.IFaces;

import org.example.POJOS.Recording;

public interface RecordingManager {
    public void addRecording(Recording recording);
    public void deleteRecording (Integer recording_id);
    //public Recording getRecordingById(Integer recording_id);
}
