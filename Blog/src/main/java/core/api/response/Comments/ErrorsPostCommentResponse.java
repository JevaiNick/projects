package core.api.response.Comments;

public class ErrorsPostCommentResponse {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ErrorsPostCommentResponse(String text) {
        this.text = text;
    }
}
