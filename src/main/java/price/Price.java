package price;

import exceptions.InvalidPriceException;

import java.util.Objects;


/*
    Immutable. Represents a single price value stored as an integer in cents.
    Ex. $12.99 = 1299, $50.00 = 5000.
 */
public class Price implements Comparable<Price> {
    private int value;

    // Constructor
    public Price (int cents) {
        setPrice(cents);
    }

    // Sets price value
    private void setPrice(int cents) {
        this.value = cents;
    }

    // Returns price value
    public int getPrice() {
        return this.value;
    }

    // Returns true if price is negative or false if positive.
    public boolean isNegative() {
        return (getPrice() < 0);
    }

    // Adds a different price to this price and returns new price object of sum
    public Price add(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        int newValue = this.getPrice() + p.getPrice();
        return new Price(newValue);
    }

    // Subtracts a different price from this price and returns a new price object of the difference.
    public Price subtract(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        int newValue = this.getPrice() - p.getPrice();
        return new Price(newValue);
    }

    // Multiplies this price by an integer and returns a new price object with the product.
    public Price multiply(int n) {
        int newValue = n * this.getPrice();
        return new Price(newValue);
    }

    // Returns true if this price is greater or equal to another price, else false.
    public boolean greaterOrEqual(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        return this.getPrice() >= p.getPrice();
    }
    // Returns true if this price is less or equal to another price, else false.
    public boolean lessOrEqual(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        return this.getPrice() <= p.getPrice();
    }
    // Returns true if this price is greater than another price, else false.
    public boolean greaterThan(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        return this.getPrice() > p.getPrice();
    }

    // Returns true if this price is less than another price, else false.
    public boolean lessThan(Price p) throws InvalidPriceException{
        if (p == null) throw new InvalidPriceException("Price object passed in cannot be null");
        return this.getPrice() < p.getPrice();
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    // Returns a negative number if this price is less than the other price, a positive number if this price is
    // greater than the other price, and 0 if they contain the same value.
    @Override
    public int compareTo(Price o) {
        if (o == null) return -1;
        return this.getPrice() - o.getPrice();
    }

    // Returns a string representation of the price value.
    // Ex. If the value of the price is 789, it will return $7.89
    @Override
    public String toString(){
        int total = this.getPrice();
        String sign = "";
        if (total < 0) sign = "-";
        int dollars = Math.abs(total / 100);
        int cents = Math.abs(total % 100);
        return String.format("$%s%,d.%02d", sign, dollars, cents);
    }

    // Returns hashcode of the price
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
