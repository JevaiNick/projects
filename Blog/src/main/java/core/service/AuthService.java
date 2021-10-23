package core.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.cage.Cage;
import com.github.cage.GCage;
import core.api.request.PasswordRequest;
import core.api.request.RegisterRequest;
import core.api.response.*;
import core.api.response.Users.UserLoginResponse;
import core.model.*;
import core.model.repository.CaptchaCodeRepository;
import core.model.repository.GlobalSettingRepository;
import core.model.repository.PostRepository;
import core.model.repository.UserRepository;
import net.bytebuddy.utility.RandomString;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Properties;


@Service
public class AuthService {

    private CaptchaCodeRepository captchaCodeRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private GlobalSettingRepository globalSettingRepository;


    @Autowired
    public AuthService(CaptchaCodeRepository captchaCodeRepository, UserRepository userRepository, AuthenticationManager authenticationManager, PostRepository postRepository, GlobalSettingRepository globalSettingRepository) {
        this.captchaCodeRepository = captchaCodeRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.postRepository = postRepository;
        this.globalSettingRepository = globalSettingRepository;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public LoginResponse getLoginResponse(String email) {
        core.model.User currentUser =
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException(email));

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setEmail(currentUser.getEmail());
        userLoginResponse.setModeration(currentUser.isModerator());
        userLoginResponse.setSettings(currentUser.isModerator());
        userLoginResponse.setId(currentUser.getId());
        userLoginResponse.setName(currentUser.getName());
        userLoginResponse.setPhoto(currentUser.getPhoto());


        if (userLoginResponse.isModeration()) {
            userLoginResponse.setModerationCount(countPosts());
        } else {
            userLoginResponse.setModerationCount(0);
        }

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userLoginResponse);

