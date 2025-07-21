package common;

import exceptions.InvalidInputException;

public class productValidator {

    private String product;

    public productValidator(String productIn) throws InvalidInputException {
        setProduct(productIn);

    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String productIn) throws InvalidInputException {
        if ((productIn.isEmpty() || productIn.length() > 5) || !(productIn.matches("[a-zA-Z.]+") || productIn.matches(".")))
            throw new InvalidInputException("productIn must be 1-5 alphabetical characters. Can also include '.'.");
        this.product = productIn.toUpperCase();
    }
}