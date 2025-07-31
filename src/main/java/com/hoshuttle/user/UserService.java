package com.hoshuttle.user;


import java.util.List;

public interface UserService {
    UserDto.Response createUser(UserDto.Request request);
    UserDto.Response getUser(Long id);
    List<UserDto.Response> getAllUsers();
    void deleteUser(Long id);
}
