package core.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModerationRequest {
    @JsonProperty("post_id")
    String postId;
    String decision;

    public ModerationRequest(String postId, String decision) {
        this.postId = postId;
        this.decision = decision;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
