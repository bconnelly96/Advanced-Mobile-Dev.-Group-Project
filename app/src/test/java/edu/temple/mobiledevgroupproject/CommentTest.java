package edu.temple.mobiledevgroupproject;

import org.junit.Test;

import edu.temple.mobiledevgroupproject.Objects.Comment;
import edu.temple.mobiledevgroupproject.Objects.SimpleDate;
import edu.temple.mobiledevgroupproject.Objects.User;

import static org.junit.Assert.*;

public class CommentTest {
    @Test
    public void comment_ConstructCommentObj() {
        Comment comment = new Comment("text", new User(), new SimpleDate(1, 1, 2019));
        assertNotNull(comment);
    }
}


