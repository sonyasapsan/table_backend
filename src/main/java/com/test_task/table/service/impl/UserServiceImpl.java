package com.test_task.table.service.impl;

import com.test_task.table.dto.UserDto;
import com.test_task.table.exception.AddUserException;
import com.test_task.table.exception.EntityNotFoundException;
import com.test_task.table.mapper.UserMapper;
import com.test_task.table.model.User;
import com.test_task.table.repository.UserRepository;
import com.test_task.table.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final static String USER_NOT_FOUND_MESSAGE = "This user is not found";
    private final static String THIS_USER_ALREADY_EXISTS = "The user with such email already exists. Email: ";
    private UserRepository userRepository;
    private UserMapper userMapper;


    @Override
    public List<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .collect(Collectors.toList());
    }

    @Override
    public UserDto save(UserDto user) {
        if (userRepository.existsByEmail(user.email())) {
            throw new AddUserException(THIS_USER_ALREADY_EXISTS + user.email());
        }
        User newUser = userMapper.toUserModel(user);
        return userMapper.toUserDto(userRepository.save(newUser));
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto updateById(Long id, UserDto user) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException(USER_NOT_FOUND_MESSAGE);
        }
        User updatedUser = userMapper.toUserModel(user);
        updatedUser.setId(id);
        return userMapper.toUserDto(userRepository.save(updatedUser));
    }
}
