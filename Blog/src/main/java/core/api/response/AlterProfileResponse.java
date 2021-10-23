package core.api.response;

public class AlterProfileResponse {
    String result;
    AlterProfileErrors alterProfileErrors = new AlterProfileErrors();

    public AlterProfileResponse(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public AlterProfileErrors getAlterProfileErrors() {
        return alterProfileErrors;
    }

    public void setAlterProfileErrors(AlterProfileErrors alterProfileErrors) {
        this.alterProfileErrors = alterProfileErrors;
    }
}
