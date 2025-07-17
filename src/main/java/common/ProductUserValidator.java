package common;

import exceptions.InvalidInputException;


// maintains logic for user and product validation as used in Quote and TradeableImpl
public class ProductUserValidator {

    private String user, product;

    public ProductUserValidator(String userIn, String productIn) throws InvalidInputException {
        setUser(userIn);
        setProduct(productIn);
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String userIn) throws InvalidInputException {
        if ((userIn.length() != 3) || !(userIn.matches("[a-zA-Z]+")))
            throw new InvalidInputException("userIn must be 3 alphabetical characters only.");
        this.user = userIn.toUpperCase();
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String productIn) throws InvalidInputException{
        if ((productIn.isEmpty() || productIn.length() > 5) || !(productIn.matches("[a-zA-Z.]") || productIn.matches(".")))
            throw new InvalidInputException("productIn must be 1-5 alphabetical characters. Can also include '.'.");
        this.product = productIn.toUpperCase();
    }
}
