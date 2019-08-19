package com.apon.impl.context;

import com.apon.framework.Context;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Context object for each processor that extends {@link com.apon.impl.processors.AbstractDataMartProcessor}.
 */
public class DataMartContext implements Context {
    private List<Task> tasks;

    private String jobStart;
    private String jobEnd;

    private String realPolicyStart;
    private String realPolicyEnd;

    public DataMartContext() {
        tasks = new ArrayList<>();
    }

    /**
     * Write the full context to file, using a {@link CSVPrinter}.
     * @param csvPrinter The printer.
     */
    public void writeToCSVPrinter(CSVPrinter csvPrinter) throws IOException {
        for (Task task : tasks) {
            csvPrinter.printRecord(
                    task.name,
                    task.tsStart,
                    task.tsEnd,
                    task.nrOfRecordsWritten,
                    task.nrOfRecordsImported,
                    task.loggedSpeed
            );
        }

        csvPrinter.printRecord("Job start", jobStart, jobEnd, null, null, null);
        csvPrinter.printRecord("Real policy timings", realPolicyStart, realPolicyEnd, null, null, null);

        csvPrinter.flush();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setJobStart(String jobStart) {
        this.jobStart = jobStart;
    }

    public void setJobEnd(String jobEnd) {
        this.jobEnd = jobEnd;
    }

    public void setRealPolicyStart(String realPolicyStart) {
        this.realPolicyStart = realPolicyStart;
    }

    public void setRealPolicyEnd(String realPolicyEnd) {
        this.realPolicyEnd = realPolicyEnd;
    }
}
