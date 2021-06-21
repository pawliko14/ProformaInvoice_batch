package com.proformainvoices.batch.process.Logic;

import com.proformainvoices.batch.process.Parameters.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class FolderCreator {

    public void CreateDir() throws IOException {

        String PathToCreateDir = Parameters.getPathToLogFile();

        String path = String.valueOf(Paths.get(PathToCreateDir, getCurrentDate()));

        File directory = new File(path);
        if(!directory.exists()) {
            Files.createDirectories(Paths.get(PathToCreateDir,getCurrentDate()));
        }
        else {
            log.info("directory, file already exists");
            log.info("existing directory path : " + path);

        }

    }

    private String getCurrentDate() {
        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormat = format.format(data);

        return dataFormat;
    }

}
