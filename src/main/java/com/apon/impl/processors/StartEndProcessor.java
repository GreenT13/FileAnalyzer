package com.apon.impl.processors;

import com.apon.impl.CSVMetaData;
import com.apon.impl.context.DataMartContext;
import com.apon.impl.context.Task;
import com.apon.impl.MyUtil;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

@DataMartProcessor(order = 0)
public class StartEndProcessor extends AbstractDataMartProcessor {

    private final static String REGEX_START_TASK = "The task '(.*)' started\\.";
    private final static String REGEX_END_TASK = "The task '(.*)' ended\\.";
    private final static Pattern PATTERN_START_TASK = Pattern.compile(REGEX_START_TASK);
    private final static Pattern PATTERN_END_TASK = Pattern.compile(REGEX_END_TASK);

    @Override
    public void processObject(CSVRecord object, DataMartContext context) {
        if (CSVMetaData.HeaderType.MESSAGE.isType(object)) {
            String message = object.get(CSVMetaData.HEADER_MESSAGE);
            if (message.matches(REGEX_START_TASK)) {
                processStartTask(object, context);
            } else if (message.matches(REGEX_END_TASK)) {
                processEndTask(object, context);
            }
        }

        nextProcessor.processObject(object, context);
    }

    private void processStartTask(CSVRecord record, DataMartContext dataMartContext) {
        String taskName = MyUtil.getMatchedGroupFromMessage(record, PATTERN_START_TASK);
        Task task = new Task();
        task.name = taskName;
        task.tsStart = record.get(CSVMetaData.HEADER_TS_LINE);

        dataMartContext.getTasks().add(task);
    }

    private void processEndTask(CSVRecord record, DataMartContext dataMartContext) {
        Task currentTask = MyUtil.getMostRecentlyAddedTask(dataMartContext, MyUtil.getMatchedGroupFromMessage(record, PATTERN_END_TASK));

        // Set the end timing on the time of the record.
        currentTask.tsEnd = record.get(CSVMetaData.HEADER_TS_LINE);
    }
}
