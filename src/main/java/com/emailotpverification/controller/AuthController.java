package com.emailotpverification.controller;

import com.emailotpverification.payload.RequestDto;
import com.emailotpverification.payload.ResponseDto;
import com.emailotpverification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("/user-register")
    public ResponseEntity<ResponseDto> registerUser(@RequestBody RequestDto requestDto) {
        ResponseDto response = userService.registerUser(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/user-verify")
    public ResponseEntity<?> verifyUser(@RequestParam String email, @RequestParam String otp) {
        String response = userService.verifyUser(email, otp);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
