package com.apon.impl;

import com.apon.impl.context.DataMartContext;
import com.apon.impl.context.Task;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.CSVRecordTestHelper;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

class MyUtilTest {
    private DataMartContext dataMartContext;

    @BeforeEach
    void setUp() {
        dataMartContext = new DataMartContext();
    }

    @Test
    void shouldReturnFirstRegexGroupFromMessageOfCSVRecord() {
        // Given
        CSVRecord csvRecord = CSVRecordTestHelper.createCSVRecordFromString("2240;2019-08-19 08:03:06.610731;Mededeling;The task 'Import CDS claim data to MIS' ended.;");
        Pattern patternTaskName = Pattern.compile("The task '(.*)' (ended)\\.");

        // When
        String result = MyUtil.getMatchedGroupFromMessage(csvRecord, patternTaskName);

        // Then
        assertEquals("Import CDS claim data to MIS", result,
                "The first regex group is not returned as a result.");
    }

    @Test
    void shouldReturnMostRecentTaskFromContext() {
        // Given
        Task task1 = new Task();
        Task task2 = new Task();
        dataMartContext.getTasks().add(task1);
        dataMartContext.getTasks().add(task2);

        // When
        Task foundTask = MyUtil.getMostRecentlyAddedTask(dataMartContext);

        // Then
        assertEquals(task2, foundTask, "The task returned");
    }

    @Test
    void shouldThrowRuntimeExceptionWhenWhenTaskListIsEmpty() {
        // When and then for function without task name.
        assertThrows(RuntimeException.class, () -> MyUtil.getMostRecentlyAddedTask(dataMartContext),
                "RuntimeException should be thrown when the list of tasks is empty, but this is not the case.");

        // When and then for function with task name.
        assertThrows(RuntimeException.class, () -> MyUtil.getMostRecentlyAddedTask(dataMartContext, anyString()),
                "RuntimeException should be thrown when the list of tasks is empty, but this is not the case.");
    }

    @Test
    void shouldThrowRuntimeExceptionWhenTaskNameIsIncorrect() {
        // Given
        Task task = new Task();
        task.name = "ANY_NAME";
        dataMartContext.getTasks().add(task);

        // When and then
        assertThrows(RuntimeException.class, () -> MyUtil.getMostRecentlyAddedTask(dataMartContext, "NOT_ANY_NAME"),
                "RuntimeException should be thrown because the given name does not match the name of the most recent task" +
                        ", but this is not the case.");
    }
}
