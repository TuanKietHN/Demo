<%--
  Created by IntelliJ IDEA.
  User: Kiet
  Date: 7/26/2025
  Time: 7:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aptech Client - Computer Education Portal</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 50%, #fd9644 100%);
            min-height: 100vh;
        }

        .container {
            background-color: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.15);
            max-width: 800px;
            margin: 0 auto;
            backdrop-filter: blur(10px);
        }

        .header {
            text-align: center;
            margin-bottom: 30px;
            padding: 20px;
            background: linear-gradient(45deg, #ff6b6b, #ee5a24);
            border-radius: 10px;
            color: white;
        }

        .header h1 {
            margin: 0;
            font-size: 2.5em;
            font-weight: 300;
        }

        .header p {
            margin: 10px 0 0 0;
            font-size: 1.2em;
            opacity: 0.9;
        }

        .nav-tabs {
            display: flex;
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 5px;
            margin-bottom: 20px;
            overflow-x: auto;
        }

        .nav-tab {
            flex: 1;
            padding: 12px 20px;
            text-align: center;
            background-color: transparent;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            white-space: nowrap;
            min-width: 120px;
        }

        .nav-tab.active {
            background: linear-gradient(45deg, #ff6b6b, #ee5a24);
            color: white;
        }

        .nav-tab:hover:not(.active) {
            background-color: #e9ecef;
        }

        .tab-content {
            display: none;
            background-color: #f8f9fa;
            padding: 25px;
            border-radius: 10px;
            margin-bottom: 20px;
        }

        .tab-content.active {
            display: block;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
        }

        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e1e5e9;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }

        input:focus {
            outline: none;
            border-color: #ff6b6b;
            box-shadow: 0 0 0 3px rgba(255, 107, 107, 0.1);
        }

        .button-group {
            text-align: center;
            margin-top: 20px;
        }

        button {
            padding: 12px 25px;
            margin: 5px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            transition: all 0.3s ease;
            min-width: 140px;
        }

        .primary-btn {
            background: linear-gradient(45deg, #ff6b6b, #ee5a24);
            color: white;
        }

        .primary-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(255, 107, 107, 0.3);
        }

        .result-section {
            background: linear-gradient(45deg, #d4edda, #c3e6cb);
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 20px;
            border-radius: 8px;
            margin-top: 25px;
        }

        .error-section {
            background: linear-gradient(45deg, #f8d7da, #f5c6cb);
            border: 1px solid #f5c6cb;
            color: #721c24;
            padding: 20px;
            border-radius: 8px;
            margin-top: 25px;
        }

        .info-card {
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 15px;
            border-left: 4px solid #ff6b6b;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .info-card h4 {
            margin: 0 0 10px 0;
            color: #ff6b6b;
        }

        .system-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            font-family: 'Courier New', monospace;
            font-size: 14px;
            white-space: pre-line;
            overflow-x: auto;
        }

        .welcome-display {
            background: linear-gradient(45deg, #e3f2fd, #bbdefb);
            border: 1px solid #90caf9;
            color: #0d47a1;
            padding: 20px;
            border-radius: 8px;
            text-align: center;
            font-size: 18px;
            font-weight: 500;
        }

        @media (max-width: 768px) {
            .container {
                margin: 10px;
                padding: 20px;
            }

            .nav-tabs {
                flex-direction: column;
            }

            .nav-tab {
                margin-bottom: 5px;
            }

            .header h1 {
                font-size: 2em;
            }

            button {
                width: 100%;
                margin: 5px 0;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>🎓 Aptech Client</h1>
        <p>Computer Education Portal</p>
        <c:if test="${not empty aptechMessage}">
            <div style="margin-top: 15px; font-size: 1.1em;">
                "${aptechMessage}"
            </div>
        </c:if>
    </div>

    <!-- Navigation Tabs -->
    <div class="nav-tabs">
        <button class="nav-tab active" onclick="showTab('message-tab')">📢 Message</button>
        <button class="nav-tab" onclick="showTab('time-tab')">⏰ Server Time</button>
        <button class="nav-tab" onclick="showTab('calculator-tab')">🧮 Calculator</button>
        <button class="nav-tab" onclick="showTab('welcome-tab')">👋 Welcome</button>
        <button class="nav-tab" onclick="showTab('system-tab')">💻 System Info</button>
    </div>

    <!-- Message Tab -->
    <div id="message-tab" class="tab-content active">
        <div class="info-card">
            <h4>Print Aptech Message</h4>
            <p>Click the button below to retrieve and display the official Aptech message from the server.</p>
            <form method="post" action="${pageContext.request.contextPath}/apt-client">
                <input type="hidden" name="action" value="printMessage">
                <div class="button-group">
                    <button type="submit" class="primary-btn">📢 Print Aptech Message</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Server Time Tab -->
    <div id="time-tab" class="tab-content">
        <div class="info-card">
            <h4>Current Server Time</h4>
            <p>Get the current date and time from the server.</p>
            <form method="post" action="${pageContext.request.contextPath}/apt-client">
                <input type="hidden" name="action" value="getSystemInfo">
                <div class="button-group">
                    <button type="submit" class="primary-btn">⏰ Get Server Time</button>
                </div>
            </form>
            <c:if test="${not empty serverTime}">
                <div class="welcome-display" style="margin-top: 20px;">
                    <strong>Current Server Time:</strong><br>
                        ${serverTime}
                </div>
            </c:if>
        </div>
    </div>

    <!-- Calculator Tab -->
    <div id="calculator-tab" class="tab-content">
        <div class="info-card">
            <h4>Sum Calculator</h4>
            <p>Calculate the sum of two numbers using the server-side EJB.</p>
            <form method="post" action="${pageContext.request.contextPath}/apt-client">
                <input type="hidden" name="action" value="calculateSum">

                <div class="form-group">
                    <label for="number1">First Number:</label>
                    <input type="number" name="number1" id="number1"
                           value="${param.number1}" required
                           placeholder="Enter first number">
                </div>

                <div class="form-group">
                    <label for="number2">Second Number:</label>
                    <input type="number" name="number2" id="number2"
                           value="${param.number2}" required
                           placeholder="Enter second number">
                </div>

                <div class="button-group">
                    <button type="submit" class="primary-btn">🧮 Calculate Sum</button>
                    <button type="button" onclick="clearCalculator()"
                            style="background-color: #6c757d; color: white;">Clear</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Welcome Tab -->
    <div id="welcome-tab" class="tab-content">
        <div class="info-card">
            <h4>Welcome Message Generator</h4>
            <p>Generate a personalized welcome message.</p>
            <form method="post" action="${pageContext.request.contextPath}/apt-client">
                <input type="hidden" name="action" value="generateWelcome">

                <div class="form-group">
                    <label for="userName">Your Name:</label>
                    <input type="text" name="userName" id="userName"
                           value="${param.userName}"
                           placeholder="Enter your name (optional)">
                    <small style="color: #666;">Leave empty to use 'Guest'</small>
                </div>

                <div class="button-group">
                    <button type="submit" class="primary-btn">👋 Generate Welcome</button>
                </div>
            </form>
        </div>
    </div>

    <!-- System Info Tab -->
    <div id="system-tab" class="tab-content">
        <div class="info-card">
            <h4>System Information</h4>
            <p>Retrieve detailed system information from the server.</p>
            <form method="post" action="${pageContext.request.contextPath}/apt-client">
                <input type="hidden" name="action" value="getSystemInfo">
                <div class="button-group">
                    <button type="submit" class="primary-btn">💻 Get System Info</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Results Display -->
    <c:if test="${not empty result}">
        <div class="result-section">
            <strong>🎯 ${not empty resultType ? resultType : 'Result'}:</strong><br><br>
            <c:choose>
                <c:when test="${resultType == 'System Information'}">
                    <div class="system-info">${result}</div>
                </c:when>
                <c:when test="${resultType == 'Welcome Message'}">
                    <div class="welcome-display">${result}</div>
                </c:when>
                <c:otherwise>
                    <div style="font-size: 18px; font-weight: 500;">${result}</div>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>

    <!-- Error Display -->
    <c:if test="${not empty errorMessage}">
        <div class="error-section">
            <strong>⚠️ Error:</strong><br>
                ${errorMessage}
        </div>
    </c:if>

    <!-- Footer -->
    <div style="text-align: center; margin-top: 30px; padding: 20px; border-top: 1px solid #dee2e6;">
        <p style="color: #666; margin: 0;">
            <strong>Aptech Computer Education</strong><br>
            EJB Session Bean Demo Application<br>
            <small>Stateless Session Bean Implementation</small>
        </p>
    </div>
</div>

<script>
    function showTab(tabId) {
        // Hide all tab contents
        const tabContents = document.querySelectorAll('.tab-content');
        tabContents.forEach(content => {
            content.classList.remove('active');
        });

        // Remove active class from all nav tabs
        const navTabs = document.querySelectorAll('.nav-tab');
        navTabs.forEach(tab => {
            tab.classList.remove('active');
        });

        // Show selected tab content
        document.getElementById(tabId).classList.add('active');

        // Add active class to clicked nav tab
        event.target.classList.add('active');

        // Clear previous results when switching tabs
        const resultSection = document.querySelector('.result-section');
        const errorSection = document.querySelector('.error-section');
        if (resultSection) resultSection.style.display = 'none';
        if (errorSection) errorSection.style.display = 'none';
    }

    function clearCalculator() {
        document.getElementById('number1').value = '';
        document.getElementById('number2').value = '';
    }

    // Add loading states to buttons
    document.querySelectorAll('form').forEach(form => {
        form.addEventListener('submit', function() {
            const submitBtn = this.querySelector('button[type="submit"]');
            const originalText = submitBtn.innerHTML;
            submitBtn.innerHTML = '🔄 Processing...';
            submitBtn.disabled = true;

            // Re-enable after 3 seconds in case of issues
            setTimeout(() => {
                submitBtn.innerHTML = originalText;
                submitBtn.disabled = false;
            }, 3000);
        });
    });

    // Auto-switch to relevant tab based on results
    document.addEventListener('DOMContentLoaded', function() {
        <c:if test="${not empty param.action}">
        <c:choose>
        <c:when test="${param.action == 'printMessage'}">
        showTab('message-tab');
        document.querySelector('[onclick="showTab(\'message-tab\')"]').classList.add('active');
        </c:when>
        <c:when test="${param.action == 'calculateSum'}">
        showTab('calculator-tab');
        document.querySelector('[onclick="showTab(\'calculator-tab\')"]').classList.add('active');
        </c:when>
        <c:when test="${param.action == 'generateWelcome'}">
        showTab('welcome-tab');
        document.querySelector('[onclick="showTab(\'welcome-tab\')"]').classList.add('active');
        </c:when>
        <c:when test="${param.action == 'getSystemInfo'}">
        showTab('system-tab');
        document.querySelector('[onclick="showTab(\'system-tab\')"]').classList.add('active');
        </c:when>
        </c:choose>
        </c:if>

        // Show results with animation
        <c:if test="${success == true and not empty result}">
        const resultDiv = document.querySelector('.result-section');
        if (resultDiv) {
            resultDiv.style.opacity = '0';
            resultDiv.style.transform = 'translateY(20px)';
            setTimeout(() => {
                resultDiv.style.transition = 'all 0.5s ease';
                resultDiv.style.opacity = '1';
                resultDiv.style.transform = 'translateY(0)';
            }, 100);
        }
        </c:if>
    });

    // Add keyboard navigation for tabs
    document.addEventListener('keydown', function(e) {
        if (e.ctrlKey) {
            const tabs = ['message-tab', 'time-tab', 'calculator-tab', 'welcome-tab', 'system-tab'];
            const currentActive = document.querySelector('.tab-content.active').id;
            const currentIndex = tabs.indexOf(currentActive);

            if (e.key === 'ArrowRight' || e.key === 'ArrowDown') {
                e.preventDefault();
                const nextIndex = (currentIndex + 1) % tabs.length;
                const nextTab = tabs[nextIndex];
                showTab(nextTab);
                document.querySelector(`[onclick="showTab('${nextTab}')"]`).classList.add('active');
            } else if (e.key === 'ArrowLeft' || e.key === 'ArrowUp') {
                e.preventDefault();
                const prevIndex = (currentIndex - 1 + tabs.length) % tabs.length;
                const prevTab = tabs[prevIndex];
                showTab(prevTab);
                document.querySelector(`[onclick="showTab('${prevTab}')"]`).classList.add('active');
            }
        }
    });
</script>
</body>
</html>