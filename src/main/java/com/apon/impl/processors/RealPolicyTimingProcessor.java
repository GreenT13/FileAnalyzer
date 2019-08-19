package com.apon.impl.processors;

import com.apon.impl.CSVMetaData;
import com.apon.impl.context.DataMartContext;
import org.apache.commons.csv.CSVRecord;

public class RealPolicyTimingProcessor extends AbstractDataMartProcessor {
    private final static String REAL_START_POLICY = "The task 'Task prepare import of policies into Data Mart.' started.";
    private final static String REAL_END_POLICY = "The task 'Import policies to MIS' ended.";

    @Override
    public void processObject(CSVRecord object, DataMartContext context) {
        if (CSVMetaData.HeaderType.MESSAGE.isType(object)) {
            String message = object.get(CSVMetaData.HEADER_MESSAGE);
            if (message.matches(REAL_START_POLICY)) {
                context.setRealPolicyStart(object.get(CSVMetaData.HEADER_TS_LINE));
            } else if (message.matches(REAL_END_POLICY)) {
                context.setRealPolicyEnd(object.get(CSVMetaData.HEADER_TS_LINE));
            }
        }

        nextProcessor.processObject(object, context);
    }
}
