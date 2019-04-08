/*Record:
*(1) Represents a generic object for storing of various data*/

package edu.temple.mobiledevgroupproject.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Record<T> {
    public static final String COMMENT_RECORD = "comment_record";
    public static final String JOB_RECORD = "job_record";

    private ArrayList<T> recordData;
    private String recordName;
    private String recordType;

    public Record(String recordName, String recordType) {
        this.recordData = new ArrayList<T>();
        this.recordName = recordName;
        this.recordType = recordType;
    }

    public ArrayList<T> getRecordData() {
        return recordData;
    }

    public String getRecordName() {
        return recordName;
    }

    public String getRecordType() {
        return recordType;
    }

    public void addDataToRecord(T data) {
        recordData.add(data);
    }

    /**
     * Constructs a JSONObject based on a Record instance's fields.
     * FORMAT: {"name":<name>,"type":<type>,"data":<[data1, data2, ... , dataN]>}
     * @return a Record instance's fields in JSONObject format.
     */
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
           jsonObject.put("name", recordName);
           jsonObject.put("type", recordType);

           JSONArray jsonDataArray = new JSONArray();
           for (int i = 0; i < recordData.size(); i++) {
               jsonDataArray.put(recordData.get(i));
           }
           jsonObject.put("data", jsonDataArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
