package com.test_task.table.controller;

import com.test_task.table.dto.UserDto;
import com.test_task.table.model.User;
import com.test_task.table.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;


@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
public class UserController {
    private UserService userService;

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@RequestBody @Valid UserDto requestDto,
                          @PathVariable @Positive Long id){
        System.out.println("В рот єбала: " + requestDto.firstName()
                + " " + requestDto.lastName() );
        return userService.updateById(id, requestDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto create(@RequestBody @Valid UserDto userDto) {
        return userService.save(userDto);
    }

    @GetMapping
    public List<User> getAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable @Positive Long id) {
        userService.deleteById(id);
    }
}
