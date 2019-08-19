package com.apon;

import com.apon.impl.DataMartParser;
import com.apon.impl.context.DataMartContext;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.Objects;

public class Main {
    public static void main(String... args)throws Exception {
        CSVPrinter csvPrinter = new CSVPrinter(new BufferedWriter(new FileWriter("target/analysis.csv")),
                CSVFormat.DEFAULT.withHeader("Name", "Start", "End", "Nr of records written", "Nr of records imported", "Logged speed"));

        File inputDirectory = new File("input");

        // Processors don't store data in their own classes (only in the context), so we don't need to recreate the chain
        // each time we process a new file. Hence, we can just instantiate the parser once, and call it for every file
        // we have in the input directory.
        DataMartParser dataMartParser = new DataMartParser();

        for (File reportFile : Objects.requireNonNull(inputDirectory.listFiles())) {
            if (reportFile.isDirectory()) {
                continue;
            }

            // One context represents one file, so we do need to create a new context.
            DataMartContext dataMartContext = new DataMartContext();

            Reader reader = new FileReader(reportFile.getAbsoluteFile());
            dataMartParser.parse(reader, dataMartContext);

            dataMartContext.writeToCSVPrinter(csvPrinter);
        }

        csvPrinter.close();
    }
}
