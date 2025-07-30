package vn.aptech.gmtapplication.servlet;

import vn.aptech.gmtapplication.ejb.GMTSessionBeanRemote;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(name = "GMTClientServlet", urlPatterns = {"/gmt-client", "/gmt"})
public class GMTClientServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(GMTClientServlet.class.getName());

    @EJB
    private GMTSessionBeanRemote gmtSessionBean;

    public GMTClientServlet() {
        super();
        LOGGER.info("GMTClientServlet created");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        LOGGER.info("GMTClientServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("GMT Client GET request received");

        try {
            // Set available countries for the form
            if (gmtSessionBean != null) {
                String[] countries = gmtSessionBean.getAvailableCountries();
                request.setAttribute("countries", countries);

                // Get current time from session bean if exists
                String currentTime = gmtSessionBean.getCurrentTime();
                if (currentTime != null && !currentTime.isEmpty()) {
                    request.setAttribute("currentTime", currentTime);
                }
            }

            // Forward to JSP page
            request.getRequestDispatcher("/gmt-client.jsp").forward(request, response);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in GMT Client GET request", e);
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("GMT Client POST request received");

        try {
            String action = request.getParameter("action");

            if ("convert".equals(action)) {
                handleConvertAction(request, response);
            } else if ("reset".equals(action)) {
                handleResetAction(request, response);
            } else {
                // Default action - show form
                doGet(request, response);
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in GMT Client POST request", e);
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    /**
     * Handle convert action
     */
    private void handleConvertAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fromCountry = request.getParameter("fromCountry");
        String toCountry = request.getParameter("toCountry");
        String currentTime = request.getParameter("currentTime");

        LOGGER.log(Level.INFO, "Converting GMT: {0} -> {1}, Time: {2}",
                new Object[]{fromCountry, toCountry, currentTime});

        // Validate input parameters
        if (fromCountry == null || fromCountry.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please select 'From Country'");
            doGet(request, response);
            return;
        }

        if (toCountry == null || toCountry.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please select 'To Country'");
            doGet(request, response);
            return;
        }

        if (currentTime == null || currentTime.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please enter current time");
            doGet(request, response);
            return;
        }

        if (fromCountry.equals(toCountry)) {
            request.setAttribute("errorMessage", "From Country and To Country cannot be the same");
            doGet(request, response);
            return;
        }

        try {
            // Set current time in session bean
            gmtSessionBean.setCurrentTime(currentTime.trim());

            // Convert time using session bean
            String result = gmtSessionBean.convertGMT(fromCountry, toCountry);

            // Set attributes for JSP display
            request.setAttribute("result", result);
            request.setAttribute("fromCountry", fromCountry);
            request.setAttribute("toCountry", toCountry);
            request.setAttribute("inputTime", currentTime);
            request.setAttribute("success", true);

            LOGGER.log(Level.INFO, "GMT conversion result: {0}", result);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during GMT conversion", e);
            request.setAttribute("errorMessage", "Conversion failed: " + e.getMessage());
        }

        // Forward to JSP page with results
        doGet(request, response);
    }

    /**
     * Handle reset action
     */
    private void handleResetAction(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        LOGGER.info("Resetting GMT client");

        try {
            // Clear session bean state
            if (gmtSessionBean != null) {
                gmtSessionBean.setCurrentTime("");
            }

            // Clear request attributes
            request.removeAttribute("result");
            request.removeAttribute("fromCountry");
            request.removeAttribute("toCountry");
            request.removeAttribute("inputTime");
            request.removeAttribute("errorMessage");

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error during reset", e);
        }

        // Redirect to clean GET request
        response.sendRedirect(request.getContextPath() + "/gmt-client");
    }

    @Override
    public String getServletInfo() {
        return "GMT Client Servlet for handling GMT time conversion requests";
    }

    @Override
    public void destroy() {
        LOGGER.info("GMTClientServlet destroyed");
        super.destroy();
    }
}
