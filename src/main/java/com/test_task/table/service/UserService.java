package com.test_task.table.service;

import com.test_task.table.dto.UserDto;
import com.test_task.table.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> findAll(Pageable pageable);

    UserDto save(UserDto user);

    void deleteById(Long id);

    UserDto updateById(Long id, UserDto user);
}
