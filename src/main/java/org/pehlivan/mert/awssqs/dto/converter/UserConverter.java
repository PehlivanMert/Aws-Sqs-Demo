package org.pehlivan.mert.awssqs.dto.converter;

import lombok.RequiredArgsConstructor;
import org.pehlivan.mert.awssqs.dto.UserCreateResponse;
import org.pehlivan.mert.awssqs.dto.UserDto;
import org.pehlivan.mert.awssqs.model.User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public UserCreateResponse toUserCreateResponse(User user) {
        return UserCreateResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .validationToken(user.getValidationToken())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .isActive(user.isActive())
                .build();
    }
}
