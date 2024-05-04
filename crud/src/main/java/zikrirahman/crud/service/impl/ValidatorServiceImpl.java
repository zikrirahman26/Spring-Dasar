package zikrirahman.crud.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import zikrirahman.crud.service.ValidatorService;

@Service
public class ValidatorServiceImpl implements ValidatorService{

    @Autowired
    private Validator validator;

    @Override
    public void validator(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() != 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
    
}
