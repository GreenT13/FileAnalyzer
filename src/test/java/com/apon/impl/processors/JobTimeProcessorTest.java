package com.apon.impl.processors;

import com.apon.framework.FinalProcessor;
import com.apon.impl.context.DataMartContext;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.CSVRecordTestHelper;

class JobTimeProcessorTest {
    private JobTimeProcessor jobTimeProcessor;
    private DataMartContext dataMartContext;

    @BeforeEach
    void setUp() {
        jobTimeProcessor = new JobTimeProcessor();
        jobTimeProcessor.setNextProcessor(new FinalProcessor<>());

        dataMartContext = Mockito.mock(DataMartContext.class);
    }

    @Test
    void shouldSetJobStartGivenStartedMessage() {
        // Given
        CSVRecord csvRecord = CSVRecordTestHelper.createCSVRecordFromString("1;2019-08-18 02:00:02.812493;Mededeling;Batch job started.;");

        // When
        jobTimeProcessor.processObject(csvRecord, dataMartContext);

        // Then
        Mockito.verify(dataMartContext).setJobStart("2019-08-18 02:00:02.812493");
    }

    @Test
    void shouldSetJobEndGivenFinishedMessage() {
        // Given
        CSVRecord csvRecord = CSVRecordTestHelper.createCSVRecordFromString("2610;2019-08-18 11:15:56.345957;Mededeling;Batch job successfully finished.;");

        // When
        jobTimeProcessor.processObject(csvRecord, dataMartContext);

        // Then
        Mockito.verify(dataMartContext).setJobEnd("2019-08-18 11:15:56.345957");
    }

    @Test
    void shouldNotTouchContextGivenRandomMessage() {
        // Given, slightly different text on the message.
        CSVRecord csvRecord = CSVRecordTestHelper.createCSVRecordFromString("2610;2019-08-18 11:15:56.345957;Mededeling;Batch job NOT finished.;");

        // When
        jobTimeProcessor.processObject(csvRecord, dataMartContext);

        // Then
        Mockito.verifyZeroInteractions(dataMartContext);
    }

}
