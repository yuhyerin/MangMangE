package com.daeng.nyang.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdCheckResponseDto {
    boolean available;
}
