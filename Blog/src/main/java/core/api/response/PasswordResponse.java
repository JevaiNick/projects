package core.api.response;

public class PasswordResponse {
    String result;
    PasswordErrors errors;

    public PasswordResponse(String result) {
        this.result = result;
    }

    public PasswordResponse(String result, PasswordErrors errors) {
        this.result = result;
        this.errors = errors;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public PasswordErrors getErrors() {
        return errors;
    }

    public void setErrors(PasswordErrors errors) {
        this.errors = errors;
    }
}
