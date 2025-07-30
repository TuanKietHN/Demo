package vn.aptech.gmtapplication.ejb;

import jakarta.ejb.Remote;


@Remote
public interface GMTSessionBeanRemote {

    String convertGMT(String fromCountry, String toCountry);


    void setCurrentTime(String currentTime);

    String getCurrentTime();

    int getGMTOffset(String country);

    String[] getAvailableCountries();
}
