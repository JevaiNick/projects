package core.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentRequest {
    @JsonProperty("parent_id")
    private String parentId = "";
    @JsonProperty("post_id")
    private String postId;
    private String text;

    public CommentRequest(String parentId, String postId, String text) {
        this.parentId = parentId;
        this.postId = postId;
        this.text = text;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
