package org.pehlivan.mert.awssqs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pehlivan.mert.awssqs.aws.AwsSQSManagementService;
import org.pehlivan.mert.awssqs.aws.SQSQueueMessageRequest;
import org.pehlivan.mert.awssqs.dto.UserActivationDto;
import org.pehlivan.mert.awssqs.dto.UserCreateRequest;
import org.pehlivan.mert.awssqs.dto.UserCreateResponse;
import org.pehlivan.mert.awssqs.dto.UserDto;
import org.pehlivan.mert.awssqs.dto.converter.UserConverter;
import org.pehlivan.mert.awssqs.exception.UserActivationException;
import org.pehlivan.mert.awssqs.exception.UserAlreadyRegistered;
import org.pehlivan.mert.awssqs.exception.UserNotFoundException;
import org.pehlivan.mert.awssqs.model.User;
import org.pehlivan.mert.awssqs.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final AwsSQSManagementService awsSQSManagementService;

    public UserCreateResponse registerUser(UserCreateRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyRegistered("Email is already registered");
        }
        User user = User.create(request);

        User savedUser = userRepository.save(user);
        sendMessageToQueue(savedUser, request);
        log.info("User is saved successfully {}", savedUser);
        return userConverter.toUserCreateResponse(savedUser);
    }

    private void sendMessageToQueue(User user, UserCreateRequest request) {
        SQSQueueMessageRequest queueMessage = SQSQueueMessageRequest.builder()
                .body(user)
                .isDeadLetterQueueTest(request.isDeadLetterQueueTest())
                .build();
        awsSQSManagementService.sendMessage(queueMessage);
    }

    public UserActivationDto activateUser(String token) {
        User user = userRepository.findByValidationTokenAndValidationTokenExpiryAfter(token, LocalDateTime.now())
                .orElseThrow(() -> new UserActivationException("Invalid or expired validation token " + token));

        user.activateUser();
        User activatedUser = userRepository.save(user);
        log.info("User {} is activated successfully", activatedUser.getEmail());
        return new UserActivationDto(activatedUser);
    }

    public UserDto findByEmail(String email) {
        User fromDb = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(String.format("User not found with email %s", email)));
        return userConverter.toUserDto(fromDb);
    }
}
