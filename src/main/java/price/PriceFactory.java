package price;
import exceptions.InvalidPriceException;

public abstract class PriceFactory {

    public static Price makePrice(int value) {
        return new Price(value);
    }

    public static Price makePrice(String strValue) throws InvalidPriceException {
        int intValue;

        if (strValue.contains("$")) strValue = strValue.replace("$", "");
        if (strValue.contains(",")) strValue = strValue.replaceAll(",", "");
        //System.out.println(strValue);

        if (strValue.contains(".")) {

            if (strValue.indexOf('.') != strValue.lastIndexOf('.')) throw new InvalidPriceException("Multiple decimal points detected");
            String[] parts = strValue.split("\\.");

            if (parts.length == 2) {

                int decimalPlaces = parts[1].length();
                if (decimalPlaces == 1 || decimalPlaces > 2)
                    throw new InvalidPriceException("Invalid number of cents.");
                strValue = strValue.replace(".", "");
                intValue = Integer.parseInt(strValue);
            }
            else {

                intValue = Integer.parseInt(parts[0]) * 100;
            }
        }
        else {
            intValue = Integer.parseInt(strValue) * 100;}

        return new Price(intValue);
    }
}