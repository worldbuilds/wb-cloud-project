package org.worldbuild.cloud.auth.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.worldbuild.cloud.auth.constants.View;
import org.worldbuild.cloud.auth.modal.OAuth2ApiResponse;
import org.worldbuild.cloud.auth.modal.UserDTO;
import org.worldbuild.cloud.auth.service.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Log4j2
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
    public String login(Principal principal,HttpServletRequest request, HttpServletResponse response){
        return View.Login.LOGIN;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Principal principal,HttpServletRequest request, HttpServletResponse response){
        return View.Login.REGISTER;
    }

    @RequestMapping(value = "/rest/home", method = RequestMethod.GET)
    public String home(Principal principal,HttpServletRequest request, HttpServletResponse response){
        log.info(principal);
        return View.Dashboard.DASHBOARD;
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<OAuth2ApiResponse> login(@RequestParam("username")String username, @RequestParam("password")String password) {
        log.info("Username- " + username + " , Password- " + password);
        try {
            return loginService.login(username, password);
        } catch (Exception e) {
            log.error("Exception- ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> register(@ModelAttribute UserDTO useDTO){
        UserDTO userDTO=loginService.save(useDTO);
        return ResponseEntity.ok(userDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> updateProfile(@PathVariable("id") Integer id,@ModelAttribute UserDTO useDTO){
        UserDTO userDTO=loginService.update(id,useDTO);
        return ResponseEntity.ok(userDTO);
    }
}
