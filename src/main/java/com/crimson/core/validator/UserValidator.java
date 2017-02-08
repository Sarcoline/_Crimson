package com.crimson.core.validator;


import com.crimson.core.dao.UserDAO;
import com.crimson.core.model.User;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

public class UserValidator implements Validator {

    private UserDAO userDAO;

    @Override
    public boolean supports(Class clazz){
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        User user = (User) target;

        List<User> userList = userDAO.getAllUsers();

        for(User userTmp : userList){
            if (userTmp.getName().contains(user.getName())){
                errors.rejectValue("name", "Name already exists!");
            }
            if (userTmp.getEmail().contains(user.getEmail())){
                errors.rejectValue("email", "Email already exists!");
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
