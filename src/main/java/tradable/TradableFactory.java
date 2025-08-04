package tradable;

import common.BookSide;
import exceptions.InvalidTradableException;
import price.Price;

public class TradableFactory {
    public static Tradable create(
            String userIn,
            String productIn,
            Price priceIn,
            int originalVolumeIn,
            BookSide sideIn
    ) throws InvalidTradableException {
        return new TradableImpl(userIn, productIn, priceIn, originalVolumeIn, sideIn);
    }
}