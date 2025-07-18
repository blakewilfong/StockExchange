package tradable;

import common.productValidator;
import common.userValidator;
import book.BookSide;
import exceptions.InvalidInputException;
import exceptions.InvalidTradableException;
import price.Price;

public class TradableImpl implements Tradable{


    private final productValidator productValidator;
    private final userValidator userValidator;

    private String user, product, id;
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
    ) throws InvalidInputException, InvalidTradableException {

        this.productValidator = new productValidator(productIn);
        this.userValidator = new userValidator(userIn);
        setUser(userIn);
        setProduct(productIn);
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
        if (side == null) throw new InvalidTradableException("sideIn cannot be null");
        this.side = sideIn;
    }

    @Override
    public String getUser() {
        return this.user;
    }

    private void setUser(String userIn) throws InvalidTradableException{
        try {
            this.userValidator.setUser(userIn);
        } catch (InvalidInputException e) {
            throw new InvalidTradableException(e.getMessage());
        }
        this.user = this.userValidator.getUser();
    }

    @Override
    public String getProduct() {
        return this.product;
    }

    private void setProduct(String productIn) throws InvalidTradableException{
        try {
            this.productValidator.setProduct(productIn);
        } catch (InvalidInputException e) {
            throw new InvalidTradableException(e.getMessage());
        }
        this.product = this.productValidator.getProduct();
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
