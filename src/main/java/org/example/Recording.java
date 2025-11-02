package org.example;

import java.time.LocalDate;

public class Recording {
    private int id;
    private LocalDate dateRecording;
    private final int max_duration = 60;
    private Type type;

    public Recording(int id, LocalDate dateRecording, Type type) {
        this.id = id;
        this.dateRecording = dateRecording;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDateRecording() {
        return dateRecording;
    }

    public int getMax_duration() {
        return max_duration;
    }

    public Type getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateRecording(LocalDate dateRecording) {
        this.dateRecording = dateRecording;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Recording{" +
                "id=" + id +
                ", dateRecording=" + dateRecording +
                ", max_duration=" + max_duration +
                ", type=" + type +
                '}';
    }

    public enum Type{
        ECG,EMG;
    }
}

