/*Comment:
*(1) Represents a comment to be displayed on a specific job
*(2) Represents an object to be stored externally*/

package edu.temple.mobiledevgroupproject.BackEnd;

import java.util.Date;

public class Comment {
    private String text;
    private User user;
    private Date datePosted;
    private Job associatedJob;

    public Comment(String text, User user, Date datePosted, Job associatedJob) {
        this.text = text;
        this.user = user;
        this.datePosted = datePosted;
        this.associatedJob = associatedJob;
    }

    public String getCommentText() {
        return text;
    }

    public User getUser() {
        return user;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public Job getAssociatedJob() {
        return associatedJob;
    }
}
