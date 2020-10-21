package com.daeng.nyang.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    String user_id;
    String user_password;

}
