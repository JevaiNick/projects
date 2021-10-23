package core.api.response.Comments;

public class PostCommentResponse {
    private String result;
    private ErrorsPostCommentResponse errors;

    public PostCommentResponse(String result, ErrorsPostCommentResponse errors) {
        this.result = result;
        this.errors = errors;
    }
}
