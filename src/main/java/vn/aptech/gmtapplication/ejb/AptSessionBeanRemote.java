package vn.aptech.gmtapplication.ejb;

import jakarta.ejb.Remote;

@Remote
public interface AptSessionBeanRemote {

    String printAptechMessage();

    String getCurrentServerTime();

    int calculateSum(int a, int b);

    String generateWelcomeMessage(String userName);

    String getSystemInfo();
}