package com.crimson.core.validator;


import com.crimson.core.dto.UserDTO;
import com.crimson.core.model.User;
import com.crimson.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class clazz){
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        UserDTO userDTO = (UserDTO) target;
        List<User> userList = userService.getAllUsers();
        for(User userTmp : userList){
            if (userTmp.getName().contains(userDTO.getName())){
                errors.rejectValue("name", "", "User already exist");
            }
            if (userTmp.getEmail().contains(userDTO.getEmail())){
                errors.rejectValue("email", "","Email already exists!");
            }
        }
    }
}
