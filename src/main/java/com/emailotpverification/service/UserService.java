package com.emailotpverification.service;

import com.emailotpverification.entity.User;
import com.emailotpverification.payload.RequestDto;
import com.emailotpverification.payload.ResponseDto;
import com.emailotpverification.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;

    public ResponseDto registerUser(RequestDto requestDto) {
        ResponseDto response = new ResponseDto();
        User existingUser = this.userRepository.findByEmail(requestDto.getEmail());
        if (existingUser != null) {
            response.setMessage("User already registered.");
        } else {
            Random random = new Random();
            String otp = String.format("%06d", random.nextInt(100000));
            User newUser = new User();
            newUser.setUserName(requestDto.getUserName());
            newUser.setEmail(requestDto.getEmail());
            newUser.setOtp(otp);
            newUser.setVerified(false);

            User savedUser = this.userRepository.saveAndFlush(newUser);
            String subject = "Email Verification";
            String body = "Your verification OTP is " + savedUser.getOtp();

            //Email send
            this.emailService.sendEmail(savedUser.getEmail(), subject, body);
            response.setUserId(savedUser.getUserId());
            response.setUserName(savedUser.getUserName());
            response.setEmail(savedUser.getEmail());
            response.setMessage("OTP sent successfully!");
        }
        return response;
    }

    public String verifyUser(String email, String otp) {
        String response = "";
        User user = this.userRepository.findByEmail(email);
        if (user != null && user.isVerified()) {
            response = "User is already verified!";
        } else if (otp.equals(user.getOtp())) {
            user.setVerified(true);
            this.userRepository.saveAndFlush(user);
            response = "User verified successfully!";
        } else {
            response = "User not verified";
        }
        return response;
    }
}
