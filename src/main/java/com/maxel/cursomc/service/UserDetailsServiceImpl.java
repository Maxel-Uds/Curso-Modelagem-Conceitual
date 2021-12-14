package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.repositories.ClienteRepository;
import com.maxel.cursomc.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Cliente cli = clienteRepository.findByEmail(email);
        if(cli == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserSpringSecurity(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfil());
    }
}
