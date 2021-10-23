package core.api.request;

public class RegisterRequest {
    private String e_mail;
    private String name;
    private String password;
    private String captcha;
    private String captcha_secret;

    public RegisterRequest(String email, String name, String password, String captcha, String captchaSecret) {
        this.e_mail = email;
        this.name = name;
        this.password = password;
        this.captcha = captcha;
        this.captcha_secret = captchaSecret;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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



    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getCaptcha_secret() {
        return captcha_secret;
    }

    public void setCaptcha_secret(String captcha_secret) {
        this.captcha_secret = captcha_secret;
    }
}
