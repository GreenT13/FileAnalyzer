package com.apon.impl.context;

public class Task {
    public String name;
    public String tsStart;
    public String tsEnd;
    public Integer nrOfRecordsWritten;
    public Integer nrOfRecordsImported;
    public Integer loggedSpeed;

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", tsStart='" + tsStart + '\'' +
                ", tsEnd='" + tsEnd + '\'' +
                ", nrOfRecordsWritten=" + nrOfRecordsWritten +
                ", nrOfRecordsImported=" + nrOfRecordsImported +
                ", loggedSpeed=" + loggedSpeed +
                '}';
    }
}
