package core.api.response;

public class PasswordErrors {
    String code;
    String password;
    String captcha;

    public PasswordErrors(String code, String password, String captcha) {
        this.code = code;
        this.password = password;
        this.captcha = captcha;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
