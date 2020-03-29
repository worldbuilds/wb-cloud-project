package org.worldbuild.cloud.auth.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.worldbuild.cloud.auth.JPA.entity.UserInfo;
import org.worldbuild.cloud.auth.JPA.service.UserInfoService;
import org.worldbuild.cloud.auth.modal.OAuth2ApiResponse;
import org.worldbuild.cloud.auth.modal.UserDTO;
import org.worldbuild.cloud.auth.utils.TOTPUtils;

import javax.annotation.PostConstruct;

@Log4j2
@Service
public class LoginServiceImpl implements  LoginService {

    private static String OAUTH2_URL="http://localhost:8071/oauth/token";

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;
    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserInfoService userInfoService;

    @PostConstruct
    public void init(){

    }

    @Override
    public ResponseEntity<OAuth2ApiResponse> login(String username, String password) {
        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization","Basic d2JjbGllbnQ6d2JjbGllbnQ=");
        HttpEntity<String> request = new HttpEntity<String>("parameters", headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(OAUTH2_URL)
                .queryParam("username", username)
                .queryParam("password", password)
                .queryParam("scope","read")
                .queryParam("grant_type","password");
        return restTemplate.postForEntity(builder.toUriString(),request,OAuth2ApiResponse.class);
    }

    @Override
    public UserDTO save(UserDTO useDTO) {
        UserInfo user= new UserInfo();
        user.setName(useDTO.getName());
        user.setEmail(useDTO.getEmail());
        user.setPhone(useDTO.getPhone());
        user.setAddress(useDTO.getAddress());
        user.setSecretKey(TOTPUtils.generateSecret());
        user.setEnabled(useDTO.isEnabled());
        user.setGoogleAuth(useDTO.isGoogleAuth());
        //
        user.setUsername(useDTO.getUsername());
        user.setPassword(passwordEncoder.encode(useDTO.getPassword()));
        user= userInfoService.save(user);
        return UserDTO.generateUserDTO(user);

    }

    @Override
    public UserDTO update(Integer userId, UserDTO useDTO) {
        UserInfo user=userInfoService.findById(userId);
        user.setName(useDTO.getName());
        user.setEmail(useDTO.getEmail());
        user.setPhone(useDTO.getPhone());
        user.setAddress(useDTO.getAddress());
        user.setEnabled(useDTO.isEnabled());
        //
        user.setPassword(passwordEncoder.encode(useDTO.getPassword()));
        user= userInfoService.save(user);
        return UserDTO.generateUserDTO(user);
    }
}
