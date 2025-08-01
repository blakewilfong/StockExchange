package quote;

import common.BookSide;
import common.ProductValidator;
import common.UserValidator;
import exceptions.InvalidInputException;
import exceptions.InvalidTradableException;
import price.Price;
import tradable.QuoteSide;

public class Quote {


    private final String user, product;
    private QuoteSide buySide, sellSide;


    public Quote(String symbol,
                 Price buyPrice,
                 int buyVolume,
                 Price sellPrice,
                 int sellVolume,
                 String userName) throws InvalidTradableException {

        try {
            this.user = UserValidator.validate(userName);
            this.product = ProductValidator.validate(symbol);
        } catch (InvalidInputException e) {
            throw new InvalidTradableException("Invalid input: " + e.getMessage(), e);
        }

        setBuySide(userName, symbol, buyPrice, buyVolume);
        setSellSide(userName, symbol, sellPrice, sellVolume);

    }



    public String getUser(){
        return this.user;
    }

    private void setBuySide(String userName, String symbol, Price price, int volume) throws InvalidTradableException {

        this.buySide = new QuoteSide(userName, symbol, price, volume, BookSide.BUY);
    }

    private void setSellSide(String userName, String symbol, Price price, int volume) throws InvalidTradableException {

        this.sellSide = new QuoteSide(userName, symbol, price, volume, BookSide.SELL);
    }

    public QuoteSide getQuoteSide(BookSide sideIn){
        return (sideIn == BookSide.BUY) ? buySide : sellSide;
    }

    public String getProduct(){
        return this.product;
    }

}
