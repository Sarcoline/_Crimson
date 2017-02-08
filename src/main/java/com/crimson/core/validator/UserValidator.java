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
                errors.rejectValue("name", "", "Name already exist");
            }
            if (userTmp.getEmail().contains(userDTO.getEmail())){
                errors.rejectValue("email", "","Email already exists!");
            }
        }
    }

    //HOW TO USE THIS VALIDATION
    /*
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public createUser(Model model, @ModelAttribute("user") User user, BindingResult result){
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, result);

        if (result.hasErrors()){
            // do something
        }
        else {
            // do something else
        }
    }
    If there are validation errors, result.hasErrors() will be true.
    */

}
