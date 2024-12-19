package com.test_task.table.mapper;

import com.test_task.table.config.MapperConfig;
import com.test_task.table.dto.UserDto;
import com.test_task.table.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUserModel(UserDto userDto);
}
