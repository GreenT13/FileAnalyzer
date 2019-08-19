package com.apon.impl;

import org.apache.commons.csv.CSVRecord;

/**
 * Meta data of the Data Mart reporting file.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CSVMetaData {
    public final static String HEADER_FILE_LINE = "Regel;Tijdstip aangemaakt;Meldingsoort;Melding;Referentie";

    public final static String HEADER_LINE_NUMBER = "Regel";
    public final static String HEADER_TS_LINE = "Tijdstip aangemaakt";
    public final static String HEADER_TYPE = "Meldingsoort";
    public final static String HEADER_MESSAGE = "Melding";
    public final static String HEADER_REFERENCE = "Referentie";

    public enum HeaderType {
        MESSAGE("Mededeling"),
        TRANSACTION("Transactie"),
        ERROR("Fatale fout"),
        SUCCESS("Succesmelding"),
        RECORDS_PER_MINUTE("Records per minuut");

        private String value;

        HeaderType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public boolean isType(CSVRecord record) {
            return value.equals(record.get(HEADER_TYPE));
        }
    }
}
