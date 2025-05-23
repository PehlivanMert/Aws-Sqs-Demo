package org.pehlivan.mert.awssqs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class UserCreateResponse {
    private Long userId;
    private String email;
    private boolean isActive;
    private String validationToken;
}
