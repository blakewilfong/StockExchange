package productBook;
import book.BookSide;
import exceptions.InvalidProductBookException;
import price.Price;
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

    // TODO
    //  Find the tradable using the tradable id passed in (search the
    //  ArrayLists at each price key in the bookEntries HashMap to find it).

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
        return (tradable == null? null: new TradableDTO(tradable));
    }



public TradableDTO removeQuotesForUser(String userName){

}

public Price topOfBookPrice(){

}

public int topOfBookVolume(){

}

public void tradeOut(Price price, int vol){

}

@Override
public String toString(){

}
}

