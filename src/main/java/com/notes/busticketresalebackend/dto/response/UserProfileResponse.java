package com.notes.busticketresalebackend.dto.response;

import com.notes.busticketresalebackend.entity.AccountStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private String name;

    private String email;

    private String phoneNumber;

    private Integer warningCount;

    private AccountStatus accountStatus;
}