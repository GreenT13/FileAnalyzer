package com.apon.impl.processors;

import com.apon.impl.CSVMetaData;
import com.apon.impl.context.DataMartContext;
import com.apon.impl.MyUtil;
import org.apache.commons.csv.CSVRecord;

import java.util.regex.Pattern;

public class RecordsImportedProcessor extends AbstractDataMartProcessor {
    private final static String RECORDS_IMPORTED = "([\\d,]*) .* are successfully imported, [\\d,]* .* have not been imported due to errors.";
    private final static Pattern RECORDS_IMPORTED_PATTERN = Pattern.compile(RECORDS_IMPORTED);

    @Override
    public void processObject(CSVRecord object, DataMartContext context) {
        if (CSVMetaData.HeaderType.MESSAGE.isType(object) &&
                object.get(CSVMetaData.HEADER_MESSAGE).matches(RECORDS_IMPORTED)) {

            String stringRecordsImported = MyUtil.getMatchedGroupFromMessage(object, RECORDS_IMPORTED_PATTERN);
            MyUtil.getMostRecentlyAddedTask(context).nrOfRecordsImported = Integer.valueOf(stringRecordsImported.replace(",", ""));
        }

        nextProcessor.processObject(object, context);
    }
}
