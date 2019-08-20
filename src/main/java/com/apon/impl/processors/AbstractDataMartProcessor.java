package com.apon.impl.processors;

import com.apon.framework.AbstractProcessor;
import com.apon.impl.context.DataMartContext;
import org.apache.commons.csv.CSVRecord;

@DataMartProcessor
public abstract class AbstractDataMartProcessor extends AbstractProcessor<CSVRecord, DataMartContext> implements Comparable<AbstractDataMartProcessor> {

    @Override
    public int compareTo(AbstractDataMartProcessor o) {
        return o.getClass().getAnnotation(DataMartProcessor.class).order() - this.getClass().getAnnotation(DataMartProcessor.class).order();
    }
}
