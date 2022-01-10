package com.maxel.cursomc.dto;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.service.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteUpdateDTO implements Serializable {

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min=5, max=120, message = "O tamanho deve ser de 5 a 120 caracteres")
    private String nome;
    @Email(message = "Email inválido")
    @NotEmpty(message = "Preenchimento obrigatório")
    private String email;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String telefone1;
    private String telefone2;
    private String telefone3;
    @NotEmpty(message = "Preenchimento obrigatório")
    private String cpfOuCnpj;

    public ClienteUpdateDTO() {}

    public ClienteUpdateDTO(Cliente cliente) {
        this.nome = cliente.getNome();
        this.email = cliente.getEmail();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }
}
