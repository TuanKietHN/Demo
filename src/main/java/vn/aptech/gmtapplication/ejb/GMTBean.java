package vn.aptech.gmtapplication.ejb;

import jakarta.ejb.Stateful;

@Stateful
public class GMTBean {

    public String convertGMT(String fromCountry, String toCountry) {
        int fromGMT = getGMT(fromCountry);
        int toGMT = getGMT(toCountry);

        int diff = toGMT - fromGMT;

        return "Time difference: " + diff + " hours";
    }

    private int getGMT(String country) {
        return switch (country) {
            case "USA" -> -5;
            case "Vietnam" -> 7;
            case "China" -> 8;
            default -> 0;
        };
    }
}