package com.hoseobus.user;

import com.hoseobus.common.CustomException;
import com.hoseobus.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto.Response createUser(UserDto.Request request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(request.getPassword()) // 나중에 인코딩 필요
                .role("ROLE_USER")
                .build();

        return UserDto.Response.from(userRepository.save(user));
    }

    @Override
    public UserDto.Response getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return UserDto.Response.from(user);
    }

    @Override
    public List<UserDto.Response> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto.Response::from)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        userRepository.deleteById(id);
    }
}
