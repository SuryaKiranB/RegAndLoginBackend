package com.example.RegAndLoginApi.Controller;

import com.example.RegAndLoginApi.Entity.*;
import com.example.RegAndLoginApi.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDetails loginDetails) {
        return userService.login(loginDetails);
    }

    @PostMapping("/registration")
    public String registerHere(@RequestBody User user) {
        return userService.registerHere(user);
    }

    @PostMapping("/resendToken")
    public String resendToken(@RequestBody ResendTokenRequest resendTokenRequest) {
        return userService.generateToken(resendTokenRequest.getEmail());
    }

    @PostMapping("/confirm")
    public String getToken(@RequestBody VerifyEmailRequest verifyEmailRequest) {
        return userService.verifyToken(verifyEmailRequest);
    }

    @PostMapping("/sendFpToken")
    public String sendFpToken(@RequestBody ResendTokenRequest resendTokenRequest){
        return userService.generateFpToken(resendTokenRequest.getEmail());
    }

    @PostMapping("/forgot-password")
    public String getFpToken(@RequestBody ForgotPasswordRequest forgotPasswordRequest){
        return userService.verifyFpToken(forgotPasswordRequest.getFpToken(),forgotPasswordRequest.getNewPassword());
    }
}
