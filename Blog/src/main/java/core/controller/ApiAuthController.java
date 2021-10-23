package core.controller;

import core.api.request.LoginRequest;
import core.api.request.PasswordRequest;
import core.api.request.RegisterRequest;
import core.api.request.RestoreRequest;
import core.api.response.*;
import core.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")

public class ApiAuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public ApiAuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(authService.getLoginResponse(user.getUsername()));


    }




    @GetMapping("/check")
    public ResponseEntity<LoginResponse> check(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }

        return ResponseEntity.ok(authService.getLoginResponse(principal.getName()));
    }

    @GetMapping("/captcha")
    public ResponseEntity<CaptchaResponse> getCaptcha(){
        return ResponseEntity.ok(authService.getCaptcha());
    }


    @PostMapping("/register")
    public ResponseEntity registration(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse =  authService.registration(registerRequest);
        if (registerResponse == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        if (registerResponse.getResult()) {
            return ResponseEntity.ok(registerResponse);
        }
        return ResponseEntity.badRequest().body(registerResponse);
    }

    @PostMapping("/restore")
    public ResponseEntity restore(@RequestBody RestoreRequest restoreRequest) throws MessagingException {
        return ResponseEntity.ok(authService.restore(restoreRequest.getEmail()));
    }

    @PostMapping("/password")
    public ResponseEntity password(@RequestBody PasswordRequest passwordRequest){
        PasswordResponse passwordResponse = authService.passwordRestore(passwordRequest);
        if (passwordResponse.getResult().equals("true")){
            return new ResponseEntity(new ResultResponse("true"), HttpStatus.OK);
        }
        return new ResponseEntity(passwordResponse, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(){
        SecurityContextHolder.getContext().setAuthentication(null);
        return new ResponseEntity(new ResultResponse("true"), HttpStatus.OK);
    }

}
