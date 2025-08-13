package market;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentMarketPublisher {

    private static CurrentMarketPublisher instance;

    private static final HashMap<String, ArrayList<CurrentMarketObserver>> filters = new HashMap<>();

    public static CurrentMarketPublisher getInstance(){
        if(instance == null)
            instance = new CurrentMarketPublisher();
        return instance;
    }

    public void subscribeCurrentMarket(String symbol, CurrentMarketObserver cmo){
        ArrayList<CurrentMarketObserver> list;
        if (filters.containsKey(symbol)) {
            list = filters.get(symbol);
        } else {
            list = new ArrayList<>();
            filters.put(symbol, list);
        }
        list.add(cmo);
    }

    public void unSubscribeCurrentMarket(String symbol, CurrentMarketObserver cmo) {
        if (!filters.containsKey(symbol)) return;
        filters.get(symbol).remove(cmo);
    }

    public static void acceptCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide){
        if (!filters.containsKey(symbol)) return;
        ArrayList<CurrentMarketObserver> list;
        list = filters.get(symbol);
        for (CurrentMarketObserver cmo: list){
            cmo.updateCurrentMarket(symbol, buySide, sellSide);
        }
    }




}
