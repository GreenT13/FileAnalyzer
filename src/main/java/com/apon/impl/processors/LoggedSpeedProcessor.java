package com.apon.impl.processors;

import com.apon.impl.CSVMetaData;
import com.apon.impl.context.DataMartContext;
import com.apon.impl.MyUtil;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

public class LoggedSpeedProcessor extends AbstractDataMartProcessor {
    private final static String SPEED = "Speed of processing entity .*: ([\\d,]*) per minute\\.";
    private final static Pattern SPEED_PATTERN = Pattern.compile(SPEED);

    @Override
    public void processObject(CSVRecord object, DataMartContext context) {
        if (CSVMetaData.HeaderType.MESSAGE.isType(object) &&
                object.get(CSVMetaData.HEADER_MESSAGE).matches(SPEED)) {

            String speed = MyUtil.getMatchedGroupFromMessage(object, SPEED_PATTERN);
            MyUtil.getMostRecentlyAddedTask(context).loggedSpeed = Integer.valueOf(speed.replace(",", ""));
        }

        nextProcessor.processObject(object, context);
    }
}
