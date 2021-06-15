package com.proformainvoices.batch.process.Logic;

import com.proformainvoices.batch.process.Parameters.Parameters;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FolderCreator {

    public void CreateDir() throws IOException {

        String PathToCreateDir = Parameters.getPathToLogFile();


        Files.createDirectories(Paths.get(PathToCreateDir,getCurrentDate()));
    }

    private String getCurrentDate() {
        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormat = format.format(data);

        return dataFormat;
    }

}
