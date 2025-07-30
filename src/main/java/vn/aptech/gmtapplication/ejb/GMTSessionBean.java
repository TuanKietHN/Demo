package vn.aptech.gmtapplication.ejb;


import jakarta.ejb.Stateful;
import jakarta.ejb.LocalBean;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;


@Stateful(name = "GMTSessionBean")
@LocalBean
public class GMTSessionBean implements GMTSessionBeanRemote, Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(GMTSessionBean.class.getName());

    // Client-specific state
    private String currentTime;

    // Static GMT offset data
    private static final Map<String, Integer> GMT_OFFSET = new HashMap<>();
    private static final String[] COUNTRIES = {"China", "Vietnam", "U.K", "U.S.A"};

    static {
        GMT_OFFSET.put("China", 8);      // GMT+8 (Beijing)
        GMT_OFFSET.put("Vietnam", 7);    // GMT+7 (Hanoi)
        GMT_OFFSET.put("U.K", 0);        // GMT+0 (London)
        GMT_OFFSET.put("U.S.A", -5);     // GMT-5 (Washington D.C)
    }

    public GMTSessionBean() {
        LOGGER.info("GMTSessionBean instance created");
    }

    @Override
    public String convertGMT(String fromCountry, String toCountry) {
        try {
            LOGGER.log(Level.INFO, "Converting GMT from {0} to {1}", new Object[]{fromCountry, toCountry});

            // Validate input countries
            Integer fromOffset = GMT_OFFSET.get(fromCountry);
            Integer toOffset = GMT_OFFSET.get(toCountry);

            if (fromOffset == null || toOffset == null) {
                LOGGER.log(Level.WARNING, "Invalid country selection: {0} or {1}", new Object[]{fromCountry, toCountry});
                return "Error: Invalid country selection. Available countries: China, Vietnam, U.K, U.S.A";
            }

            if (currentTime == null || currentTime.trim().isEmpty()) {
                LOGGER.warning("Current time not set");
                return "Error: Please set current time first";
            }

            // Parse current time from format: "Country: HH:MM - Country HH:MM"
            String[] parts = currentTime.split(" - ");
            if (parts.length < 1) {
                LOGGER.warning("Invalid time format");
                return "Error: Invalid time format. Expected format: 'Country: HH:MM - Country HH:MM'";
            }

            // Extract time from first part (current time)
            String timeStr;
            if (parts[0].contains(":")) {
                String[] timeParts = parts[0].split(":");
                if (timeParts.length >= 2) {
                    // Extract just the time part (last two elements)
                    int len = timeParts.length;
                    timeStr = timeParts[len-2] + ":" + timeParts[len-1].trim();
                } else {
                    timeStr = parts[0].trim();
                }
            } else {
                LOGGER.warning("Time format does not contain colon separator");
                return "Error: Invalid time format. Expected format with colon separator";
            }

            // Parse hour and minute
            String[] timeComponents = timeStr.split(":");
            if (timeComponents.length != 2) {
                LOGGER.warning("Invalid time components");
                return "Error: Invalid time format. Expected HH:MM";
            }

            int hour, minute;
            try {
                hour = Integer.parseInt(timeComponents[0].trim());
                minute = Integer.parseInt(timeComponents[1].trim());
            } catch (NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Error parsing time components: {0}", e.getMessage());
                return "Error: Invalid time format. Hour and minute must be numbers";
            }

            // Validate time ranges
            if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                return "Error: Invalid time. Hour must be 0-23, minute must be 0-59";
            }

            // Calculate time difference
            int timeDifference = toOffset - fromOffset;

            // Convert time
            int newHour = hour + timeDifference;

            // Handle day change
            String dayIndicator = "";
            if (newHour < 0) {
                newHour += 24;
                dayIndicator = " (previous day)";
            } else if (newHour >= 24) {
                newHour -= 24;
                dayIndicator = " (next day)";
            }

            // Format result
            String result = String.format("%s: %02d:%02d - %s %02d:%02d%s",
                    fromCountry, hour, minute, toCountry, newHour, minute, dayIndicator);

            // Update current time with result
            setCurrentTime(result);

            LOGGER.log(Level.INFO, "GMT conversion successful: {0}", result);
            return result;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error converting GMT time", e);
            return "Error converting time: " + e.getMessage();
        }
    }

    @Override
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
        LOGGER.log(Level.INFO, "Current time set to: {0}", currentTime);
    }

    @Override
    public String getCurrentTime() {
        return currentTime != null ? currentTime : "";
    }

    @Override
    public int getGMTOffset(String country) {
        Integer offset = GMT_OFFSET.get(country);
        return offset != null ? offset : 0;
    }

    @Override
    public String[] getAvailableCountries() {
        return COUNTRIES.clone();
    }


    public Map<String, Integer> getAllGMTOffsets() {
        return new HashMap<>(GMT_OFFSET);
    }

    public boolean isValidCountry(String country) {
        return GMT_OFFSET.containsKey(country);
    }

    public String formatTime(int hour, int minute) {
        return String.format("%02d:%02d", hour, minute);
    }
}
