package core.api.response.Posts;

public class PostsCalendarResponse {
    String date;
    int count;

    public PostsCalendarResponse(String date, int count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
