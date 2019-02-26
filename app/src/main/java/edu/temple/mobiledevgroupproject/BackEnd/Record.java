/*Record:
*(1) Represents a generic object for storing of various data*/

package edu.temple.mobiledevgroupproject.BackEnd;

import java.util.ArrayList;

public class Record<T> {
    private ArrayList<T> recordData;
    private String recordName;
    private T recordType;

    public Record(ArrayList<T> recordData, String recordName, T recordType) {
        this.recordData = recordData;
        this.recordName = recordName;
        this.recordType = recordType;
    }

    public ArrayList<T> getRecordData() {
        return recordData;
    }

    public String getRecordName() {
        return recordName;
    }

    public T getRecordType() {
        return recordType;
    }
}
