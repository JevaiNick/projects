package core.api.request.Post;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostRequestTag {
    String offset;
    String limit;
    String tag;

    public PostRequestTag(String offset, String limit, String tag) {
        this.offset = offset;
        this.limit = limit;
        this.tag = tag;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
