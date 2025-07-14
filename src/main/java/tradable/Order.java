package tradable;

import book.BookSide;
import exceptions.InvalidOrderException;
import price.Price;


public class Order implements Tradable{

    private String user, product, id;
    private Price price;
    private BookSide side;
    private int originalVolume, remainingVolume;
    private int cancelledVolume = 0;
    private int filledVolume = 0;

    public Order(
            String userIn,
            String productIn,
            Price priceIn,
            BookSide sideIn,
            int originalVolumeIn
    ) throws InvalidOrderException {
        if ((userIn.length() != 3) || !(userIn.matches("[a-zA-Z]+")))
            throw new InvalidOrderException("userIn must be 3 alphabetical characters only.");
        this.user = userIn.toUpperCase();

        if ((productIn.isEmpty() || productIn.length() > 5) || !(productIn.matches("[a-zA-Z.]") || productIn.matches(".")))
            throw new InvalidOrderException("productIn must be 1-5 alphabetical characters. Can also include '.'.");
        this.product = productIn.toUpperCase();

        if (priceIn == null) throw new InvalidOrderException("Price object cannot be null.");
        this.price = priceIn;

        if (side == null) throw new InvalidOrderException("sideIn cannot be null");
        this.side = sideIn;

        if (originalVolumeIn <= 0 || originalVolumeIn >= 10000)
            throw new InvalidOrderException("OriginalVolumeIn must be greater than 0 and less than 10,000");
        this.originalVolume = originalVolumeIn;
        this.remainingVolume = originalVolumeIn;
        this.id = this.user + this.product + this.price + System.nanoTime();


    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public int getRemainingVolume() {
        return 0;
    }

    @Override
    public void setCancelledVolume(int newVol) {

    }

    @Override
    public int getCancelledVolume() {
        return 0;
    }

    @Override
    public void setRemainingVolume(int newVol) {

    }

    @Override
    public TradableDTO makeTradableDTO() {
        return null;
    }

    @Override
    public Price getPrice() {
        return null;
    }

    @Override
    public void setFilledVolume(int newVol) {

    }

    @Override
    public int getFilledVolume() {
        return 0;
    }

    @Override
    public BookSide getSide() {
        return null;
    }

    @Override
    public String getUser() {
        return "";
    }

    @Override
    public String getProduct() {
        return "";
    }

    @Override
    public int getOriginalVolume() {
        return 0;
    }
}
