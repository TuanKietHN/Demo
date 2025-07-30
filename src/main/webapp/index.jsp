<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GMT Application - Java EE 10 Session Bean Demo</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #ff6b6b 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }

        .container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            text-align: center;
            max-width: 600px;
            width: 100%;
        }

        .header {
            margin-bottom: 40px;
        }

        .header h1 {
            color: #333;
            font-size: 3em;
            font-weight: 300;
            margin-bottom: 10px;
            background: linear-gradient(45deg, #667eea, #764ba2);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }

        .header p {
            color: #666;
            font-size: 1.2em;
            margin-bottom: 10px;
        }

        .subtitle {
            color: #888;
            font-size: 1em;
            font-style: italic;
        }

        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin: 40px 0;
        }

        .feature-card {
            background: white;
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.08);
            transition: all 0.3s ease;
            border-left: 4px solid transparent;
        }

        .feature-card:nth-child(1) {
            border-left-color: #667eea;
        }

        .feature-card:nth-child(2) {
            border-left-color: #ff6b6b;
        }

        .feature-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.15);
        }

        .feature-icon {
            font-size: 3em;
            margin-bottom: 15px;
        }

        .feature-card h3 {
            color: #333;
            margin-bottom: 10px;
            font-size: 1.4em;
        }

        .feature-card p {
            color: #666;
            line-height: 1.6;
        }

        .buttons {
            display: flex;
            gap: 20px;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 40px;
        }

        .btn {
            padding: 15px 30px;
            text-decoration: none;
            border-radius: 50px;
            font-weight: 600;
            font-size: 16px;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 10px;
            min-width: 180px;
            justify-content: center;
        }

        .btn-primary {
            background: linear-gradient(45deg, #667eea, #764ba2);
            color: white;
        }

        .btn-secondary {
            background: linear-gradient(45deg, #ff6b6b, #ee5a24);
            color: white;
        }

        .btn:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 25px rgba(0,0,0,0.2);
        }

        .tech-info {
            background: rgba(255, 255, 255, 0.7);
            border-radius: 10px;
            padding: 20px;
            margin-top: 30px;
            border: 1px solid rgba(255, 255, 255, 0.3);
        }

        .tech-info h4 {
            color: #333;
            margin-bottom: 15px;
            font-size: 1.2em;
        }

        .tech-stack {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 15px;
        }

        .tech-badge {
            background: linear-gradient(45deg, #f8f9fa, #e9ecef);
            color: #495057;
            padding: 8px 16px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 500;
            border: 1px solid #dee2e6;
        }

        .footer {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid rgba(0,0,0,0.1);
            color: #666;
            font-size: 14px;
        }

        @media (max-width: 768px) {
            .container {
                padding: 30px 20px;
            }

            .header h1 {
                font-size: 2.5em;
            }

            .buttons {
                flex-direction: column;
                align-items: center;
            }

            .btn {
                width: 100%;
                max-width: 300px;
            }
        }

        /* Animation for page load */
        .container {
            animation: fadeInUp 0.8s ease-out;
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Floating animation for feature cards */
        .feature-card {
            animation: float 6s ease-in-out infinite;
        }

        .feature-card:nth-child(2) {
            animation-delay: -3s;
        }

        @keyframes float {
            0%, 100% {
                transform: translateY(0px);
            }
            50% {
                transform: translateY(-10px);
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>🌍 GMT Application</h1>
        <p>Java EE 10 Session Bean Demonstration</p>
        <p class="subtitle">IntelliJ Ultimate • GlassFish 7 • JDK 17</p>
    </div>

    <div class="features">
        <div class="feature-card">
            <div class="feature-icon">⏰</div>
            <h3>GMT Converter</h3>
            <p>Convert time zones between different countries using a <strong>Stateful Session Bean</strong>. Supports China, Vietnam, U.K, and U.S.A time zones.</p>
        </div>

        <div class="feature-card">
            <div class="feature-icon">🎓</div>
            <h3>Aptech Client</h3>
            <p>Interactive client for Aptech Computer Education using a <strong>Stateless Session Bean</strong>. Includes calculator, welcome messages, and system info.</p>
        </div>
    </div>

    <div class="buttons">
        <a href="${pageContext.request.contextPath}/gmt-client" class="btn btn-primary">
            🌍 GMT Time Converter
        </a>
        <a href="${pageContext.request.contextPath}/apt-client" class="btn btn-secondary">
            🎓 Aptech Client
        </a>
    </div>

    <div class="tech-info">
        <h4>🛠️ Technology Stack</h4>
        <div class="tech-stack">
            <span class="tech-badge">Java EE 10</span>
            <span class="tech-badge">Jakarta EJB 4.0</span>
            <span class="tech-badge">GlassFish 7</span>
            <span class="tech-badge">JDK 17</span>
            <span class="tech-badge">Maven</span>
            <span class="tech-badge">MySQL 8.4</span>
            <span class="tech-badge">JSP & Servlets</span>
        </div>
    </div>

    <div class="footer">
        <p><strong>Assignment Implementation:</strong> Java EE 10 with Stateful & Stateless Session Beans</p>
        <p><small>
            Question 1: GMT Time Conversion (Stateful) •
            Question 2: Aptech Client (Stateless)
        </small></p>
    </div>
</div>

<script>
    // Add some interactive effects
    document.addEventListener('DOMContentLoaded', function() {
        // Add hover effect to buttons
        const buttons = document.querySelectorAll('.btn');
        buttons.forEach(btn => {
            btn.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-3px) scale(1.05)';
            });

            btn.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0) scale(1)';
            });
        });

        // Add click animation
        buttons.forEach(btn => {
            btn.addEventListener('click', function(e) {
                this.style.transform = 'translateY(0) scale(0.95)';
                setTimeout(() => {
                    this.style.transform = 'translateY(-3px) scale(1.05)';
                }, 150);
            });
        });

        // Parallax effect on scroll (if page becomes scrollable)
        window.addEventListener('scroll', function() {
            const scrolled = window.pageYOffset;
            const header = document.querySelector('.header');
            if (header) {
                header.style.transform = `translateY(${scrolled * 0.1}px)`;
            }
        });
    });

    // Add random color variations to tech badges
    document.querySelectorAll('.tech-badge').forEach((badge, index) => {
        const colors = [
            'linear-gradient(45deg, #667eea, #764ba2)',
            'linear-gradient(45deg, #ff6b6b, #ee5a24)',
            'linear-gradient(45deg, #4ecdc4, #44a08d)',
            'linear-gradient(45deg, #a8edea, #fed6e3)',
            'linear-gradient(45deg, #ff9a9e, #fecfef)',
            'linear-gradient(45deg, #a18cd1, #fbc2eb)',
            'linear-gradient(45deg, #ffecd2, #fcb69f)'
        ];

        setTimeout(() => {
            badge.style.background = colors[index % colors.length];
            badge.style.color = 'white';
            badge.style.borderColor = 'transparent';
        }, index * 200);
    });
</script>
</body>
</html>