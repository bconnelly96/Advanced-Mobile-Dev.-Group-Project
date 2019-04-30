package edu.temple.mobiledevgroupproject.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import edu.temple.mobiledevgroupproject.Objects.Comment;
import edu.temple.mobiledevgroupproject.Objects.Constants;
import edu.temple.mobiledevgroupproject.Objects.Job;
import edu.temple.mobiledevgroupproject.Objects.RequestHandler;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.User;
import edu.temple.mobiledevgroupproject.R;

public class CommentFragment extends Fragment implements RecyclerViewItemClicked {
    //layout objects
    RecyclerView recyclerView;
    EditText commentEditText;
    Button postButton;
    CommentAdapter commentAdapter;
    ProgressDialog progressDialog;

    //other objects
    Job job;
    User user;
    ArrayList<Comment> commentsList;
    public CommentPostedInterface commentPostedListener;

    public CommentFragment() {
        // Required empty public constructor
    }

    public interface CommentPostedInterface {
        void getPostedComment(Comment comment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        commentPostedListener = (CommentPostedInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        commentPostedListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        Bundle args = getArguments();
        if (args != null) {
            job = args.getParcelable("this_job");
            user = args.getParcelable("this_user");
            commentsList = job.getCommentList().getRecordData();
        }

        recyclerView = view.findViewById(R.id.comment_recycler);
        commentEditText = view.findViewById(R.id.comment_et);
        postButton = view.findViewById(R.id.post_button);
        progressDialog = new ProgressDialog(getContext());

        commentAdapter = new CommentAdapter(commentsList, this);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);

        postButton.setOnClickListener(new View.OnClickListener() {
            String toastString;

            @Override
            public void onClick(View v) {
                String commentString = commentEditText.getText().toString().trim();
                if (commentString.length() != 0) {
                    Calendar cal = Calendar.getInstance();
                    SimpleDate thisDate = new SimpleDate(cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.YEAR));
                    Comment newComment = new Comment(commentString, user, thisDate);
                    //commentPostedListener.getPostedComment(newComment);
                    commentAdapter.notifyDataSetChanged();

                    //SEND TO DB
                    sendCommentToDatabase(newComment.getCommentText());

                    toastString = getResources().getString(R.string.comment_posted);
                    commentEditText.setText("");
                } else {
                    toastString = getResources().getString(R.string.comment_empty);
                }
                Toast.makeText(getContext(), toastString, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void userItemClick(View v, int position) {
        String selectedUserName = commentsList.get(position).getUser().getUserName();
        commentEditText.setText("@" + selectedUserName);
    }

    /**
     * Helper method.
     * Send new comment to databse
     *
     * @param comment Comment instance to be send to DB
     */
    private void sendCommentToDatabase(final String comment) {
        progressDialog.setMessage("Adding comment...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.COMMENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.hide();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Log.d("CommentResponse", jsonObject.toString());
                            job.updateCommentList(new Comment(comment, user, new SimpleDate("2019-04-29")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("body", comment);
                params.put("jobTitle", job.getJobTitle());
                return params;
            }
        };
        RequestHandler.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}