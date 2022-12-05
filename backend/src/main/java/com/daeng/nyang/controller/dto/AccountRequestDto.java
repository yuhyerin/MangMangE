package com.daeng.nyang.controller.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
@Data
public class AccountRequestDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=100)
    private String user_id;

    @Column(nullable=false)
    private String user_email;

    @Column(nullable=false)
    private String user_password;

    @Column(nullable=false, length=50)
    private String user_name;

    @CreationTimestamp
    private Date regdate;

    private String role;

}
