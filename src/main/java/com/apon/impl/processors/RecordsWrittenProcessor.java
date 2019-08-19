package com.apon.impl.processors;

import com.apon.impl.CSVMetaData;
import com.apon.impl.context.DataMartContext;
import com.apon.impl.context.Task;
import com.apon.impl.MyUtil;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

public class RecordsWrittenProcessor extends AbstractDataMartProcessor {

    private final static String RECORDS_WRITTEN = "In total ([\\d,]*) .* are imported into Data Mart within this task.";
    private final static Pattern RECORDS_WRITTEN_PATTERN = Pattern.compile(RECORDS_WRITTEN);

    @Override
    public void processObject(CSVRecord object, DataMartContext context) {
        if (CSVMetaData.HeaderType.RECORDS_PER_MINUTE.isType(object)
                && object.get(CSVMetaData.HEADER_MESSAGE).matches(RECORDS_WRITTEN)) {
            Task currentTask = MyUtil.getMostRecentlyAddedTask(context);
            currentTask.nrOfRecordsWritten = Integer.valueOf(MyUtil.getMatchedGroupFromMessage(object, RECORDS_WRITTEN_PATTERN));
        }

        nextProcessor.processObject(object, context);
    }
}
