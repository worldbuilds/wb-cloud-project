package org.worldbuild.cloud.auth.JPA.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.worldbuild.cloud.auth.JPA.entity.UserInfo;
import org.worldbuild.cloud.auth.JPA.repository.UserInfoRepository;

import javax.annotation.PostConstruct;

@Log4j2
@Service(value = "customUserDetailsService")
public class CustomUserDetailService implements  UserInfoService, UserDetailsService {

    @PostConstruct
    public void init(){
    }

    @Autowired
    @Qualifier(value = "passwordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo findById(Integer userId) { return  userInfoRepository.findById(userId).get(); }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo=userInfoRepository.findByUsername(username);
        log.info(userInfo);
        if(userInfo==null||!userInfo.isEnabled()){
            log.info("User does not exists "+username);
            throw  new UsernameNotFoundException("User does not exists "+username);
        }
        return userInfo;
    }
}
