package productBook;
import book.BookSide;
import common.productValidator;
import exceptions.InvalidInputException;
import exceptions.InvalidProductBookException;
import quote.Quote;
import tradable.Tradable;
import tradable.TradableDTO;
import productBook.ProductBookSide;


public class ProductBook {

    private String product;
    private final productValidator productValidator;
    private final ProductBookSide buySide, sellSide;

    public ProductBook(String product) throws InvalidInputException, InvalidProductBookException {

        productValidator = new productValidator(product);
        setProduct(product);
        buySide = new ProductBookSide(BookSide.BUY);
        sellSide = new ProductBookSide(BookSide.SELL);
    }

    public TradableDTO add(Tradable t) throws InvalidProductBookException{

        System.out.println("**ADD: " + t);
        if (t == null) throw new InvalidProductBookException("Tradable can not be null.");

        TradableDTO dto;
        BookSide side = t.getSide();

        if (side == BookSide.BUY){
            dto = buySide.add(t);
        }
        else dto = sellSide.add(t);
        tryTrade();
        return dto;
    }

    public TradableDTO[] add(Quote qte) throws InvalidProductBookException{

        if (qte == null) throw new InvalidProductBookException("Quote can not be null");

        removeQuotesForUser(qte.getUser());
        TradableDTO buyDTO  = buySide.add(qte.getQuoteSide(BookSide.BUY));
        TradableDTO sellDTO = sellSide.add(qte.getQuoteSide(BookSide.SELL));
        tryTrade();
        return new TradableDTO[] {buyDTO, sellDTO};
    }

    public TradableDTO cancel(BookSide side, String orderId){

        ProductBookSide bookSide = (side == BookSide.BUY) ? buySide : sellSide;
        return bookSide.cancel(orderId);
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

        TradableDTO buyDTO = buySide.removeQuotesForUser(userName);
        TradableDTO sellDTO = sellSide.removeQuotesForUser(userName);
        return new TradableDTO[] {buyDTO, sellDTO};
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

    public String getTopOfBookString(BookSide side){

        return null;
    }
    // TODO
    //  Override the toString method to generate a String containing a summary of the book
    //  content as follows (be sure to let the ProductBookSides generate their part of the String).
    @Override
    public String toString(){

        return null;
    }



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
