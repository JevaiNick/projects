package core.api.request.Post;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostRequest {
    public PostRequest(String offset, String limit, String mode) {
        this.offset = offset;
        this.limit = limit;
        this.mode = mode;
    }

    private String offset;
    private String limit;
    private String mode;


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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
