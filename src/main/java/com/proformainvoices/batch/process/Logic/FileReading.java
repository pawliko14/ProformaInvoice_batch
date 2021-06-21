package com.proformainvoices.batch.process.Logic;

import com.proformainvoices.batch.process.Parameters.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileReading {

    private String pathToDirectory;
    private List<String> completePathsToFIle;
    private String directoryName;

    public FileReading() {
        pathToDirectory = Parameters.getPathToLogFile();
    }

    public void setFileNameDirectoryName(){
        directoryName = getDirectoryName();
        completePathsToFIle = getCompletePathsToFIle();
      //  getCompletePathsToFIle();
    }
    
    public List<String> getCompletePaths(){
        if(this.completePathsToFIle == null || this.completePathsToFIle.size()==0){
            log.debug("cannot return empty path to file - return ''");
            //System.out.println("cannot return empty path to file return '' ");
            return new ArrayList<>();
        }
        return completePathsToFIle;
    }


    /**
     * should read for file name like :
     * statement_XXXXXXXX.sta
     *
     * statement could start with capitalized 's'
     * .sta extension is constant
     *
     * @return
     */
    private List<String> getCompletePathsToFIle() {

        //String returningString = "-1";
        List<String> returningString = new ArrayList<>();
        File root = new File(this.pathToDirectory + "\\" +   directoryName);

        FilenameFilter fileBeginWIth = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                 name =  name.toUpperCase();
                return name.startsWith("STATEMENT");
            }
        };

        File[] files = root.listFiles(fileBeginWIth);

        if(files.length == 0) {
            System.out.println("there is no statement_xxxxxx.sta file in "  + root);
            return returningString;
        }

        for(File f : files) {
            returningString.add(f.toString());
        }


        return returningString; // should return only very first one
    }

    /**
     * should return name of Directory with current date
     * SimpleDataFormat for directory to read:
     *
     * YYYY-MM-dd  -> 2021-06-08
     *
     * Dir will be CRONed to create one per day
     * so there should not be repititions
     *
     * @return
     */
    private String getDirectoryName(){
        return getCurrentDate();
    }

    private String getCurrentDate() {
        Date data = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormat = format.format(data);

        return dataFormat;
    }

    public List<String> readProformaRecordsFromFile(String file) throws IOException {

        if(this.completePathsToFIle.equals("-1") || this.completePathsToFIle == null) {
            return new ArrayList<>();
        }

      //  String pathTofile = this.completePathsToFIle;
        String pathTofile = file;
        List<String> lines = new ArrayList<>();
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(pathTofile));
            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        } catch(IOException e) {
            System.out.println("cannot read from file " + e);
        }
        finally {
            reader.close();
        }
        if(!lines.isEmpty()){
                lines.removeIf(s->!s.startsWith("~20PF"));
        }


        //remove "~20" from each string
        List<String> trimmedList = lines.stream()
                .map(s -> s.substring(3, s.length()))
                .collect(Collectors.toList());

        // remove leading or tailing zeros
        trimmedList.forEach(s->s.strip());


        return trimmedList;
    }
}
