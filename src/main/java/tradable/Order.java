package tradable;

import book.BookSide;
import exceptions.InvalidInputException;
import exceptions.InvalidTradableException;
import price.Price;


public class Order implements Tradable {

    private final Tradable tradable;

    public Order(
            String userIn,
            String productIn,
            Price priceIn,
            int originalVolumeIn,
            BookSide sideIn
    ) throws InvalidInputException, InvalidTradableException {
        tradable = new TradableImpl(
                userIn,
                productIn,
                priceIn,
                originalVolumeIn,
                sideIn
        );
    }

    @Override
    public String getId() {
        return tradable.getId();
    }

    @Override
    public void setRemainingVolume(int newVol) {
        tradable.setRemainingVolume(newVol);
    }

    @Override
    public int getRemainingVolume() {
        return tradable.getRemainingVolume();
    }

    @Override
    public void setCancelledVolume(int newVol) {
        tradable.setCancelledVolume(newVol);
    }

    @Override
    public int getCancelledVolume() {
        return tradable.getCancelledVolume();
    }

    @Override
    public TradableDTO makeTradableDTO() {
        return tradable.makeTradableDTO();
    }

    @Override
    public Price getPrice() {
        return this.tradable.getPrice();
    }

    @Override
    public void setFilledVolume(int newVol) {
        this.tradable.setFilledVolume(newVol);
    }

    @Override
    public int getFilledVolume() {
        return this.tradable.getFilledVolume();
    }

    @Override
    public BookSide getSide() {
        return this.tradable.getSide();
    }

    @Override
    public String getUser() {
        return this.tradable.getUser();
    }

    @Override
    public String getProduct() {
        return this.tradable.getProduct();
    }

    @Override
    public int getOriginalVolume() {
        return this.tradable.getOriginalVolume();
    }


    @Override
    public String toString(){
        return getUser() + " " + getSide() + " order: " + getProduct() + " at " + getPrice() + ", Orig Vol: " +
                getOriginalVolume() + ", Rem Vol: " + getRemainingVolume() + ", Fill Vol: " + getFilledVolume() +
                ", CXL Vol: " + getCancelledVolume() + ", ID: " + getId();
    }
}

