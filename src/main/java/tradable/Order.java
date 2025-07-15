package tradable;

import book.BookSide;
import price.Price;


public class Order implements Tradable {

    private Tradable tradable;

    public Order(
            String userIn,
            String productIn,
            Price priceIn,
            int originalVolumeIn,
            BookSide sideIn
    ) {
        this.tradable = new TradableImpl(
                userIn,
                productIn,
                priceIn,
                originalVolumeIn,
                sideIn
        );
    }

    @Override
    public String getId() {
        return this.tradable.getId();
    }

    @Override
    public int getRemainingVolume() {
        return this.tradable.getRemainingVolume();
    }

    @Override
    public void setCancelledVolume(int newVol) {
        this.tradable.setCancelledVolume(newVol);
    }

    @Override
    public int getCancelledVolume() {
        return this.tradable.getCancelledVolume();
    }

    @Override
    public void setRemainingVolume(int newVol) {
        this.tradable.setRemainingVolume(newVol);
    }

    @Override
    public TradableDTO makeTradableDTO() {
        return this.tradable.makeTradableDTO();
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
        return getUser() + getSide() + "order: " + getProduct() + "at " + getPrice() + ", Orig vol: " +
                getOriginalVolume() + ", Rem Vol: " + getRemainingVolume() + ", Fill Vol: " + getFilledVolume() +
                ", CXL Vol: " + getCancelledVolume() + ", ID: " + getId();
    }
}

