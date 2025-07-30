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
    <title>GMT Client - Time Zone Converter</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
        }

        .container {
            background-color: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 8px 32px rgba(0,0,0,0.1);
            max-width: 600px;
            margin: 0 auto;
            backdrop-filter: blur(10px);
        }

        .header {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }

        .header h1 {
            margin: 0;
            color: #667eea;
            font-size: 2.5em;
            font-weight: 300;
        }

        .header p {
            margin: 10px 0 0 0;
            color: #666;
            font-size: 1.1em;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
            font-size: 14px;
        }

        select, input[type="text"] {
            width: 100%;
            padding: 12px 15px;
            border: 2px solid #e1e5e9;
            border-radius: 8px;
            font-size: 16px;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }

        select:focus, input[type="text"]:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        .button-group {
            text-align: center;
            margin-top: 30px;
            gap: 15px;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
        }

        button {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            transition: all 0.3s ease;
            min-width: 120px;
        }

        .convert-btn {
            background: linear-gradient(45deg, #667eea, #764ba2);
            color: white;
        }

        .convert-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(102, 126, 234, 0.3);
        }

        .reset-btn {
            background-color: #6c757d;
            color: white;
        }

        .reset-btn:hover {
            background-color: #5a6268;
            transform: translateY(-2px);
        }

        .close-btn {
            background-color: #dc3545;
            color: white;
        }

        .close-btn:hover {
            background-color: #c82333;
            transform: translateY(-2px);
        }

        .result {
            background: linear-gradient(45deg, #d4edda, #c3e6cb);
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 20px;
            border-radius: 8px;
            margin-top: 25px;
            font-size: 16px;
            font-weight: 500;
        }

        .error {
            background: linear-gradient(45deg, #f8d7da, #f5c6cb);
            border: 1px solid #f5c6cb;
            color: #721c24;
            padding: 20px;
            border-radius: 8px;
            margin-top: 25px;
            font-size: 16px;
        }

        .country-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-size: 14px;
            color: #495057;
        }

        .country-info h4 {
            margin: 0 0 10px 0;
            color: #333;
        }

        .country-list {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 10px;
        }

        .country-item {
            background-color: white;
            padding: 10px;
            border-radius: 5px;
            border-left: 4px solid #667eea;
        }

        .example {
            background-color: #e3f2fd;
            border: 1px solid #bbdefb;
            border-radius: 5px;
            padding: 10px;
            margin-top: 5px;
            font-size: 12px;
            color: #1565c0;
        }

        @media (max-width: 768px) {
            .container {
                margin: 10px;
                padding: 20px;
            }

            .header h1 {
                font-size: 2em;
            }

            .button-group {
                flex-direction: column;
                align-items: center;
            }

            button {
                width: 100%;
                max-width: 200px;
                margin-bottom: 10px;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>GMT Client</h1>
        <p>Global Time Zone Converter</p>
    </div>

    <!-- Country Information -->
    <div class="country-info">
        <h4>Supported Countries & Time Zones</h4>
        <div class="country-list">
            <div class="country-item">
                <strong>China</strong><br>GMT+8 (Beijing)
            </div>
            <div class="country-item">
                <strong>Vietnam</strong><br>GMT+7 (Hanoi)
            </div>
            <div class="country-item">
                <strong>U.K</strong><br>GMT+0 (London)
            </div>
            <div class="country-item">
                <strong>U.S.A</strong><br>GMT-5 (Washington D.C)
            </div>
        </div>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/gmt-client" id="gmtForm">
        <input type="hidden" name="action" value="convert">

        <div class="form-group">
            <label for="fromCountry">From Country:</label>
            <select name="fromCountry" id="fromCountry" required>
                <option value="">Select Source Country</option>
                <option value="China" ${param.fromCountry == 'China' ? 'selected' : ''}>China (GMT+8)</option>
                <option value="Vietnam" ${param.fromCountry == 'Vietnam' ? 'selected' : ''}>Vietnam (GMT+7)</option>
                <option value="U.K" ${param.fromCountry == 'U.K' ? 'selected' : ''}>U.K (GMT+0)</option>
                <option value="U.S.A" ${param.fromCountry == 'U.S.A' ? 'selected' : ''}>U.S.A (GMT-5)</option>
            </select>
        </div>

        <div class="form-group">
            <label for="toCountry">To Country:</label>
            <select name="toCountry" id="toCountry" required>
                <option value="">Select Target Country</option>
                <option value="China" ${param.toCountry == 'China' ? 'selected' : ''}>China (GMT+8)</option>
                <option value="Vietnam" ${param.toCountry == 'Vietnam' ? 'selected' : ''}>Vietnam (GMT+7)</option>
                <option value="U.K" ${param.toCountry == 'U.K' ? 'selected' : ''}>U.K (GMT+0)</option>
                <option value="U.S.A" ${param.toCountry == 'U.S.A' ? 'selected' : ''}>U.S.A (GMT-5)</option>
            </select>
        </div>

        <div class="form-group">
            <label for="currentTime">Current Time:</label>
            <input type="text" name="currentTime" id="currentTime"
                   placeholder="e.g., Vietnam: 10:35 - China 4:35"
                   value="${param.currentTime}" required>
            <div class="example">
                <strong>Format examples:</strong><br>
                • Vietnam: 14:30 - China 15:30<br>
                • China: 09:15 - U.K 01:15<br>
                • U.S.A: 08:00 - Vietnam 20:00
            </div>
        </div>

        <div class="button-group">
            <button type="submit" class="convert-btn" onclick="return validateForm()">
                🌍 Convert Time
            </button>
            <button type="button" class="reset-btn" onclick="resetForm()">
                🔄 Reset
            </button>
            <button type="button" class="close-btn" onclick="closeApplication()">
                ❌ Close
            </button>
        </div>
    </form>

    <!-- Display Results -->
    <c:if test="${not empty result}">
        <div class="result">
            <strong>🎯 Conversion Result:</strong><br>
                ${result}
            <c:if test="${not empty inputTime}">
                <br><small><strong>Original Input:</strong> ${inputTime}</small>
            </c:if>
        </div>
    </c:if>

    <!-- Display Error Messages -->
    <c:if test="${not empty errorMessage}">
        <div class="error">
            <strong>⚠️ Error:</strong><br>
                ${errorMessage}
        </div>
    </c:if>

    <!-- Success Message -->
    <c:if test="${success == true and not empty result}">
        <script>
            // Add subtle success animation
            document.addEventListener('DOMContentLoaded', function() {
                const resultDiv = document.querySelector('.result');
                if (resultDiv) {
                    resultDiv.style.opacity = '0';
                    resultDiv.style.transform = 'translateY(20px)';
                    setTimeout(() => {
                        resultDiv.style.transition = 'all 0.5s ease';
                        resultDiv.style.opacity = '1';
                        resultDiv.style.transform = 'translateY(0)';
                    }, 100);
                }
            });
        </script>
    </c:if>
</div>

<script>
    function validateForm() {
        const fromCountry = document.getElementById('fromCountry').value;
        const toCountry = document.getElementById('toCountry').value;
        const currentTime = document.getElementById('currentTime').value.trim();

        if (!fromCountry) {
            alert('Please select a source country.');
            return false;
        }

        if (!toCountry) {
            alert('Please select a target country.');
            return false;
        }

        if (fromCountry === toCountry) {
            alert('Source and target countries cannot be the same.');
            return false;
        }

        if (!currentTime) {
            alert('Please enter the current time.');
            return false;
        }

        // Basic time format validation
        if (!currentTime.includes(':')) {
            alert('Please enter time in the correct format (e.g., "Vietnam: 10:35 - China 4:35")');
            return false;
        }

        return true;
    }

    function resetForm() {
        if (confirm('Are you sure you want to reset the form?')) {
            document.getElementById('gmtForm').reset();
            // Also redirect to clear any results
            window.location.href = '${pageContext.request.contextPath}/gmt-client';
        }
    }

    function closeApplication() {
        if (confirm('Are you sure you want to close the application?')) {
            window.close();
            // If window.close() doesn't work (most modern browsers), redirect to home
            setTimeout(() => {
                window.location.href = '${pageContext.request.contextPath}/';
            }, 100);
        }
    }

    // Auto-generate time format when countries are selected
    document.getElementById('fromCountry').addEventListener('change', updateTimeFormat);
    document.getElementById('toCountry').addEventListener('change', updateTimeFormat);

    function updateTimeFormat() {
        const fromCountry = document.getElementById('fromCountry').value;
        const toCountry = document.getElementById('toCountry').value;
        const currentTimeInput = document.getElementById('currentTime');

        if (fromCountry && !currentTimeInput.value) {
            // Generate example format based on selected countries
            const currentTime = new Date();
            const hours = String(currentTime.getHours()).padStart(2, '0');
            const minutes = String(currentTime.getMinutes()).padStart(2, '0');

            currentTimeInput.placeholder = `${fromCountry}: ${hours}:${minutes} - ${toCountry || 'Country'} XX:XX`;
        }
    }

    // Add loading animation for form submission
    document.getElementById('gmtForm').addEventListener('submit', function() {
        const submitBtn = document.querySelector('.convert-btn');
        submitBtn.innerHTML = '🔄 Converting...';
        submitBtn.disabled = true;
    });
</script>
</body>
</html>
