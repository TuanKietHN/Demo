package vn.aptech.gmtapplication.client;

import vn.aptech.gmtapplication.ejb.AptSessionBeanRemote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Standalone client application for Aptech Session Bean (Question 2)
 * This class demonstrates how to access stateless EJB from a standalone Java application
 */
public class AptClient {

    private static final Logger LOGGER = Logger.getLogger(AptClient.class.getName());
    private AptSessionBeanRemote aptSessionBean;
    private Scanner scanner;

    /**
     * Constructor
     */
    public AptClient() {
        scanner = new Scanner(System.in);
    }

    /**
     * Initialize EJB connection
     */
    public void initializeEJB() throws NamingException {
        LOGGER.info("Initializing Aptech EJB connection...");

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
        String jndiName = "java:global/gmt-application/AptSessionBean!com.example.ejb.AptSessionBeanRemote";
        aptSessionBean = (AptSessionBeanRemote) context.lookup(jndiName);

        LOGGER.info("Aptech EJB connection initialized successfully");
    }

    /**
     * Display main menu
     */
    public void displayMenu() {
        System.out.println("\n===== Aptech Client Application =====");
        System.out.println("1. Print Aptech Message");
        System.out.println("2. Get Current Server Time");
        System.out.println("3. Calculate Sum of Two Numbers");
        System.out.println("4. Generate Welcome Message");
        System.out.println("5. Get System Information");
        System.out.println("6. Exit");
        System.out.print("Please select an option (1-6): ");
    }

