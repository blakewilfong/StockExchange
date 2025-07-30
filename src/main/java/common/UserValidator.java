package common;

import exceptions.InvalidInputException;

public class UserValidator {

    public static String validate(String user) throws InvalidInputException {
        if ((user.length() != 3) || !(user.matches("[a-zA-Z]+")))
            throw new InvalidInputException("userIn must be 3 alphabetical characters only.");
        return user.toUpperCase();
    }
}