package com.maxel.cursomc.service.validation;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.domain.enums.TipoCliente;
import com.maxel.cursomc.dto.ClienteNewDTO;
import com.maxel.cursomc.repositories.ClienteRepository;
import com.maxel.cursomc.resources.exceptions.FieldMessage;
import com.maxel.cursomc.service.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        Cliente alreadyExists = clienteRepository.findByCpfOuCnpj(objDto.getCpfOuCnpj());

        if(alreadyExists != null && objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF já cadastrado"));
        }

        if(alreadyExists != null && objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ já cadastrado"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente obj = clienteRepository.findByEmail(objDto.getEmail());
        if(obj != null) {
            list.add(new FieldMessage("email", "O email já existe"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}