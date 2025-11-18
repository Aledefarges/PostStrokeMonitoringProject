package org.example.Server.Utilities;

import java.io.IOException;
import java.nio.file.Files;

public class SaveRcordings {
    private static final String CSV_FOLDER = "recordings_csv/";

    public SaveRcordings() {
        try{
            Files.createDirectories(Path.of(CSV_FOLDER))
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
