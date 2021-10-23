package core.api.request.Post;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class PostRequestSearch {
    public PostRequestSearch(String offset, String limit, String query) {
        this.offset = offset;
        this.limit = limit;
        this.query = query;
    }

    String offset;
    String limit;
    String query;

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

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
