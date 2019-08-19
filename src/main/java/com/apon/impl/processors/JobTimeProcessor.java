package com.apon.impl.processors;

import com.apon.impl.CSVMetaData;
import com.apon.impl.context.DataMartContext;
import org.apache.commons.csv.CSVRecord;

public class JobTimeProcessor extends AbstractDataMartProcessor {
    private final static String START_JOB = "Batch job started.";
    private final static String END_JOB = "Batch job successfully finished.";

    @Override
    public void processObject(CSVRecord object, DataMartContext context) {
        if (CSVMetaData.HeaderType.MESSAGE.isType(object)) {
            String message = object.get(CSVMetaData.HEADER_MESSAGE);
            if (message.matches(START_JOB)) {
                context.setJobStart(object.get(CSVMetaData.HEADER_TS_LINE));
            } else if (message.matches(END_JOB)) {
                context.setJobEnd(object.get(CSVMetaData.HEADER_TS_LINE));
            }
        }

        nextProcessor.processObject(object, context);
    }
}
