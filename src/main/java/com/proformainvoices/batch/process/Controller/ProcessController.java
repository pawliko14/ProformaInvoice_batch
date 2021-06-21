package com.proformainvoices.batch.process.Controller;

import com.proformainvoices.batch.process.JDBCTemplateImplementation.ImplProformaRepositoryJDBCTemplate;
import com.proformainvoices.batch.process.Logic.FileReading;
import com.proformainvoices.batch.process.Logic.FolderCreator;
import com.proformainvoices.batch.process.model.Proforma;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ProcessController {



    @Autowired
    final FolderCreator folderCreator;

    @Autowired
    final FileReading fileReading;

    @Autowired
    final ImplProformaRepositoryJDBCTemplate implProformaRepositoryJDBCTemplate;

    public ProcessController(FolderCreator folderCreator, FileReading fileReader, ImplProformaRepositoryJDBCTemplate implProformaRepositoryJDBCTemplate) {
        this.folderCreator = folderCreator;
        this.fileReading = fileReader;
        this.implProformaRepositoryJDBCTemplate = implProformaRepositoryJDBCTemplate;
    }

    /**
     *  CRON table "30 06 * * 1-5 *"
     *  creation of folder should start at 6:00 each day except weekends
     *
     * @throws IOException
     */
    @Scheduled(cron ="0 30 6 * * 1-5", zone = "GMT+2:00")
    public void doScheduledDirctoryCreation() throws IOException {

        System.out.println("dir creation");
        folderCreator.CreateDir();

        System.out.println("dir created3");
    }

    /**
     *  CRON table " 0 0 10 * * 1-5"
     *  do analitics based on file .sta files in current direcotry
     *
     * @throws IOException
     */
    @Scheduled(cron = "0 0 10 * * 1-5" , zone = "GMT+2:00")
    public void doFiltrationOnDatabase() throws IOException {
        fileReading.setFileNameDirectoryName();
        List<String> listOfFilesToRead = fileReading.getCompletePaths();

        for(String Paths : listOfFilesToRead) {

            List<String> linesFromFile = fileReading.readProformaRecordsFromFile(Paths);
            log.info("lines from file");
            linesFromFile.forEach(log::info);


            // finding all records in Proforma table
            List<Proforma> allProformaRecords = implProformaRepositoryJDBCTemplate.findAll();

            //find all documents numbers
            List<String> listOfDocumentNumbers = allProformaRecords.stream()
                    .map(s -> s.getDocumentNumber())
                    .collect(Collectors.toList());

            log.info("list of document numbers");
            listOfDocumentNumbers.forEach(log::info);


            // compare data from database and taken from file, matching records assign to list
            List<String> collectedMatchingRecords = listOfDocumentNumbers.stream()
                    .filter(element -> linesFromFile.contains(element))
                    .collect(Collectors.toList());

        log.info("collected matching records");
        collectedMatchingRecords.forEach(log::info);

            if (collectedMatchingRecords.size() != 0) {
                for (String s : collectedMatchingRecords) {
                    Proforma recordByDocumentNumber = implProformaRepositoryJDBCTemplate.findRecordByDocumentNumber(s);

                    // now, if proforma exists it must be updated as paid with current date
                    if (recordByDocumentNumber.isPaid() != true) {
                        recordByDocumentNumber.setPaid(true);
                        implProformaRepositoryJDBCTemplate.updatePaid(recordByDocumentNumber);
                        System.out.println("record updated" + recordByDocumentNumber);
                    } else {
                        log.info("Cannot do operations, CollectedMatchingRecords returns 0");
                    }
                }
            }

        }


    }
}
