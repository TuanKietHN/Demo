package vn.aptech.gmtapplication.ejb;

import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.util.logging.Level;
import vn.aptech.gmtapplication.ejb.AptSessionBeanRemote;
@Stateless(name = "AptSessionBean")
@LocalBean
public class AptSessionBean implements AptSessionBeanRemote {

    private static final Logger LOGGER = Logger.getLogger(AptSessionBean.class.getName());
    private static final String APTECH_MESSAGE = "Welcome to Aptech Computer Education!";

    /**
     * Default constructor
     */
    public AptSessionBean() {
        LOGGER.info("AptSessionBean instance created");
    }

    @Override
    public String printAptechMessage() {
        LOGGER.info("printAptechMessage() called");
        return APTECH_MESSAGE;
    }

    @Override
    public String getCurrentServerTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = now.format(formatter);

        LOGGER.log(Level.INFO, "Current server time requested: {0}", currentTime);
        return currentTime;
    }

    @Override
    public int calculateSum(int a, int b) {
        int sum = a + b;
        LOGGER.log(Level.INFO, "calculateSum({0}, {1}) = {2}", new Object[]{a, b, sum});
        return sum;
    }

    @Override
    public String generateWelcomeMessage(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            userName = "Guest";
        }

        String message = String.format("Hello %s, %s", userName.trim(), APTECH_MESSAGE);
        LOGGER.log(Level.INFO, "Welcome message generated for user: {0}", userName);
        return message;
    }

    @Override
    public String getSystemInfo() {
        StringBuilder info = new StringBuilder();
        info.append("Server Information:\n");
        info.append("Java Version: ").append(System.getProperty("java.version")).append("\n");
        info.append("Operating System: ").append(System.getProperty("os.name")).append("\n");
        info.append("Server Time: ").append(getCurrentServerTime()).append("\n");
        info.append("Available Processors: ").append(Runtime.getRuntime().availableProcessors()).append("\n");
        info.append("Total Memory: ").append(Runtime.getRuntime().totalMemory() / 1024 / 1024).append(" MB\n");
        info.append("Free Memory: ").append(Runtime.getRuntime().freeMemory() / 1024 / 1024).append(" MB");

        LOGGER.info("System information requested");
        return info.toString();
    }

    /**
     * Business method to validate user input
     * @param input Input string to validate
     * @return true if input is valid
     */
    public boolean validateInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    /**
     * Business method to format message with timestamp
     * @param message Message to format
     * @return Formatted message with timestamp
     */
    public String formatMessageWithTimestamp(String message) {
        String timestamp = getCurrentServerTime();
        return String.format("[%s] %s", timestamp, message);
    }
}
