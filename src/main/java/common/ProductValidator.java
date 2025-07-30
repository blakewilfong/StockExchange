package common;

import exceptions.InvalidInputException;

public class ProductValidator {

    public static String validate(String productIn) throws InvalidInputException {
        if ((productIn.isEmpty() || productIn.length() > 5) || !(productIn.matches("[a-zA-Z.]+") || productIn.matches(".")))
            throw new InvalidInputException("productIn must be 1-5 alphabetical characters. Can also include '.'.");
        return productIn.toUpperCase();

    }
}