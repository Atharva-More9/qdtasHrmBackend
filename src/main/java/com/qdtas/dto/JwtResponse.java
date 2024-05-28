package com.qdtas.dto;

import com.qdtas.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
        private User user;
        private String token;

        public JwtResponse(User user, String token) {
            this.user = user;
            this.token = token;
        }

}
