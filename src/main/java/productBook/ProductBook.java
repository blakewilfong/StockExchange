package productBook;
import book.BookSide;
import common.productValidator;
import exceptions.InvalidInputException;
import exceptions.InvalidProductBookException;
import quote.Quote;
import tradable.Tradable;
import tradable.TradableDTO;

public class ProductBook {

    private String product;
    private final productValidator productValidator;

    public ProductBook(String product) throws InvalidInputException, InvalidProductBookException {

        productValidator = new productValidator(product);
        setProduct(product);
        ProductBookSide buySide = new ProductBookside();
        ProductBookSide sellSide = new ProductBookside();
    }


    // TODO
    //  Call the “add” method of the ProductBookSide the tradable is for (BUY or SELL) and save the
    //  TradableDTO that is returned from the “add” call. Call “tryTrade” (seen below), then return the

    public TradableDTO add(Tradable t) throws InvalidProductBookException{
        System.out.println("**ADD: " + t);
        if (t == null) throw new InvalidProductBookException("Tradable can not be null.");


    }


    // TODO
    //  First, call removeQuotesForUser (passing the user id as the parameter).
    //  Call the “add” method of the BUY ProductBookSide, passing the BUY-side of the quote, and save the
    //  TradableDTO that is returned from the “add” call.
    //  Call the “add” method of the SELL ProductBookSide, passing the SELL-side of the quote, and save the
    //  TradableDTO that is returned from the “add” call.
    //  Call “tryTrade” (seen below)
    //  Return both TradableDTOs in an array of TradableDTOs (BUY side DTO is index 0, SELL side DTO is index
    //  1)

    public TradableDTO[] add(Quote qte) throws InvalidProductBookException{
        if (qte == null) throw new InvalidProductBookException("Quote can not be null");


    }

    // TODO
    //  [Method is used for Orders] Call the “cancel” method of the ProductBookSide the order is for
    //  (BUY or SELL) and return the resulting TradableDTO.

    public TradableDTO cancel(BookSide side, String orderId){

    }

    // TODO
    //  [Method is used for Quotes] This method should do the following:
    //  Call the “removeQuotesForUser” method of the BUY ProductBookSide, passing the String userName,
    //  and save the TradableDTO that is returned.
    //  Call the “removeQuotesForUser” method of the SELL ProductBookSide, passing String userName, and
    //  save the TradableDTO that is returned.
    //  Then return both TradableDTOs in an array of TradableDTOs (BUY side DTO is index 0, SELL side DTO is
    //  index 1).

    public TradableDTO[] removeQuotesForUser(String userName){

    }

    // TODO
    //   Checks to see if the book sides are tradable, and if so, perform the trades. If not, it
    //   does nothing. See diagram in Appendix A for how this should work.
    //   See diagram in Appendix A for how this should work.

    public void tryTrade(){

    }

    // TODO
    //  This method returns a String summary of the top-of-book for the specified side.
    //  The String should be returned as follows:
    //  "Top of BUY book: Top of BUY book: $122.50 x 75 -OR-
    //  Top of SELL book: Top of SELL book: $122.90 x 100"

    public String getTopOfBookString(Side side){

    }
    // TODO
    //  Override the toString method to generate a String containing a summary of the book
    //  content as follows (be sure to let the ProductBookSides generate their part of the String).
    @Override
    public String toString()

    public String getProduct() {
        return this.product;
    }

    private void setProduct(String productIn) throws InvalidProductBookException {
        try {
            this.productValidator.setProduct(productIn);
        } catch (InvalidInputException e) {
            throw new InvalidProductBookException(e.getMessage());
        }
        this.product = this.productValidator.getProduct();
    }
}
