package quote;
import common.BookSide;
import common.productValidator;
import common.userValidator;

import exceptions.InvalidInputException;
import exceptions.InvalidQuoteException;
import exceptions.InvalidTradableException;
import price.Price;
import tradable.QuoteSide;

public class Quote {

    private final productValidator productValidator;
    private final userValidator userValidator;

    private String user, product;
    private QuoteSide buySide, sellSide;


    public Quote(String symbol,
                 Price buyPrice,
                 int buyVolume,
                 Price sellPrice,
                 int sellVolume,
                 String userName) throws InvalidQuoteException, InvalidInputException, InvalidTradableException {


        this.productValidator = new productValidator(symbol);
        this.userValidator = new userValidator(userName);
        setUser(userName);
        setProduct(symbol);
        setBuySide(userName, symbol, buyPrice, buyVolume);
        setSellSide(userName, symbol, sellPrice, sellVolume);

    }

    private void setUser(String userIn) throws InvalidQuoteException {
        try {
            this.userValidator.setUser(userIn);
        } catch (InvalidInputException e) {
            throw new InvalidQuoteException(e.getMessage());
        }
        this.user = this.userValidator.getUser();
    }

    public String getUser(){
        return this.user;
    }

    private void setBuySide(String userName, String symbol, Price price, int volume) throws InvalidInputException, InvalidTradableException {

        this.buySide = new QuoteSide(userName, symbol, price, volume, BookSide.BUY);
    }

    private void setSellSide(String userName, String symbol, Price price, int volume) throws InvalidInputException, InvalidTradableException {

        this.sellSide = new QuoteSide(userName, symbol, price, volume, BookSide.SELL);
    }

    public QuoteSide getQuoteSide(BookSide sideIn){
        return (sideIn == BookSide.BUY) ? buySide : sellSide;
    }

    private void setProduct(String productIn) throws InvalidQuoteException{
        try {
            this.productValidator.setProduct(productIn);
        } catch (InvalidInputException e) {
            throw new InvalidQuoteException(e.getMessage());
        }
        this.product = this.productValidator.getProduct();
    }

    public String getProduct(){
        return this.product;
    }

}
