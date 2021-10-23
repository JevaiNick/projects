package core.api.response;

import java.util.ArrayList;
import java.util.List;

public class RegisterResponse {
    private boolean result;
    RegisterErrors errors = new RegisterErrors();

    public RegisterResponse(boolean result) {
        this.result = result;
    }

    public RegisterResponse(boolean result, RegisterErrors errors) {
        this.result = result;
        this.errors = errors;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public RegisterErrors getErrors() {
        return errors;
    }

    public void setErrors(RegisterErrors errors) {
        this.errors = errors;
    }
}
