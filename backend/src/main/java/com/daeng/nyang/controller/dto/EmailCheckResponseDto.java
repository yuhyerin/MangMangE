package com.daeng.nyang.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailCheckResponseDto {
    String email;
    String originHash; // 암호화 한 인증번호
    boolean available;
}
