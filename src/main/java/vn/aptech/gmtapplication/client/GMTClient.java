package vn.aptech.gmtapplication.client;

import vn.aptech.gmtapplication.ejb.GMTSessionBeanRemote;
import jakarta.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Standalone client application for GMT Session Bean
 * This class demonstrates how to access EJB from a standalone Java application
 */
public class GMTClient {

    private static final Logger LOGGER = Logger.getLogger(GMTClient.class.getName());
    private GMTSessionBeanRemote gmtSessionBean;
    private Scanner scanner;

    /**
     * Constructor
     */
    public GMTClient() {
        scanner = new Scanner(System.in);
    }

    /**
     * Initialize EJB connection
     */
    public void initializeEJB() throws NamingException {
        LOGGER.info("Initializing EJB connection...");

        // Set up JNDI properties for GlassFish
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty(Context.URL_PKG_PREFIXES,
                "com.sun.enterprise.naming");
        props.setProperty(Context.PROVIDER_URL,
                "iiop://localhost:3700");

        // Create JNDI context
        Context context = new InitialContext(props);

        // Lookup EJB
        String jndiName = "java:global/gmt-application/GMTSessionBean!com.example.ejb.GMTSessionBeanRemote";
        gmtSessionBean = (GMTSessionBeanRemote) context.lookup(jndiName);

        LOGGER.info("EJB connection initialized successfully");
    }

    /**
     * Display main menu
     */
    public void displayMenu() {
        System.out.println("\n===== GMT Client Application =====");
        System.out.println("1. Convert GMT Time");
        System.out.println("2. View Available Countries");
        System.out.println("3. Get Current Time");
        System.out.println("4. Set Current Time");
        System.out.println("5. Exit");
        System.out.print("Please select an option (1-5): ");
    }

    /**
     * Handle user menu selection
     */
    public void handleMenuSelection(int choice) {
        try {
            switch (choice) {
                case 1:
                    convertGMTTime();
                    break;
                case 2:
                    viewAvailableCountries();
                    break;
                case 3:
                    getCurrentTime();
                    break;
                case 4:
                    setCurrentTime();
                    break;
                case 5:
                    System.out.println("Thank you for using GMT Client Application!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-5.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling menu selection", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Convert GMT time
     */
    private void convertGMTTime() {
        System.out.println("\n--- GMT Time Conversion ---");

        try {
            // Display available countries
            String[] countries = gmtSessionBean.getAvailableCountries();
            System.out.println("Available countries:");
            for (int i = 0; i < countries.length; i++) {
                System.out.println((i + 1) + ". " + countries[i] + " (GMT" +
                        (gmtSessionBean.getGMTOffset(countries[i]) >= 0 ? "+" : "") +
                        gmtSessionBean.getGMTOffset(countries[i]) + ")");
            }

            // Get from country
            System.out.print("\nSelect FROM country (1-" + countries.length + "): ");
            int fromIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline

            if (fromIndex < 0 || fromIndex >= countries.length) {
                System.out.println("Invalid selection!");
                return;
            }
            String fromCountry = countries[fromIndex];

            // Get to country
            System.out.print("Select TO country (1-" + countries.length + "): ");
            int toIndex = scanner.nextInt() - 1;
            scanner.nextLine(); // consume newline

            if (toIndex < 0 || toIndex >= countries.length) {
                System.out.println("Invalid selection!");
                return;
            }
            String toCountry = countries[toIndex];

            // Get current time
            System.out.print("Enter current time (HH:MM format, e.g., 14:30): ");
            String timeStr = scanner.nextLine().trim();

            // Validate time format
            if (!timeStr.matches("\\d{1,2}:\\d{2}")) {
                System.out.println("Invalid time format! Please use HH:MM format.");
                return;
            }

            // Set current time in format expected by bean
            String currentTime = fromCountry + ": " + timeStr + " - " + fromCountry + " " + timeStr;
            gmtSessionBean.setCurrentTime(currentTime);

            // Convert time
            String result = gmtSessionBean.convertGMT(fromCountry, toCountry);

            System.out.println("\nConversion Result:");
            System.out.println(result);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error converting GMT time", e);
            System.out.println("Error during conversion: " + e.getMessage());
        }
    }

    /**
     * View available countries
     */
    private void viewAvailableCountries() {
        System.out.println("\n--- Available Countries ---");

        try {
            String[] countries = gmtSessionBean.getAvailableCountries();

            System.out.println("Supported countries with GMT offsets:");
            for (String country : countries) {
                int offset = gmtSessionBean.getGMTOffset(country);
                System.out.printf("%-15s: GMT%s%d%n", country,
                        (offset >= 0 ? "+" : ""), offset);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error viewing countries", e);
            System.out.println("Error retrieving countries: " + e.getMessage());
        }
    }

    /**
     * Get current time from session bean
     */
    private void getCurrentTime() {
        System.out.println("\n--- Current Time ---");

        try {
            String currentTime = gmtSessionBean.getCurrentTime();

            if (currentTime == null || currentTime.isEmpty()) {
                System.out.println("No current time set in session.");
            } else {
                System.out.println("Current time in session: " + currentTime);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting current time", e);
            System.out.println("Error retrieving current time: " + e.getMessage());
        }
    }

    /**
     * Set current time in session bean
     */
    private void setCurrentTime() {
        System.out.println("\n--- Set Current Time ---");

        try {
            System.out.print("Enter time string (e.g., 'Vietnam: 14:30 - China 15:30'): ");
            String timeStr = scanner.nextLine().trim();

            if (timeStr.isEmpty()) {
                System.out.println("Time string cannot be empty!");
                return;
            }

            gmtSessionBean.setCurrentTime(timeStr);
            System.out.println("Current time set successfully: " + timeStr);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting current time", e);
            System.out.println("Error setting current time: " + e.getMessage());
        }
    }

    /**
     * Run the client application
     */
    public void run() {
        try {
            // Initialize EJB connection
            initializeEJB();

            System.out.println("GMT Client Application started successfully!");
            System.out.println("Connected to EJB server.");

            // Main application loop
            while (true) {
                displayMenu();

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    handleMenuSelection(choice);
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Invalid input", e);
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.nextLine(); // clear invalid input
                }
            }

        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize EJB connection", e);
            System.err.println("Failed to connect to EJB server: " + e.getMessage());
            System.err.println("Please ensure GlassFish server is running and EJB is deployed.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in client application", e);
            System.err.println("Unexpected error: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        GMTClient client = new GMTClient();
        client.run();
    }
}