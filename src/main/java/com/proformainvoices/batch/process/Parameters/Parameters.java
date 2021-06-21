package com.proformainvoices.batch.process.Parameters;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.springframework.scheduling.annotation.Scheduled;

public class Parameters {


   // private static String PathToLogFile = "C:\\Users\\el08\\Desktop\\Proforma_Invoices_webService\\Batch\\LogicDirectory";
      private static String PathToLogFile = "\\\\192.168.90.203\\Common\\Programy\\Proforma_Invoices_webService\\Batch\\LogicDirectory";
//    private static String PathToLogFileError = "\\\\192.168.90.203\\Common\\Programy\\GTT_FAT\\ArticleExamination\\Log\\err.txt";

    private static final String CronCreationDir = "0 45 7 * * 1-5"; // 7:45 once a day MON-FRI

    //   public static String getPathToLogFileError() { return PathToLogFileError;}
    public static String getPathToLogFile() { return PathToLogFile; }
}
