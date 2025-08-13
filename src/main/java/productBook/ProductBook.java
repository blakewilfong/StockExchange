package productBook;

import common.BookSide;
import common.ProductValidator;

import exceptions.DataValidationException;
import exceptions.InvalidInputException;
import exceptions.InvalidProductBookException;

import market.CurrentMarketTracker;
import price.Price;
import quote.Quote;
import tradable.Tradable;
import tradable.TradableDTO;


public class ProductBook {

    private final String product;

    private final ProductBookSide buySide, sellSide;

    public ProductBook(String product) throws InvalidProductBookException {

        try {
            this.product = ProductValidator.validate(product);
        } catch (InvalidInputException e) {
            throw new InvalidProductBookException("Bad input: " + e.getMessage(), e);
        }

        buySide = new ProductBookSide(BookSide.BUY);
        sellSide = new ProductBookSide(BookSide.SELL);
    }

    public TradableDTO add(Tradable t) throws InvalidProductBookException {

        if (t == null) throw new InvalidProductBookException("Tradable can not be null.");
        //System.out.println("**ADD: " + t);

        TradableDTO dto;
        BookSide side = t.getSide();
        try {
            if (side == BookSide.BUY){
                dto = buySide.add(t);
            }
            else dto = sellSide.add(t);
            tryTrade();
            updateMarket();
        }
        catch (DataValidationException e) {
            throw new InvalidProductBookException("Failed to add tradable to ProductBook: " + e.getMessage(), e);
        }
        return dto;
    }

    public TradableDTO[] add(Quote qte) throws InvalidProductBookException {

        if (qte == null) throw new InvalidProductBookException("Quote can not be null");

        removeQuotesForUser(qte.getUser());
        //System.out.println("**ADD: " + qte.getQuoteSide(BookSide.BUY));
        //System.out.println("**ADD: " + qte.getQuoteSide(BookSide.SELL));
        TradableDTO buyDTO;
        TradableDTO sellDTO;

        try {
            buyDTO = buySide.add(qte.getQuoteSide(BookSide.BUY));
            sellDTO = sellSide.add(qte.getQuoteSide(BookSide.SELL));
            tryTrade();
        } catch (DataValidationException e) {
            throw new InvalidProductBookException("Failed to add tradable to ProductBook: " + e.getMessage(), e);
        }
        return new TradableDTO[] {buyDTO, sellDTO};
    }

    public TradableDTO cancel(BookSide side, String orderId) throws InvalidProductBookException {

        ProductBookSide bookSide = (side == BookSide.BUY) ? buySide : sellSide;
        TradableDTO cancelledOrder;
        try{
            cancelledOrder = bookSide.cancel(orderId);
            updateMarket();
        } catch (DataValidationException e) {
            throw new InvalidProductBookException("Failed to cancel: " + e.getMessage(), e);
        }
        if (cancelledOrder == null) throw new InvalidProductBookException("Cancelled order not found.");

        return cancelledOrder;
    }

    public TradableDTO[] removeQuotesForUser(String userName) throws InvalidProductBookException {
        if (userName == null) throw new InvalidProductBookException("userName cannot be null.");

        TradableDTO buyDTO;
        TradableDTO sellDTO;
        try {
            buyDTO = buySide.removeQuotesForUser(userName);
            sellDTO = sellSide.removeQuotesForUser(userName);
        } catch (DataValidationException e) {
            throw new InvalidProductBookException("Failed to removeQuotesForUser: " + e.getMessage(), e);
        }
        updateMarket();
        return new TradableDTO[] {buyDTO, sellDTO};
    }

    public void tryTrade() throws DataValidationException {
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

    public String getTopOfBookString(BookSide side){
        int buyPrice = 0;
        int sellPrice = 0;
        int buyVolume = 0;
        int sellVolume = 0;

        Price buyTop = buySide.topOfBookPrice();
        Price sellTop = sellSide.topOfBookPrice();

        if (buyTop != null){
            buyPrice = buyTop.getPrice();
            buyVolume = buySide.topOfBookVolume();
        }
        if (sellTop != null){
            sellPrice = sellTop.getPrice();
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

        return "--------------------------------------------\n" +
                "Product Book: " + getProduct() + "\n" +
                buySide.toString() +
                sellSide.toString() +
                "--------------------------------------------";
    }

    public String getProduct() {
        return this.product;
    }

    private void updateMarket(){
        CurrentMarketTracker CMT = CurrentMarketTracker.getInstance();
        CMT.updateMarket(this.product, buySide.topOfBookPrice(), buySide.topOfBookVolume(), sellSide.topOfBookPrice(), sellSide.topOfBookVolume());

    }


}
