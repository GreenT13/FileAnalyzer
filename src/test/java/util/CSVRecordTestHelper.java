package util;

import com.apon.impl.CSVMetaData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVRecordTestHelper {

    public static CSVRecord createCSVRecordFromString(String content) {
        StringReader stringReader = new StringReader(content);
        try {
            CSVParser csvParser = new CSVParser(stringReader, CSVFormat.DEFAULT
                    .withDelimiter(';')
                    .withIgnoreEmptyLines()
                    .withHeader(CSVMetaData.HEADER_FILE_LINE.split(";")));

            return csvParser.getRecords().get(0);
        } catch (IOException e) {
            throw new RuntimeException("CSVRecordTestHelper#createCSVRecordFromString failed for some reason, that should not happen.");
        }
    }

    @Test
    void csvRecordShouldContainCorrectValues() {
        // Given
        String lineNumber = "1";
        String tsLine = "2019-08-18 02:00:02.812493";
        String type = "Mededeling";
        String message = "Batch job started.";
        String reference = "1337";
        String input = lineNumber + ";" + tsLine + ";" + type + ";" + message + ";" + reference;

        // When
        CSVRecord csvRecord = CSVRecordTestHelper.createCSVRecordFromString(input);

        // Then
        assertEquals(lineNumber, csvRecord.get(CSVMetaData.HEADER_LINE_NUMBER),
                "The line number is incorrectly set on the csvRecord.");
        assertEquals(tsLine, csvRecord.get(CSVMetaData.HEADER_TS_LINE),
                "The tsLine is incorrectly set on the csvRecord.");
        assertEquals(type, csvRecord.get(CSVMetaData.HEADER_TYPE),
                "The type is incorrectly set on the csvRecord.");
        assertEquals(message, csvRecord.get(CSVMetaData.HEADER_MESSAGE),
                "The type is incorrectly set on the csvRecord.");
        assertEquals(reference, csvRecord.get(CSVMetaData.HEADER_REFERENCE),
                "The type is incorrectly set on the csvRecord.");
    }
}
