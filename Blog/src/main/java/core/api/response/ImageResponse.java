package core.api.response;

public class ImageResponse {
    String result;
    ImageErrors errors;

    public ImageResponse(String result, ImageErrors errors) {
        this.result = result;
        this.errors = errors;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ImageErrors getErrors() {
        return errors;
    }

    public void setErrors(ImageErrors errors) {
        this.errors = errors;
    }
}
