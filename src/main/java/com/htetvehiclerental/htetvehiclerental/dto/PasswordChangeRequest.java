package com.htetvehiclerental.htetvehiclerental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {
    private Long employeeId;
    private String oldPassword;
    private String newPassword;
}