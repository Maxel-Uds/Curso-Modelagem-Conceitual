package com.maxel.cursomc.resources;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.dto.EmailDTO;
import com.maxel.cursomc.dto.NewPasswordDTO;
import com.maxel.cursomc.repositories.ClienteRepository;
import com.maxel.cursomc.security.JWTUtil;
import com.maxel.cursomc.security.UserSpringSecurity;
import com.maxel.cursomc.service.AuthService;
import com.maxel.cursomc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthResources {

    @Autowired
    private AuthService authService;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="/refresh-token", method= RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSpringSecurity loggedUser = UserService.authenticated();
        String token = jwtUtil.generateToken(loggedUser.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/forgot-pass", method = RequestMethod.POST)
    public ResponseEntity<Void> forgotPass(@Valid @RequestBody EmailDTO emailDTO) {
        authService.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/change-pass", method = RequestMethod.POST)
    public ResponseEntity<Void> changePass(@Valid @RequestBody NewPasswordDTO newPassDTO) {
        authService.changePassword(newPassDTO);
        return ResponseEntity.noContent().build();
    }
}
