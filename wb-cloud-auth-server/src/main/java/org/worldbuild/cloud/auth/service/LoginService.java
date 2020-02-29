package org.worldbuild.cloud.auth.service;

import org.springframework.http.ResponseEntity;
import org.worldbuild.cloud.auth.JPA.entity.UserInfo;
import org.worldbuild.cloud.auth.modal.OAuth2ApiResponse;
import org.worldbuild.cloud.auth.modal.UserDTO;

public interface LoginService {

    ResponseEntity<OAuth2ApiResponse> login(String username, String password);

    UserDTO save(UserDTO useDTO);

    UserDTO update(Integer userId, UserDTO useDTO);
}
