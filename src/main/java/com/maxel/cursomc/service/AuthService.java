package com.maxel.cursomc.service;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.repositories.ClienteRepository;
import com.maxel.cursomc.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if(cliente== null) {
           throw new ObjectNotFoundException("Email não encontrado");
        }

        String newPass = newPassword();
        cliente.setSenha(bCryptPasswordEncoder.encode(newPass));

        clienteRepository.save(cliente);
        emailService.sendNewPasswordEail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for(int i = 0; i < 10; i++ ) {
            vet[i] = randomChar();
        }

        return new String(vet);
    }

    private char randomChar() {
        int option = rand.nextInt(3);

        if(option == 0) { //gera dígito
            return (char) (rand.nextInt(10) + 40);
        }
        else if(option == 1) { //gera letra maiúscula
            return (char) (rand.nextInt(26) + 65);
        }
        else { //gera letra minúscula
            return (char) (rand.nextInt(26) + 97);
        }
    }
}
