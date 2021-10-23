package core.api.response.Posts;

import core.api.response.CommentsForPostResponse;
import core.api.response.Users.UserCommentResponse;
import core.api.response.Users.UserPostResponse;
import core.model.PostComment;

import java.util.ArrayList;
import java.util.List;

public class SinglePostResponse {
    private int id;
    private long timestamp;
    private UserPostResponse user;
    private String title;
    private String text;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;
    private List<CommentsForPostResponse> comments = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    public SinglePostResponse() {
    }

    public SinglePostResponse(int id,
                              long timestamp,
                              UserPostResponse user,
                              String title,
                              String text,
                              int likeCount,
                              int dislikeCount,
                              int commentCount,
                              int viewCount,
                              List<String> tags) {
        this.id = id;
        this.timestamp = timestamp;
        this.user = user;
        this.title = title;
        this.text = text;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.tags = tags;
    }

    public void addComment(PostComment postComment){
        comments.add(new CommentsForPostResponse(postComment.getId(),
                postComment.getTime().getTime()/ 1000L,
                postComment.getText(),
                new UserCommentResponse(postComment.getUsers().getId(),
                        postComment.getUsers().getName(),
                        postComment.getUsers().getPhoto())));
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

    public UserPostResponse getUser() {
        return user;
    }

    public void setUser(UserPostResponse user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public List<CommentsForPostResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentsForPostResponse> comments) {
        this.comments = comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
