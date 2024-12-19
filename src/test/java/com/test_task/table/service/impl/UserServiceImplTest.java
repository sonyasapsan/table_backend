package com.test_task.table.service.impl;

import com.test_task.table.dto.UserDto;
import com.test_task.table.exception.EntityNotFoundException;
import com.test_task.table.mapper.UserMapper;
import com.test_task.table.model.User;
import com.test_task.table.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper userMapper;

    @Test
    @DisplayName("Find all users, valid case")
    void findAll_validCase_returnUserList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<User> expected = getUsersList();
        Page<User> userPage = new PageImpl<>(expected, pageable, expected.size());
        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);

        List<User> actual = userService.findAll(pageable);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Save user, valid case")
    void saveUser_validCase_returnUserDto() {
        User user = getUser1();
        UserDto expected = getUserDto1();
        Mockito.when(userRepository.existsByEmail("soniasapsan@gmail.com")).thenReturn(false);
        Mockito.when(userMapper.toUserModel(expected)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toUserDto(user)).thenReturn(expected);

        UserDto actual = userService.save(expected);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Delete user by id, valid case")
    void deleteById() {
        Long id = 1L;
        userService.deleteById(id);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Update user by id, valid case")
    void updateById_validCase_returnUserDto() {
        UserDto expected = getUserDto2();
        User user = getUser2();
        user.setId(1L);
        Mockito.when(userRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userMapper.toUserModel(expected)).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userMapper.toUserDto(user)).thenReturn(expected);

        UserDto actual = userService.updateById(1L, expected);
        assertEquals(expected, actual);
    }

    private static User getUser1() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Sonia");
        user.setFirstName("Baranova");
        user.setEmail("soniasapsan@gmail.com");
        return user;
    }

    private static UserDto getUserDto1() {
        return new UserDto("Sonia", "Baranova",
                "soniasapsan@gmail.com");
    }

    private static User getUser2() {
        User user = new User();
        user.setId(2L);
        user.setFirstName("Sonia");
        user.setFirstName("Sapsan");
        user.setEmail("sofiasapsan@gmail.com");
        return user;
    }

    private static UserDto getUserDto2() {
        return new UserDto("Sonia", "Sapsan",
                "sofiasapsan@gmail.com");
    }

    private static List<User> getUsersList() {
        List users = new ArrayList();
        users.add(getUser1());
        users.add(getUser2());
        return users;
    }
}