        return loginResponse;
    }

    public int countPosts() {
        Iterable<Post> postIterable = postRepository.findAll();
        int count = 0;
        for (Post p : postIterable) {
            if (p.getModerationStatus() == ModerationStatus.NEW) {
                count++;
            }
        }
        return count;
    }


    public static String getAuthName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public CaptchaResponse getCaptcha() {
        cleanOldCaptcha();
        Cage cage = new GCage();
        String captchaCode = cage.getTokenGenerator().next();
        String captchaSecretCode = Base64.getEncoder().encodeToString(captchaCode.getBytes());
        CaptchaCode captchaCodeEntity = new CaptchaCode();
        captchaCodeEntity.setCode(captchaCode);
        captchaCodeEntity.setSecretCode(captchaSecretCode);
        captchaCodeEntity.setTime(new Timestamp(System.currentTimeMillis()));
        captchaCodeRepository.save(captchaCodeEntity);
        String encodeImage = "data:image/png;base64, "
                + Base64.getEncoder()
                .encodeToString(cage.draw(captchaCode));
        return new CaptchaResponse(captchaSecretCode, encodeImage);
    }

    private void cleanOldCaptcha() {
        long difference = 3600000;
        Iterable<CaptchaCode> captchaCodes = captchaCodeRepository.findAll();
        for (CaptchaCode captchaCode : captchaCodes) {
            long captchaTime = captchaCode.getTime().getTime();
            if ((System.currentTimeMillis() - captchaTime) > difference) {
                captchaCodeRepository.delete(captchaCode);
            }
        }

    }

    private boolean isRegistrationOpen(){
        Iterable<GlobalSetting> globalSettingIterable = globalSettingRepository.findAll();
        for (GlobalSetting gs : globalSettingIterable){
            if (gs.getCode().equals("MULTIUSER_MODE")){
                if (gs.getValue().equalsIgnoreCase("yes")){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return false;
    }

    public RegisterResponse registration(RegisterRequest registerRequest) {
        if (!isRegistrationOpen()){
            return null;
        }
        RegisterResponse registerResponse = new RegisterResponse(false);
        if (!isExistEmail(registerRequest.getE_mail())
                && ValidationService.isValidPassword(registerRequest.getPassword())
                && ValidationService.isValidUsername(registerRequest.getName())
                && isValidCaptcha(registerRequest.getCaptcha(), registerRequest.getCaptcha_secret())) {
            userRepository.save(new User(new java.sql.Date(System.currentTimeMillis()),
                    registerRequest.getName(), registerRequest.getE_mail(), bcrypt(registerRequest.getPassword()), String.valueOf(registerRequest.getE_mail().hashCode())));
            return new RegisterResponse(true);
        }
        if (isExistEmail(registerRequest.getE_mail())) {
            registerResponse.getErrors().setEmail("Этот e-mail уже зарегистрирован");
        }
        if (!ValidationService.isValidUsername(registerRequest.getName())) {
            registerResponse.getErrors().setName("Имя указано неверно");
        }
        if (!ValidationService.isValidPassword(registerRequest.getPassword())) {
            registerResponse.getErrors().setPassword("Пароль короче 6-ти символов");
        }
        if (!isValidCaptcha(registerRequest.getCaptcha(), registerRequest.getCaptcha_secret())) {
            registerResponse.getErrors().setCaptcha("Код с картинки введён неверно");
        }
        return registerResponse;

    }

    private boolean isValidCaptcha(String captcha, String captchaSecret) {
        Iterable<CaptchaCode> captchaCodes = captchaCodeRepository.findAll();
        for (CaptchaCode captchaCode : captchaCodes) {
            if (captchaCode.getSecretCode().equals(captchaSecret) && captchaCode.getCode().equals(captcha)) {
                return true;
            }
        }
        return false;
    }

    public boolean isExistEmail(String email) {
        Iterable<User> userIterable = userRepository.findAll();
        for (User user : userIterable) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static String bcrypt(String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        return bCryptPasswordEncoder.encode(password);
    }

    public ResultResponse restore(String email) throws MessagingException {
        Logger logger = LogManager.getLogger();
        if (isExistEmail(email)) {
            String subject = "Restoring password!";
            String text = "https://jevai-java-skillbox.herokuapp.com/login/change-password/" + email.hashCode();
            User user = getUserByEmail(email);
            user.setCode(String.valueOf(email.hashCode()));
            userRepository.save(user);

            String to = email;
            String from = "d.zv.csgo@gmail.com";
            String host = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("jevai.dev@gmail.com", "hzdgn12457");
                }
            });
            session.setDebug(true);

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(subject);
                message.setText(text);
                System.out.println("sending...");
                logger.info("sending...");
                Transport.send(message);
                System.out.println("Sent message successfully....");
                logger.info("sending...");
            } catch (MessagingException mex) {
                mex.printStackTrace();
                return new ResultResponse("false");
            }
            return new ResultResponse("true");
        } else return new ResultResponse("false");
    }

    private User getUserByEmail(String email) {
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public PasswordResponse passwordRestore(PasswordRequest passwordRequest) {
        if (isCaptchaRight(passwordRequest.getCaptcha(), passwordRequest.getCaptchaSecret())){
            if (passwordRequest.getCode().length() >= 6) {
                User user = getUserByCode(passwordRequest.getCode());
                if (user != null) {
                    user.setPassword(bcrypt(passwordRequest.getPassword()));
                    userRepository.save(user);
                    return new PasswordResponse("true");
                }
                return new PasswordResponse("false", new PasswordErrors("Ссылка для восстановления пароля устарела.\n" +
                        "<a href=\n" +
                        "\\\"/auth/restore\\\">Запросить ссылку снова</a>", null, null));
            }
            return new PasswordResponse("false", new PasswordErrors(null, "Пароль короче 6-ти символов", null));
        }
        return new PasswordResponse("false", new PasswordErrors(null, null, "Код с картинки введен неверно"));
    }

    private boolean isCaptchaRight(String captcha, String captchaCode){
        Iterable<CaptchaCode> captchaCodes = captchaCodeRepository.findAll();
        for (CaptchaCode captchaEntity : captchaCodes){
            if (captchaEntity.getCode().equals(captcha) && captchaEntity.getSecretCode().equals(captchaCode)){
                return true;
            }
        }
        return false;
    }
    private User getUserByCode(String code){
        Iterable<User> users = userRepository.findAll();
        for (User user : users){
            if (user.getCode().equals(code)){
                return user;
            }
        }
        return null;
    }

    //////////////////////////////////////////////////////////
//    private void writeLogInCloud(String text){
//        String s = RandomString.make(6);
//        Regions clientRegion = Regions.EU_CENTRAL_1;
//        String bucketName = "jevaibucket/publicprefix";
//        String fileObjKeyName = s + ".jpg";
//        String fileName = s;
//
//        AWSCredentials awsCredentials =
//                new BasicAWSCredentials("AKIAVAR2I7GKLP66SIHL", "W3dXfLlwvfj+E8ucH62wwgalYZufOXLwFx2yxWu+");
//        AmazonS3 s3Client = AmazonS3ClientBuilder
//                .standard()
//                .withRegion(clientRegion)
//                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                .build();
//        // Upload a file as a new object with ContentType and title specified.
//        PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, f);
//        s3Client.putObject(request);
//        return "https://jevaibucket.s3.eu-central-1.amazonaws.com/publicprefix/" + fileObjKeyName;
//    }
}

