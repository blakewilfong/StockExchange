package productBook;
import book.BookSide;
import exceptions.InvalidProductBookException;
import price.Price;
import quote.Quote;
import tradable.Tradable;
import tradable.TradableDTO;
import java.util.ArrayList;
import java.util.TreeMap;


public class ProductBookSide {

    final TreeMap<Price, ArrayList<Tradable>> bookEntries;
    BookSide side;

    public ProductBookSide(BookSide sideIn) throws InvalidProductBookException {

        if (sideIn == null) throw new InvalidProductBookException("sideIn cannot be null.");
        side = sideIn;
        bookEntries = new TreeMap<>();
    }

    public TradableDTO add(Tradable o) {

        if (!bookEntries.containsKey(o.getPrice())) {
            bookEntries.put(o.getPrice(), new ArrayList<Tradable>());
            bookEntries.get(o.getPrice()).add(o);
        }
        return new TradableDTO(o);
    }

    public TradableDTO cancel(String tradableId) {

        Tradable tradable = null;
        for (Price key : bookEntries.keySet()) {
            for (Tradable t : bookEntries.get(key)) {
                if (t.getId().equals(tradableId)) {
                    tradable = t;
                    System.out.println("**CANCEL: " + tradable);
                    bookEntries.get(key).remove(t);
                    tradable.setCancelledVolume(tradable.getCancelledVolume() + tradable.getRemainingVolume());
                    tradable.setRemainingVolume(0);
                    if (bookEntries.get(key).isEmpty()) {
                        bookEntries.remove(key);
                    }
                    break;
                }
            }
        }
        return (tradable == null ? null : new TradableDTO(tradable));
    }


    public TradableDTO removeQuotesForUser(String userName) {

        TradableDTO dto = null;
        for (Price key : bookEntries.keySet()) {
            for (Tradable t : bookEntries.get(key)) {
                if (t.getUser().equals(userName)) {
                    dto = cancel(t.getId());
                    if (bookEntries.get(key).isEmpty()) {
                        bookEntries.remove(key);
                    }
                    break;
                }
            }
        }
        return dto;
    }

    public Price topOfBookPrice() {

        if (bookEntries.isEmpty()) return null;
        return bookEntries.firstKey();
    }

    public int topOfBookVolume() {

        int totalVolume = 0;

        if (side == BookSide.BUY) {
            for (Tradable t : bookEntries.get(bookEntries.lastKey())) {
                totalVolume += t.getRemainingVolume();

            }
            return totalVolume;
        }
        for (Tradable t : bookEntries.get(bookEntries.firstKey())) {
            totalVolume += t.getRemainingVolume();
        }
        return totalVolume;
    }

    //TODO
    //  Trade out any tradablesat or better than the Price passed in, up to
    //  the volume value passed in. See diagram in Appendix B for how this should work.

    public void tradeOut(Price price, int vol) {

    }

    //TODO
    //  Fix this toString()
    @Override
    public String toString() {
        if (side == BookSide.BUY){
            //Side: BUY
            System.out.println("Side: BUY");
            //for each price key
            //Price: $9.95
            System.out.println("\tPrice: " + );
            //for each item in that price keys array list of tradables
            //CCC order: BUY AMZN at $9.95, Orig Vol: 70, Rem Vol: 70, Fill Vol: 0, CXL Vol: 0, ID: CCCAMZN$9.95189383477035100

        }

    }

}
