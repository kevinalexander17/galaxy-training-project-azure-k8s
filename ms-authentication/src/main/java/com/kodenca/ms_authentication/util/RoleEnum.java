package com.kodenca.ms_authentication.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROLE_USER("ADMIN"),
    ROLE_CLIENT("CLIENT");

    private final String role;
}
