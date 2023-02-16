package com.istic.ofbapi.mapper;

import com.istic.ofbapi.model.User;
import com.istic.ofbapi.payload.UserRequest;
import com.istic.ofbapi.payload.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserResponse userToUserResponse(User user);

    User userRequestToUser(UserRequest newUser);
}
