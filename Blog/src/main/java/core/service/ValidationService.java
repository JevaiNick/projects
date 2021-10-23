package core.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {
    public static boolean isValidUsername(String name) {
        String regex = "^(?=[a-zA-Z0-9._]{3,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
        Pattern p = Pattern.compile(regex);
        if (name.trim().equals("")) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        if (email.trim().equals("")){
            return false;
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public static boolean isValidPassword(String password){
        if (password.length() < 6){
            return false;
        }
        return true;
    }

}
