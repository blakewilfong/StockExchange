package market;

import price.Price;

public class CurrentMarketSide {

    private final Price price;
    private final int volume;

    CurrentMarketSide(Price priceIn, int volumeIn){
        price = priceIn;
        volume = volumeIn;
    }

    @Override
    public String toString() {
        return price + "x" + volume;
    }


}
