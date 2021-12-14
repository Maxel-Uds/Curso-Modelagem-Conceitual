package com.maxel.cursomc.resources;

import com.maxel.cursomc.security.JWTUtil;
import com.maxel.cursomc.security.UserSpringSecurity;
import com.maxel.cursomc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthResources {

    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="/refresh-token", method= RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSpringSecurity loggedUser = UserService.authenticated();
        String token = jwtUtil.generateToken(loggedUser.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
