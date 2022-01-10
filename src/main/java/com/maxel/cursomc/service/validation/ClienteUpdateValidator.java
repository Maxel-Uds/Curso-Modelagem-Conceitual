package com.maxel.cursomc.service.validation;

import com.maxel.cursomc.domain.Cliente;
import com.maxel.cursomc.dto.ClienteUpdateDTO;
import com.maxel.cursomc.repositories.ClienteRepository;
import com.maxel.cursomc.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteUpdateDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann) {
    }

    @Override
    public boolean isValid(ClienteUpdateDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        Cliente obj = clienteRepository.findByEmail(objDto.getEmail());
        if(obj != null && !obj.getCpfOuCnpj().equals(objDto.getCpfOuCnpj())) {
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
