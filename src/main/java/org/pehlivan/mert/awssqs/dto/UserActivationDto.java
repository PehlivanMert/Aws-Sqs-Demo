package org.pehlivan.mert.awssqs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.pehlivan.mert.awssqs.model.User;

@Data
@AllArgsConstructor
public class UserActivationDto {
    private User activatedUser;
}
