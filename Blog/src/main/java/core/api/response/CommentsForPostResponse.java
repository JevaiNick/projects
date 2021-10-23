package core.api.response;

import core.api.response.Users.UserCommentResponse;

public class CommentsForPostResponse {
    private int id;
    private long timestamp;
    private String text;
    private UserCommentResponse user;

    public CommentsForPostResponse(int id, long timestamp, String text, UserCommentResponse user) {
        this.id = id;
        this.timestamp = timestamp;
        this.text = text;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserCommentResponse getUser() {
        return user;
    }

    public void setUser(UserCommentResponse user) {
        this.user = user;
    }
}
