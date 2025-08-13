package market;

import price.Price;

public class CurrentMarketTracker {

    private static CurrentMarketTracker instance;

    public static CurrentMarketTracker getInstance(){
        if(instance == null)
            instance = new CurrentMarketTracker();
        return instance;
    }

    public void updateMarket(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume){
        int width;

        if (sellPrice == null || buyPrice == null) {
            width = 0;
        } else {
            width = sellPrice.getPrice() - buyPrice.getPrice();
        }

        CurrentMarketSide currentMarketBuySide = new CurrentMarketSide(buyPrice, buyVolume);
        CurrentMarketSide currentMarketSellSide = new CurrentMarketSide(sellPrice, sellVolume);

        String s = "*********** Current Market ***********\n" +
                "* " + symbol + "   " + buyPrice + "x" + buyVolume +
                " - " + sellPrice + "x" + sellVolume + " [" + width + "]\n" +
                "**************************************\n";
        System.out.println(s);
        CurrentMarketPublisher.acceptCurrentMarket(symbol, currentMarketBuySide, currentMarketSellSide);

    }

}





