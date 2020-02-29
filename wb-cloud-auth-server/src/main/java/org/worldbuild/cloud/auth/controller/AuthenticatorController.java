package org.worldbuild.cloud.auth.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.worldbuild.cloud.auth.JPA.entity.UserInfo;
import org.worldbuild.cloud.auth.service.TOTPService;

@Log4j2
@RestController
@RequestMapping("/rest/")
public class AuthenticatorController {

    @Autowired
    private TOTPService timeOTPService;

    @RequestMapping(value = "/authenticate/{authKey}", method = RequestMethod.GET)
    public ResponseEntity<String> register(@PathVariable(value = "authKey",required = true) String authKey, Authentication authentication){
        UserInfo userInfo= (UserInfo) authentication.getPrincipal();
        log.info("Google Authenticator Key- "+authKey+" Secretkey- "+userInfo.getSecretKey());
        if(timeOTPService.verifyCode(authKey,userInfo.getSecretKey())) {
            return ResponseEntity.ok("MATCHED");
        }else{
            return ResponseEntity.ok("UNMATCHED");
        }
    }
}
