package core.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordRequest {
    String code;
    String password;
    String captcha;
    @JsonProperty("captchaSecret")
    String captchaSecret;

    public PasswordRequest(String code, String password, String captcha, String captchaSecret) {
        this.code = code;
        this.password = password;
        this.captcha = captcha;
        this.captchaSecret = captchaSecret;
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

    public String getCaptchaSecret() {
        return captchaSecret;
    }

    public void setCaptchaSecret(String captchaSecret) {
        this.captchaSecret = captchaSecret;
    }
}
