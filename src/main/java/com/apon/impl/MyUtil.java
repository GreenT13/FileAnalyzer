package com.apon.impl;

import com.apon.impl.context.DataMartContext;
import com.apon.impl.context.Task;
import org.apache.commons.csv.CSVRecord;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for some common methods.
 */
public class MyUtil {

    /**
     * Get the most recently task added to {@link DataMartContext}. If expectedTaskName is given, we verify that
     * the given name equals the tasks name. If this is not the case, we throw a runtime exception.
     * @param dataMartContext The context in which the tasks are stored.
     * @param expectedTaskName (Optional) The expected name of the task.
     */
    public static Task getMostRecentlyAddedTask(DataMartContext dataMartContext, String expectedTaskName) {
        // Get the last task inserted in the list (which must be the task that just started).
        List<Task> tasks = dataMartContext.getTasks();

        if (tasks.size() == 0) {
            throw new RuntimeException("Recently added task could not be found, because no task started yet.");
        }

        Task currentTask = tasks.get(tasks.size() - 1);

        // If this is not the current task, throw an exception.
        if (expectedTaskName != null && !currentTask.name.equals(expectedTaskName)) {
            throw new RuntimeException("The most recently added task does not equal the current ending task.");
        }

        return currentTask;
    }

    /**
     * Get the most recently task added to {@link DataMartContext}.
     * @param dataMartContext The context in which the tasks are stored.
     */
    public static Task getMostRecentlyAddedTask(DataMartContext dataMartContext) {
        return getMostRecentlyAddedTask(dataMartContext, null);
    }

    /**
     * Return the first group from the regex from the message in the given record. A runtime exception
     * will be thrown if the regex doesn't match the message.
     * @param record The record from which we will read the message.
     * @param pattern The patten of which the first group will be returned.
     */
    public static String getMatchedGroupFromMessage(CSVRecord record, Pattern pattern) {
        Matcher matcher = pattern.matcher(record.get(CSVMetaData.HEADER_MESSAGE));
        if (!matcher.find()) {
            throw new RuntimeException("This should not happen.");
        }

        return matcher.group(1);
    }
}
