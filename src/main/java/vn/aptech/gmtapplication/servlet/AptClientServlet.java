package vn.aptech.gmtapplication.servlet;


import vn.aptech.gmtapplication.ejb.AptSessionBeanRemote;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(name = "AptClientServlet", urlPatterns = {"/apt-client", "/aptech"})
public class AptClientServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(AptClientServlet.class.getName());

    @EJB
    private AptSessionBeanRemote aptSessionBean;
    public AptClientServlet() {
        super();
        LOGGER.info("AptClientServlet created");
    }
    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.info("AptClientServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Aptech Client GET request received");

        try {
            // Get initial data from session bean
            if (aptSessionBean != null) {
                String aptechMessage = aptSessionBean.printAptechMessage();
                String serverTime = aptSessionBean.getCurrentServerTime();
                String systemInfo = aptSessionBean.getSystemInfo();

                request.setAttribute("aptechMessage", aptechMessage);
                request.setAttribute("serverTime", serverTime);
                request.setAttribute("systemInfo", systemInfo);
            }

            // Forward to JSP page
            request.getRequestDispatcher("/apt-client.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in Aptech Client GET request", e);
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Aptech Client POST request received");

        try {
            String action = request.getParameter("action");

            if ("printMessage".equals(action)) {
                handlePrintMessageAction(request, response);
            } else if ("calculateSum".equals(action)) {
                handleCalculateSumAction(request, response);
            } else if ("generateWelcome".equals(action)) {
                handleGenerateWelcomeAction(request, response);
            } else if ("getSystemInfo".equals(action)) {
                handleGetSystemInfoAction(request, response);
            } else {
                // Default action - show form
                doGet(request, response);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in Aptech Client POST request", e);
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            doGet(request, response);
        }
    }

    private void handlePrintMessageAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Print Aptech message requested");

        try {
            String message = aptSessionBean.printAptechMessage();
            request.setAttribute("result", message);
            request.setAttribute("resultType", "Aptech Message");
            request.setAttribute("success", true);

            LOGGER.log(Level.INFO, "Aptech message printed: {0}", message);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error printing Aptech message", e);
            request.setAttribute("errorMessage", "Failed to print message: " + e.getMessage());
        }

        doGet(request, response);
    }

    private void handleCalculateSumAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String num1Str = request.getParameter("number1");
        String num2Str = request.getParameter("number2");

        LOGGER.log(Level.INFO, "Calculate sum requested: {0} + {1}", new Object[]{num1Str, num2Str});

        try {
            // Validate input parameters
            if (num1Str == null || num1Str.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Please enter first number");
                doGet(request, response);
                return;
            }

            if (num2Str == null || num2Str.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Please enter second number");
                doGet(request, response);
                return;
            }

            // Parse numbers
            int num1 = Integer.parseInt(num1Str.trim());
            int num2 = Integer.parseInt(num2Str.trim());

            // Calculate sum using session bean
            int sum = aptSessionBean.calculateSum(num1, num2);

            // Set result attributes
            String result = String.format("%d + %d = %d", num1, num2, sum);
            request.setAttribute("result", result);
            request.setAttribute("resultType", "Calculation Result");
            request.setAttribute("number1", num1Str);
            request.setAttribute("number2", num2Str);
            request.setAttribute("success", true);

            LOGGER.log(Level.INFO, "Sum calculation result: {0}", result);

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid number format", e);
            request.setAttribute("errorMessage", "Please enter valid numbers");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error calculating sum", e);
            request.setAttribute("errorMessage", "Calculation failed: " + e.getMessage());
        }

        doGet(request, response);
    }
    private void handleGenerateWelcomeAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");

        LOGGER.log(Level.INFO, "Generate welcome message for user: {0}", userName);

        try {
            // Generate welcome message using session bean
            String welcomeMessage = aptSessionBean.generateWelcomeMessage(userName);

            // Set result attributes
            request.setAttribute("result", welcomeMessage);
            request.setAttribute("resultType", "Welcome Message");
            request.setAttribute("userName", userName != null ? userName : "");
            request.setAttribute("success", true);

            LOGGER.log(Level.INFO, "Welcome message generated: {0}", welcomeMessage);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error generating welcome message", e);
            request.setAttribute("errorMessage", "Failed to generate welcome message: " + e.getMessage());
        }

        doGet(request, response);
    }

    private void handleGetSystemInfoAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("System information requested");

        try {
            String systemInfo = aptSessionBean.getSystemInfo();

            request.setAttribute("result", systemInfo);
            request.setAttribute("resultType", "System Information");
            request.setAttribute("success", true);

            LOGGER.info("System information retrieved successfully");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error getting system information", e);
            request.setAttribute("errorMessage", "Failed to get system info: " + e.getMessage());
        }

        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Aptech Client Servlet for handling Aptech session bean operations";
    }

    @Override
    public void destroy() {
        LOGGER.info("AptClientServlet destroyed");
        super.destroy();
    }
}
