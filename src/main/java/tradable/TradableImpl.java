package tradable;

import common.ProductValidator;
import common.UserValidator;
import common.BookSide;
import exceptions.InvalidInputException;
import exceptions.InvalidTradableException;
import price.Price;

public class TradableImpl implements Tradable{

    private final String user;
    private final String product;
    private String id;
    private Price price;
    private int originalVolume, remainingVolume;
    private int cancelledVolume = 0;
    private int filledVolume = 0;
    private BookSide side;

    public TradableImpl(
            String userIn,
            String productIn,
            Price priceIn,
            int originalVolumeIn,
            BookSide sideIn
    ) throws InvalidTradableException {

        try {
            this.user = UserValidator.validate(userIn);
            this.product = ProductValidator.validate(productIn);
        } catch (InvalidInputException e) {
            throw new InvalidTradableException("Bad input: " + e.getMessage(), e);
        }
        setPrice(priceIn);
        setOriginalVolume(originalVolumeIn);
        setRemainingVolume(originalVolumeIn);
        setSide(sideIn);
        setId();
    }

    @Override
    public String getId() {
        return this.id;
    }

    private void setId() {
        this.id = this.getUser() + this.getProduct() + this.getPrice() + System.nanoTime();
    }

    @Override
    public int getRemainingVolume() {
        return this.remainingVolume;
    }

    @Override
    public void setCancelledVolume(int newVol) {
        this.cancelledVolume = newVol;
    }

    @Override
    public int getCancelledVolume() {
        return this.cancelledVolume;
    }

    @Override
    public void setRemainingVolume(int newVol) {
        this.remainingVolume = newVol;
    }

    @Override
    public TradableDTO makeTradableDTO() {
        return new TradableDTO(this);
    }

    @Override
    public Price getPrice() {
        return this.price;
    }

    private void setPrice(Price priceIn) throws InvalidTradableException{
        if (priceIn == null) throw new InvalidTradableException("priceIn cannot be null.");
        this.price = priceIn;
    }

    @Override
    public int getFilledVolume() {
        return this.filledVolume;
    }
    @Override
    public void setFilledVolume(int newVol) {
        this.filledVolume = newVol;
    }

    @Override
    public BookSide getSide() {
        return this.side;
    }

    private void setSide(BookSide sideIn) throws InvalidTradableException{
        if (sideIn == null) throw new InvalidTradableException("sideIn cannot be null");
        this.side = sideIn;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    @Override
    public String getProduct() {
        return this.product;
    }


    @Override
    public int getOriginalVolume() {
        return this.originalVolume;
    }

    private void setOriginalVolume(int newVol) throws InvalidTradableException{
        if (newVol <= 0 || newVol >= 10000)
            throw new InvalidTradableException("OriginalVolumeIn must be greater than 0 and less than 10,000");
        this.originalVolume = newVol;
    }
}
