package productBook;

import common.BookSide;
import exceptions.DataValidationException;
import exceptions.InvalidProductBookException;
import quote.Quote;
import tradable.Tradable;
import tradable.TradableDTO;
import user.UserManager;

import java.util.Random;
import java.util.HashMap;

public final class ProductManager {

    private static ProductManager instance;
    private final HashMap<String, ProductBook> productMap = new HashMap<>();

    public static ProductManager getInstance(){
        if (instance == null)
            instance = new ProductManager();
        return instance;
    }

    private ProductManager(){

    }

    public void addProduct(String symbol) throws DataValidationException {
        ProductBook newProductBook;
        try {
            newProductBook = new ProductBook(symbol);
        } catch (InvalidProductBookException e) {
            throw new DataValidationException("product symbol not valid: " + e.getMessage(), e);
        }
        productMap.put(symbol, newProductBook);
    }

    public ProductBook getProductBook(String symbol) throws DataValidationException {
        if (!productMap.containsKey(symbol)) throw new DataValidationException("Product does not exist");
        return productMap.get(symbol);
    }

    public String getRandomProduct() throws DataValidationException {
        if (productMap.isEmpty()) throw new DataValidationException("ProductMap is empty");
        int numProducts = productMap.size();
        Random rand = new Random();
        int randNumber = rand.nextInt(numProducts);
        Object[] keys = productMap.keySet().toArray();
        return (String) keys[randNumber];

    }

    public TradableDTO addTradable(Tradable o) throws DataValidationException, InvalidProductBookException {
        if (o == null) throw new DataValidationException("Tradable can not be null");
        String symbol = o.getProduct();
        ProductBook p = getProductBook(symbol);
        TradableDTO newDTO = p.add(o);
        UserManager.getInstance().updateTradable(o.getUser(), o.makeTradableDTO());
        return newDTO;
    }

    public TradableDTO[] addQuote(Quote q) throws DataValidationException, InvalidProductBookException {
        if (q == null) throw new DataValidationException("Quote can not be null");
        String user = q.getUser();
        ProductBook p = getProductBook(user);
        p.removeQuotesForUser(user);

        TradableDTO buyDTO = addTradable(q.getQuoteSide(BookSide.BUY));
        TradableDTO sellDTO = addTradable(q.getQuoteSide(BookSide.SELL));

        return new TradableDTO[] {buyDTO, sellDTO};
    }

    public TradableDTO cancel(TradableDTO o) throws DataValidationException {
        if (o == null) throw new DataValidationException("DTO can not be null");
        ProductBook p = getProductBook(o.product());
        TradableDTO dto;
        try {
            dto = p.cancel(o.side(), o.tradableId());
        } catch (InvalidProductBookException e) {
            System.out.println("Failure to cancel");
            return null;
        }
        return dto;
    }


    public TradableDTO[] cancelQuote(String symbol, String user) throws DataValidationException {
        if (symbol == null) throw new DataValidationException("Symbol can not be null");
        if (user == null) throw new DataValidationException("User can not be null");
        ProductBook p = getProductBook(symbol);
        TradableDTO[] newDTOArray = new TradableDTO[2];
        try {
            newDTOArray = p.removeQuotesForUser(user);
        } catch (InvalidProductBookException e) {
            System.out.println("Failed to remove quote");
        }
        return newDTOArray;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(ProductBook pb: productMap.values()){
            sb.append(pb.toString());
        }
        return sb.toString();
    }



}






