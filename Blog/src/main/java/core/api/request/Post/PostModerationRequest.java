package core.api.request.Post;

public class PostModerationRequest {
    private String offset;
    private String limit;
    private String status;

    public PostModerationRequest(String offset, String limit, String status) {
        this.offset = offset;
        this.limit = limit;
        this.status = status;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
