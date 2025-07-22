package productBook;
import book.BookSide;
import exceptions.InvalidProductBookException;
import price.Price;
import tradable.Tradable;
import tradable.TradableDTO;
import java.util.ArrayList;
import java.util.Set;
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
        else {
            bookEntries.get(o.getPrice()).add(o);
        }
        return new TradableDTO(o);
    }

    public TradableDTO cancel(String tradableId) {

        Tradable tradable;
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
                        return new TradableDTO(tradable);
                    }

                }
            }
        }
        return null;
    }


    public TradableDTO removeQuotesForUser(String userName) {

        TradableDTO dto;
        for (Price key : bookEntries.keySet()) {
            for (Tradable t : bookEntries.get(key)) {
                if (t.getUser().equals(userName)) {
                    dto = cancel(t.getId());
//                    if (bookEntries.get(key).isEmpty()) {
//                        bookEntries.remove(key);
//                        return dto;
//                    }
                    return dto;
                }
            }
        }
        return null;
    }

    public Price topOfBookPrice() {

        if (bookEntries.isEmpty()) return null;
        return (side == BookSide.BUY) ? bookEntries.lastKey() : bookEntries.firstKey();
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

    public void tradeOut(Price price, int volToTrade) {

        Price topPrice = topOfBookPrice();
        if (topPrice == null || topPrice.getPrice() > price.getPrice()) return;

        ArrayList<Tradable> atPrice = bookEntries.get(topPrice);
        int totalVolAtPrice = 0;
        for(Tradable t: atPrice) totalVolAtPrice += t.getRemainingVolume();

        if (volToTrade >= totalVolAtPrice){
            for(Tradable t: atPrice){
                int rv = t.getRemainingVolume();
                t.setFilledVolume(t.getOriginalVolume());
                t.setRemainingVolume(0);

                System.out.println("\t\tFULL FILL: (" + side + t.getFilledVolume() + ") " + t);
                //atPrice.remove(t);
            }
            bookEntries.remove(topOfBookPrice());
            return;
        }

        int remainder = volToTrade;

        for (Tradable t: atPrice){
            double ratio = (double) t.getRemainingVolume() / totalVolAtPrice;
            int toTrade = (int) Math.ceil(volToTrade * ratio);
            toTrade = Math.min(toTrade, remainder);
            t.setFilledVolume(t.getFilledVolume() + toTrade);
            t.setRemainingVolume(t.getRemainingVolume() - toTrade);
            System.out.println("\t\tPARTIAL FILL: (" + side + t.getFilledVolume() + ") " + t);
            remainder -= toTrade;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Set<Price> keys;
        sb.append("Side: ").append(side).append("\n");
        if (side == BookSide.BUY) {
            keys = bookEntries.descendingKeySet();
        } else keys = bookEntries.keySet();
        if (keys.isEmpty()) {
            sb.append("\t\t<Empty>\n");
            return sb.toString();
        }
        for (Price p : keys) {
            sb.append("\t\t").append(p).append(":\n");

            ArrayList<Tradable> entry = bookEntries.get(p);
            for (Tradable t : entry) {
                sb.append("\t\t\t\t").append(t).append("\n");
            }
        }
        return sb.toString();
    }

}
