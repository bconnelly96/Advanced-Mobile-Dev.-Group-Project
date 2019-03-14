/*Record:
*(1) Represents a generic object for storing of various data*/

package edu.temple.mobiledevgroupproject.BackEnd;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void setRecordData(ArrayList<T> recordData) {
        this.recordData = recordData;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public void setRecordType(T recordType) {
        this.recordType = recordType;
    }

    //Returns a JSONObject containing values of instance's fields
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
           jsonObject.put("name", recordName);
           jsonObject.put("type", recordType);
           jsonObject.put("date", recordData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
