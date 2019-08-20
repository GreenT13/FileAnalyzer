package com.apon.impl;

import com.apon.framework.FinalProcessor;
import com.apon.framework.IProcessor;
import com.apon.framework.ProcessorChain;
import com.apon.impl.context.DataMartContext;
import com.apon.impl.processors.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * The DataMartParser fills a {@link DataMartContext} with data created by processors.
 * All the processors for this parser extends {@link AbstractDataMartProcessor}.
 */
public class DataMartParser {

    private static List<Class<? extends IProcessor<CSVRecord, DataMartContext>>> processorClasses;

    static {
        Reflections reflections = new Reflections("com.apon.impl.processors");
        List<Class<? extends AbstractDataMartProcessor>> processors = new ArrayList<>(reflections.getSubTypesOf(AbstractDataMartProcessor.class));
        processorClasses = new ArrayList<>();
        for (Class<?> processorClass : processors) {
            @SuppressWarnings("unchecked")
            Class<? extends IProcessor<CSVRecord, DataMartContext>> realProcessorClass = (Class<? extends IProcessor<CSVRecord, DataMartContext>>) processorClass;
            if (Modifier.isAbstract(realProcessorClass.getModifiers())) {
                continue;
            }
            processorClasses.add(realProcessorClass);
        }
    }

    private final IProcessor<CSVRecord, DataMartContext> chain;

    public DataMartParser() {
        try {
            chain = createChain(processorClasses);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not instantiate chain.");
        }
    }

    /**
     * Execute all processors on the Data Mart report.
     * @param reader The report.
     * @param dataMartContext The context.
     */
    public void parse(Reader reader, DataMartContext dataMartContext) throws IOException {
        for (CSVRecord csvRecord : createCSVParser(reader)) {
            chain.processObject(csvRecord, dataMartContext);
        }
    }

    /**
     * Create a CSVParser with the correct settings for a Data Mart report file.
     * @param reader The file to read.
     */
    private CSVParser createCSVParser(Reader reader) throws IOException {
        return new CSVParser(reader, CSVFormat.DEFAULT
                .withDelimiter(';')
                .withIgnoreEmptyLines()
                .withHeader(CSVMetaData.HEADER_FILE_LINE.split(";"))
                .withFirstRecordAsHeader());
    }

    /**
     * Create a chain of {@link IProcessor} objects from a list of classes. It starts with a {@link ProcessorChain},
     * and ends with a {@link FinalProcessor}.
     * @param processorClasses The list of classes to instantiate.
     */
    private IProcessor<CSVRecord, DataMartContext> createChain(List<Class<? extends IProcessor<CSVRecord, DataMartContext>>> processorClasses)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        IProcessor<CSVRecord, DataMartContext> chainEntryPoint = new ProcessorChain<>();
        IProcessor<CSVRecord, DataMartContext> chain = chainEntryPoint;

        for (Class<? extends IProcessor<CSVRecord, DataMartContext>> processorClass : processorClasses) {
            IProcessor<CSVRecord, DataMartContext> processor = processorClass.getConstructor().newInstance();
            chain = chain.setNextProcessor(processor);
        }

        chain.setNextProcessor(new FinalProcessor<>());

        return chainEntryPoint;
    }

}
