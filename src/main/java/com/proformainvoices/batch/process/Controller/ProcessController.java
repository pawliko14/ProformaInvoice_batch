package com.proformainvoices.batch.process.Controller;

import com.proformainvoices.batch.process.JDBCTemplateImplementation.ImplProformaRepositoryJDBCTemplate;
import com.proformainvoices.batch.process.Logic.FileReading;
import com.proformainvoices.batch.process.Logic.FolderCreator;
import com.proformainvoices.batch.process.model.Proforma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
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
     *  creation of folder should start at 6:30 each day except weekends
     *
     * @throws IOException
     */
    @Scheduled(cron = "30 06 * * 1-5 *")
    public void doScheduledDirctoryCreation() throws IOException {
        folderCreator.CreateDir();

        System.out.println("dir created");
    }

    @Scheduled(cron = "*/15 * * * * *")
    public void doFiltrationOnDatabase() throws IOException {
        fileReading.setFileNameDirectoryName();
        List<String> listOfFilesToRead = fileReading.getCompletePaths();

        for(String Paths : listOfFilesToRead) {

            List<String> linesFromFile = fileReading.readProformaRecordsFromFile(Paths);
            //     linesFromFile.forEach(System.out::println);


            // finding all records in Proforma table
            List<Proforma> allProformaRecords = implProformaRepositoryJDBCTemplate.findAll();

            //find all documents numbers
            List<String> listOfDocumentNumbers = allProformaRecords.stream()
                    .map(s -> s.getDocumentNumber())
                    .collect(Collectors.toList());

//        System.out.println("list of document numbers");
//        listOfDocumentNumbers.forEach(System.out::println);


            // compare data from database and taken from file, matching records assign to list
            List<String> collectedMatchingRecords = listOfDocumentNumbers.stream()
                    .filter(element -> linesFromFile.contains(element))
                    .collect(Collectors.toList());

//        System.out.println("collected matching records");
//        collectedMatchingRecords.forEach(System.out::println);

            if (collectedMatchingRecords.size() != 0) {
                for (String s : collectedMatchingRecords) {
                    Proforma recordByDocumentNumber = implProformaRepositoryJDBCTemplate.findRecordByDocumentNumber(s);

                    // now, if proforma exists it must be updated as paid with current date
                    if (recordByDocumentNumber.isPaid() != true) {
                        recordByDocumentNumber.setPaid(true);
                        implProformaRepositoryJDBCTemplate.updatePaid(recordByDocumentNumber);
                        System.out.println("record updated" + recordByDocumentNumber);
                    } else {
                        System.out.println("Cannot do operations, CollectedMatchingRecords returns 0");
                    }
                }
            }

        }


    }
}
