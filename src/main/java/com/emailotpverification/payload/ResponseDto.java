package com.emailotpverification.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private Long userId;
    private String userName;
    private String email;
    private boolean verified;
    private String message;

}
