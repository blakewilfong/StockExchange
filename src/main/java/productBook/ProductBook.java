package productBook;
import book.BookSide;
import common.productValidator;
import exceptions.InvalidInputException;
import exceptions.InvalidProductBookException;
import price.Price;
import quote.Quote;
import tradable.Tradable;
import tradable.TradableDTO;


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

        if (t == null) throw new InvalidProductBookException("Tradable can not be null.");
        System.out.println("**ADD: " + t);

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


        System.out.println("**ADD: " + qte.getQuoteSide(BookSide.BUY));
        System.out.println("**ADD: " + qte.getQuoteSide(BookSide.SELL));

        TradableDTO buyDTO  = buySide.add(qte.getQuoteSide(BookSide.BUY));
        TradableDTO sellDTO = sellSide.add(qte.getQuoteSide(BookSide.SELL));
        tryTrade();
        return new TradableDTO[] {buyDTO, sellDTO};
    }

    public TradableDTO cancel(BookSide side, String orderId) throws InvalidProductBookException{



        ProductBookSide bookSide = (side == BookSide.BUY) ? buySide : sellSide;
        TradableDTO cancelledOrder = bookSide.cancel(orderId);
        if (cancelledOrder == null) throw new InvalidProductBookException("Cancelled order not found.");

        return cancelledOrder;
    }

    public TradableDTO[] removeQuotesForUser(String userName) throws InvalidProductBookException{
        if (userName == null) throw new InvalidProductBookException("userName cannot be null.");
        TradableDTO buyDTO = buySide.removeQuotesForUser(userName);
        TradableDTO sellDTO = sellSide.removeQuotesForUser(userName);
        return new TradableDTO[] {buyDTO, sellDTO};
    }

    public void tryTrade(){
        Price topBuy = buySide.topOfBookPrice();
        Price topSell = sellSide.topOfBookPrice();
        if (topBuy == null || topSell == null) return;
        int totalToTrade = Math.max(buySide.topOfBookVolume(),sellSide.topOfBookVolume());
        int toTrade;
        while(totalToTrade > 0) {
            topBuy = buySide.topOfBookPrice();
            topSell = sellSide.topOfBookPrice();
            if (topBuy == null || topSell == null || topSell.getPrice() > topBuy.getPrice()) return;
            toTrade = Math.min(buySide.topOfBookVolume(), sellSide.topOfBookVolume());
            buySide.tradeOut(topBuy, toTrade);
            sellSide.tradeOut(topBuy, toTrade);
            totalToTrade -= toTrade;
        }

    }
    //  Top of BUY book: Top of BUY book: $0.00 x 0
    public String getTopOfBookString(BookSide side){
        int buyPrice = 0;
        int sellPrice = 0;
        int buyVolume = 0;
        int sellVolume = 0;

        if (buySide.topOfBookPrice() != null){
            buyPrice = buySide.topOfBookPrice().getPrice();
            buyVolume = buySide.topOfBookVolume();
        }
        if (sellSide.topOfBookPrice() != null){
            sellPrice = sellSide.topOfBookPrice().getPrice();
            sellVolume = sellSide.topOfBookVolume();
        }

        String formattedBuyPrice = String.format("$%.2f", buyPrice / 100.0);
        String formattedSellPrice = String.format("$%.2f", sellPrice / 100.0);

        if (side == BookSide.BUY) {
            return "Top of BUY book: " + formattedBuyPrice + " x " + buyVolume;
        }
        return "Top of SELL book: " + formattedSellPrice + " x " + sellVolume;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------\n");
        sb.append("Product Book: ").append(getProduct()).append("\n");
        sb.append(buySide.toString());
        sb.append(sellSide.toString());
        sb.append("--------------------------------------------");

        return sb.toString();
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
