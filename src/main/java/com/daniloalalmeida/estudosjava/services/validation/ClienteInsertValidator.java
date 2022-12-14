package com.daniloalalmeida.estudosjava.services.validation;

import com.daniloalalmeida.estudosjava.domain.Cliente;
import com.daniloalalmeida.estudosjava.domain.enums.TipoCliente;
import com.daniloalalmeida.estudosjava.dto.ClienteNewDTO;
import com.daniloalalmeida.estudosjava.exceptions.FieldMessage;
import com.daniloalalmeida.estudosjava.repositories.ClienteRepository;
import com.daniloalalmeida.estudosjava.services.validation.utils.CpfAndCnpjValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteInsert ann) {}

    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !CpfAndCnpjValidator.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido."));
        }

        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !CpfAndCnpjValidator.isValidCNPJ(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido."));
        }

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if(aux != null) {
            list.add(new FieldMessage("email", "E-mail já existente."));
        }

        for(FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
