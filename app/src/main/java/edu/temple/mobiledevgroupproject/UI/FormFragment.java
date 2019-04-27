/*FormFragment:
*(1) Displays fields for Job data retrieval
*(2) Gets Posted Job data from user
*(3) Hands valid Job data to parent Activity*/

package edu.temple.mobiledevgroupproject.UI;

import android.app.TimePickerDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import edu.temple.mobiledevgroupproject.Objects.Comment;
import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.Objects.Record;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.SimpleTime;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class FormFragment extends Fragment {
    //layout objects
    EditText titleView;
    EditText jobDescView;
    EditText addrLineView;
    EditText cityView;
    EditText stateView;
    EditText zipCodeView;
    CalendarView calendarView;
    Button startButton;
    Button endButton;
    Button confirmButton;

    //other objects
    FormInterface formInterfaceListener;
    View mView;
    User thisUser;
    int selectedMonth;
    int selectedDay;
    int selectedYear;
    SimpleTime startTime;
    SimpleTime endTime;

    public FormFragment() {
        // Required empty public constructor
    }

    public interface FormInterface {
        void getDataFromForm(Job jobData, User user);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        formInterfaceListener = (FormInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        formInterfaceListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_form, container, false);

        Bundle args = getArguments();
        if (args != null) {
            thisUser = args.getParcelable("this_user");
        }

        titleView = mView.findViewById(R.id.job_title_f);
        jobDescView = mView.findViewById(R.id.job_desc_f);
        addrLineView = mView.findViewById(R.id.job_addr_f);
        cityView = mView.findViewById(R.id.job_city_f);
        stateView = mView.findViewById(R.id.job_state_f);
        zipCodeView = mView.findViewById(R.id.job_zip_f);
        calendarView = mView.findViewById(R.id.calendar_view);
        startButton = mView.findViewById(R.id.start_button);
        endButton = mView.findViewById(R.id.end_button);
        confirmButton = mView.findViewById(R.id.confirm_button_f);

        Calendar cal = Calendar.getInstance();
        //android encodes jan. as 0 and dec. as 11
        final int thisMonth = cal.get(Calendar.MONTH) + 1;
        final int thisDay = cal.get(Calendar.DAY_OF_MONTH);
        final int thisYear = cal.get(Calendar.YEAR);
        final int thisHour = cal.get(Calendar.HOUR_OF_DAY);
        final int thisMinute = cal.get(Calendar.MINUTE);
        //initialize date selection vars.
        selectedMonth = thisMonth;
        selectedDay = thisDay;
        selectedYear = thisYear;

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTime(hourOfDay, minute, true);
                        startButton.setText(SimpleTime.formatTime(startTime));
                    }
                }, thisHour, thisMinute, false);
                timePickerDialog.show();
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTime(hourOfDay, minute, false);
                        endButton.setText(SimpleTime.formatTime(endTime));
                    }
                }, thisHour, thisMinute, false);
                timePickerDialog.show();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //android encodes jan. as 0 and dec. as 11
                selectedMonth = month + 1;
                selectedDay = dayOfMonth;
                selectedYear = year;
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobAddr = addrLineView.getText().toString().trim();
                String jobCity = cityView.getText().toString().trim();
                String jobState = stateView.getText().toString().trim();
                String jobZipCode = zipCodeView.getText().toString().trim();

                if (allFieldsHaveInput() && dateFieldsValid() && SimpleTime.isValidTimeInterval(startTime, endTime) && addrToLatLng(jobAddr, jobCity, jobState, jobZipCode) != null) {
                    SimpleDate simpleDate = new SimpleDate(selectedMonth, selectedDay, selectedYear);

                    LatLng jobLoc = addrToLatLng(jobAddr, jobCity, jobState, jobZipCode);

                    String jobTitle = titleView.getText().toString().trim();

                    Record<Comment> commentList = new Record<>(jobTitle + "/comments", Record.COMMENT_RECORD);

                    Job newJob = new Job();
                    newJob.setJobTitle(jobTitle)
                            .setJobDescription(jobDescView.getText().toString().trim())
                            .setDatePosted(new SimpleDate(thisMonth, thisDay, thisYear))
                            .setDateOfJob(simpleDate)
                            .setStartTime(startTime)
                            .setEndTime(endTime)
                            .setLocation(jobLoc)
                            .setUser(thisUser)
                            .setCommentList(commentList);

                    System.out.println("******************************");
                    System.out.println("******************************");
                    System.out.println(newJob.toJSONObject().toString());
                    System.out.println("******************************");
                    System.out.println("******************************");

                    formInterfaceListener.getDataFromForm(newJob, thisUser);
                    String toastString = getResources().getString(R.string.job_created);
                    Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();
                } else {
                    String toastString = getResources().getString(R.string.invalid);
                    Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return mView;
    }

    /**
     * Helper Method.
     * Checks for any empty UI input field.
     * @return true if all UI input fields have some input.
     */
    private boolean allFieldsHaveInput() {
        return ((titleView.getText().toString().trim().length() != 0) &&
                (jobDescView.getText().toString().trim().length() != 0) &&
                (addrLineView.getText().toString().trim().length() != 0) &&
                (cityView.getText().toString().trim().length() != 0) &&
                (stateView.getText().toString().trim().length() != 0) &&
                (zipCodeView.getText().toString().trim().length() != 0)) &&
                (selectedYear != 0) &&
                (selectedDay != 0) &&
                (selectedMonth) != 0 &&
                (startTime != null) &&
                (endTime != null);
    }

    /**
     * Helper Method.
     * Checks for invalid input in date fields.
     * @return true if date entered in date input fields is valid.
     */
    private boolean dateFieldsValid() {
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        SimpleDate sd = new SimpleDate(selectedMonth, selectedDay, selectedYear);
        return (sd.getYear() >= thisYear && SimpleDate.isValidDate(sd.getMonth(), sd.getDay(), sd.getYear()));
    }

    /**
     * Helper Method.
     * Assemble a LatLng object from the address corresponding to the args.
     * @param jobAddr    Represents job's address, i.e. "123 elm street"
     * @param jobCity    Represents job's city, i.e. "Philadelphia"
     * @param jobState   Represents job's state, i.e. "PA"
     * @param jobZipCode Represents job's zip code, i.e. "12345"
     * @return a LatLng object
     */
    private LatLng addrToLatLng(String jobAddr, String jobCity, String jobState, String jobZipCode) {
        Geocoder geocoder = new Geocoder(getContext());
        List<Address> address;
        LatLng location = null;
        StringBuilder sb = new StringBuilder();

        sb.append(jobAddr);
        sb.append(", ");
        sb.append(jobCity);
        sb.append(", ");
        sb.append(jobState);
        sb.append(" ");
        sb.append(jobZipCode);

        String addrString = sb.toString();

        try {
            address = geocoder.getFromLocationName(addrString, 5);
            if (address == null || address.size() == 0) {
                return null;
            }
            Address retAddr = address.get(0);
            location = new LatLng(retAddr.getLatitude(), retAddr.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }

    /**
     * Helper Method.
     * Sets this instance's start and end time fields.
     * @param hourOfDay     Represents the hour of the day selected from TimePickerDialog in 24 hr. format.
     * @param minuteOfHour  Represents the minute of the hour selected from TimePickerDialog.
     * @param isStartTime   Determines whether the time to be set is the start of end time of this Fragment's Job instance.
     */
    private void setTime(int hourOfDay, int minuteOfHour, boolean isStartTime) {
        String period = SimpleTime.ANTE_MERIDIEM;
        int hour = hourOfDay;

        //Time selected is in PM
        if (hourOfDay > 12) {
            period = SimpleTime.POST_MERIDIEM;
            hour -= 12;
        }

        if (isStartTime) {
            startTime = new SimpleTime(hour, minuteOfHour, period);
        } else {
            endTime = new SimpleTime(hour, minuteOfHour, period);
        }
    }
}