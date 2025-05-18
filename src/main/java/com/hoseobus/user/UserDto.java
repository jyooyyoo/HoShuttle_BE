package com.hoseobus.user;

import lombok.*;

public class UserDto {

    @Getter @Setter
    @NoArgsConstructor
    public static class Request {
        private String email;
        private String name;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String email;
        private String name;
        private String role;

        public static Response from(User user) {
            return new Response(user.getId(), user.getEmail(), user.getName(), user.getRole());
        }
    }
}
