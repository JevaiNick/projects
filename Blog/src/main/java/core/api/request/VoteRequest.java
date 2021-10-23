package core.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoteRequest {
    @JsonProperty("post_id")
    String postId;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
