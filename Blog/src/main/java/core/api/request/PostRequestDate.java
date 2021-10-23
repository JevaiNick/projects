package core.api.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class PostRequestDate {
    String offset;
    String limit;
    String date;

    public PostRequestDate(String offset, String limit, String date) {
        this.offset = offset;
        this.limit = limit;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
