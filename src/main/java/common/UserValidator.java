package common;

import exceptions.InvalidInputException;

public class UserValidator {

    private String user;

    public UserValidator(String userIn) throws InvalidInputException {
        setUser(userIn);

    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String userIn) throws InvalidInputException {
        if ((userIn.length() != 3) || !(userIn.matches("[a-zA-Z]+")))
            throw new InvalidInputException("userIn must be 3 alphabetical characters only.");
        this.user = userIn.toUpperCase();
    }
}