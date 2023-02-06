package com.hellog.domain.user;

import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.repository.UserRepository;
import com.hellog.domain.user.domain.type.AuthType;
import com.hellog.domain.user.domain.type.UserRole;
import com.hellog.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private final User user = User.builder()
            .name("우준성")
            .email("abc@gmail.com")
            .description("안녕하세요")
            .role(UserRole.USER)
            .authType(AuthType.DODAM)
            .build();

    private final Optional<User> optionalUser = Optional.of(user);

    @BeforeEach
    public void init() {
        userService = new UserService(userRepository);
    }

    @Test
    @DisplayName("getUserById() service")
    void getUserById() {
        when(userRepository.findById(anyLong())).thenReturn(optionalUser);
        assertEquals(userService.getUserById(1L), user);
    }

    @Test
    void createUser() {

    }

}