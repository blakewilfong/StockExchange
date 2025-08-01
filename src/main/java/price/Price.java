package price;

import exceptions.InvalidPriceException;
import java.util.Objects;


public class Price implements Comparable<Price> {
    private int value;

    public Price (int cents) {
        setPrice(cents);
    }

    private void setPrice(int cents) {
        this.value = cents;
    }

    public int getPrice() {
        return this.value;
    }

    public boolean isNegative() {
        return (getPrice() < 0);
    }

    public Price add(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        int newValue = this.getPrice() + p.getPrice();
        return new Price(newValue);
    }

    public Price subtract(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        int newValue = this.getPrice() - p.getPrice();
        return new Price(newValue);
    }

    public Price multiply(int n) {
        int newValue = n * this.getPrice();
        return new Price(newValue);
    }

    public boolean greaterOrEqual(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        return this.getPrice() >= p.getPrice();
    }

    public boolean lessOrEqual(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        return this.getPrice() <= p.getPrice();
    }

    public boolean greaterThan(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        return this.getPrice() > p.getPrice();
    }

    public boolean lessThan(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        return this.getPrice() < p.getPrice();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Price o) {
        if (o == null) return -1;
        return this.getPrice() - o.getPrice();
    }

    @Override
    public String toString(){
        int total = this.getPrice();
        String sign = "";
        if (total < 0) sign = "-";
        int dollars = Math.abs(total / 100);
        int cents = Math.abs(total % 100);
        return String.format("$%s%,d.%02d", sign, dollars, cents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