    /**
     * Handle user menu selection
     */
    public void handleMenuSelection(int choice) {
        try {
            switch (choice) {
                case 1:
                    printAptechMessage();
                    break;
                case 2:
                    getCurrentServerTime();
                    break;
                case 3:
                    calculateSum();
                    break;
                case 4:
                    generateWelcomeMessage();
                    break;
                case 5:
                    getSystemInformation();
                    break;
                case 6:
                    System.out.println("Thank you for using Aptech Client Application!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling menu selection", e);
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Print Aptech message
     */
    private void printAptechMessage() {
        System.out.println("\n--- Aptech Message ---");

        try {
            String message = aptSessionBean.printAptechMessage();
            System.out.println("Message: " + message);

            LOGGER.log(Level.INFO, "Aptech message printed: {0}", message);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error printing Aptech message", e);
            System.out.println("Error printing message: " + e.getMessage());
        }
    }

    /**
     * Get current server time
     */
    private void getCurrentServerTime() {
        System.out.println("\n--- Current Server Time ---");

        try {
            String serverTime = aptSessionBean.getCurrentServerTime();
            System.out.println("Server Time: " + serverTime);

            LOGGER.log(Level.INFO, "Server time retrieved: {0}", serverTime);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting server time", e);
            System.out.println("Error getting server time: " + e.getMessage());
        }
    }

    /**
     * Calculate sum of two numbers
     */
    private void calculateSum() {
        System.out.println("\n--- Calculate Sum ---");

        try {
            System.out.print("Enter first number: ");
            int num1 = scanner.nextInt();

            System.out.print("Enter second number: ");
            int num2 = scanner.nextInt();
            scanner.nextLine(); // consume newline

            int sum = aptSessionBean.calculateSum(num1, num2);

            System.out.println("Result: " + num1 + " + " + num2 + " = " + sum);

            LOGGER.log(Level.INFO, "Sum calculated: {0} + {1} = {2}", new Object[]{num1, num2, sum});

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error calculating sum", e);
            System.out.println("Error calculating sum: " + e.getMessage());
            scanner.nextLine(); // clear invalid input
        }
    }

    /**
     * Generate welcome message
     */
    private void generateWelcomeMessage() {
        System.out.println("\n--- Generate Welcome Message ---");

        try {
            System.out.print("Enter your name (or press Enter for Guest): ");
            String userName = scanner.nextLine().trim();

            String welcomeMessage = aptSessionBean.generateWelcomeMessage(userName);
            System.out.println("Welcome Message: " + welcomeMessage);

            LOGGER.log(Level.INFO, "Welcome message generated for: {0}",
                    userName.isEmpty() ? "Guest" : userName);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating welcome message", e);
            System.out.println("Error generating welcome message: " + e.getMessage());
        }
    }

    /**
     * Get system information
     */
    private void getSystemInformation() {
        System.out.println("\n--- System Information ---");

        try {
            String systemInfo = aptSessionBean.getSystemInfo();
            System.out.println(systemInfo);

            LOGGER.info("System information retrieved successfully");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting system information", e);
            System.out.println("Error getting system information: " + e.getMessage());
        }
    }

    /**
     * Display application header
     */
    private void displayHeader() {
        System.out.println("========================================");
        System.out.println("    APTECH COMPUTER EDUCATION");
        System.out.println("      EJB Client Application");
        System.out.println("========================================");
    }

    /**
     * Run the client application
     */
    public void run() {
        try {
            // Display header
            displayHeader();

            // Initialize EJB connection
            System.out.println("Connecting to Aptech EJB server...");
            initializeEJB();

            System.out.println("Aptech Client Application started successfully!");
            System.out.println("Connected to EJB server.");

            // Print initial Aptech message
            try {
                String initialMessage = aptSessionBean.printAptechMessage();
                System.out.println("\n" + initialMessage);
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Could not get initial message", e);
            }

            // Main application loop
            while (true) {
                displayMenu();

                try {
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    handleMenuSelection(choice);

                    // Pause before showing menu again
                    System.out.println("\nPress Enter to continue...");
                    scanner.nextLine();

                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Invalid input", e);
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.nextLine(); // clear invalid input
                }
            }

        } catch (NamingException e) {
            LOGGER.log(Level.SEVERE, "Failed to initialize Aptech EJB connection", e);
            System.err.println("Failed to connect to Aptech EJB server: " + e.getMessage());
            System.err.println("Please ensure GlassFish server is running and EJB is deployed.");
            System.err.println("\nTroubleshooting steps:");
            System.err.println("1. Check if GlassFish server is running on localhost:3700");
            System.err.println("2. Verify that the application is deployed correctly");
            System.err.println("3. Check server logs for any deployment errors");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unexpected error in Aptech client application", e);
            System.err.println("Unexpected error: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Test all EJB methods (for development/testing purposes)
     */
    public void testAllMethods() {
        System.out.println("\n--- Testing All EJB Methods ---");

        try {
            // Test print message
            System.out.println("1. Testing printAptechMessage():");
            String message = aptSessionBean.printAptechMessage();
            System.out.println("   Result: " + message);

            // Test current time
            System.out.println("\n2. Testing getCurrentServerTime():");
            String time = aptSessionBean.getCurrentServerTime();
            System.out.println("   Result: " + time);

            // Test calculate sum
            System.out.println("\n3. Testing calculateSum(10, 20):");
            int sum = aptSessionBean.calculateSum(10, 20);
            System.out.println("   Result: " + sum);

            // Test welcome message
            System.out.println("\n4. Testing generateWelcomeMessage('Test User'):");
            String welcome = aptSessionBean.generateWelcomeMessage("Test User");
            System.out.println("   Result: " + welcome);

            // Test system info
            System.out.println("\n5. Testing getSystemInfo():");
            String sysInfo = aptSessionBean.getSystemInfo();
            System.out.println("   Result:\n" + sysInfo);

            System.out.println("\nAll methods tested successfully!");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during method testing", e);
            System.out.println("Error during testing: " + e.getMessage());
        }
    }

    /**
     * Main method
     */
    public static void main(String[] args) {
        AptClient client = new AptClient();

        // Check for test argument
        if (args.length > 0 && "test".equalsIgnoreCase(args[0])) {
            try {
                client.initializeEJB();
                client.testAllMethods();
            } catch (Exception e) {
                System.err.println("Test failed: " + e.getMessage());
            }
        } else {
            client.run();
        }
    }
}
