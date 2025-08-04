package tradable;

import common.BookSide;
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
    ) throws InvalidTradableException {
        this.tradable = TradableFactory.create(userIn, productIn, priceIn, originalVolumeIn, sideIn);

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

        return getUser() + " " + getSide() + " side order for " + getProduct() + ": Price: " + getPrice() + ", Orig Vol:  " +
                getOriginalVolume() + ", Rem Vol:  " + getRemainingVolume() + ", Fill Vol:  " + getFilledVolume() +
                ", Cxl'd Vol:   " + getCancelledVolume() + ", ID: " + getId();
    }
}

