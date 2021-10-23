package core.api.response.Posts;

public class NewPostResponse {
    String result;
    NewPostErrors newPostErrors = new NewPostErrors();

    public NewPostResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public NewPostErrors getNewPostErrors() {
        return newPostErrors;
    }

    public void setNewPostErrors(NewPostErrors newPostErrors) {
        this.newPostErrors = newPostErrors;
    }
}
