package com.apon.impl.processors;

import com.apon.framework.FinalProcessor;
import com.apon.impl.context.DataMartContext;
import com.apon.impl.context.Task;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.CSVRecordTestHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggedSpeedProcessorTest {
    private LoggedSpeedProcessor loggedSpeedProcessor;
    private DataMartContext dataMartContext;
    private Task mostRecentTask;

    @BeforeEach
    void setUp() {
        loggedSpeedProcessor = new LoggedSpeedProcessor();
        loggedSpeedProcessor.setNextProcessor(new FinalProcessor<>());

        dataMartContext = new DataMartContext();
        mostRecentTask = new Task();
        dataMartContext.getTasks().add(new Task());
        dataMartContext.getTasks().add(mostRecentTask);
    }

    @Test
    void shouldUpdateLoggedSpeedOnMostRecentTask() {
        // Given
        CSVRecord csvRecord = CSVRecordTestHelper.createCSVRecordFromString("102;2019-08-18 02:02:26.567296;Records per minuut;Speed of processing entity Party(s): 604 per minute.;");

        // When
        loggedSpeedProcessor.processObject(csvRecord, dataMartContext);

        // Then
        assertEquals(604, mostRecentTask.loggedSpeed,
                "Logged speed is not set on the most recent task.");
    }


}
