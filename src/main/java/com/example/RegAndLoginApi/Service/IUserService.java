package com.example.RegAndLoginApi.Service;

import com.example.RegAndLoginApi.Entity.LoginDetails;
import com.example.RegAndLoginApi.Entity.User;
import com.example.RegAndLoginApi.Entity.VerifyEmailRequest;

import java.rmi.UnexpectedException;
import java.util.List;

public interface IUserService {

     String registerHere(User registration);

     String login(LoginDetails loginDetails);

     String generateToken(String email);

     String verifyToken(VerifyEmailRequest verifyEmailRequest);
    
     String verifyFpToken(String fpToken,String newPassword);

    String generateFpToken(String email);
}
