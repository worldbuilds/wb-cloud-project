package org.worldbuild.cloud.auth.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.worldbuild.cloud.auth.constants.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Log4j2
@Controller
public class RootController {

    @RequestMapping(value = "/",method = RequestMethod.GET)
     public  String login(Principal principal, HttpServletRequest request, HttpServletResponse response){
         System.out.println(principal);
         return View.Dashboard.HOME;
     }

     @ResponseBody
     @RequestMapping(value = "/user",method = RequestMethod.GET)
     public  Principal user(Principal principal, HttpServletRequest request, HttpServletResponse response){
        return principal;
    }


}
