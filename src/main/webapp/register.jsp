<%-- 
    Document   : register.jsp
    Created on : Sep 28, 2014, 6:29:51 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
    </head>
    <body>
        <header>
        <h1>InstaGrim</h1>
        <h2>Your world in Black and White</h2>
        </header>
        <nav>
        </nav>
       
        <article>
            <h3>Register as user</h3>
            <form method="POST" action="Register">
                <ul>
                    <li>User Name <input type="text" name="username"><font color="red">${UsernameCheck}</font></li> 
                    <li>Password <input type="password" name="password"><font color="red">${PasswordCheck}</font></li> 
                    <li>Confirm Password <input type="password" name="passwordCheck"></li>
                    <li>Email Address <input type="email" name="email"></li>
                </ul>
                <br/>
                <input type="submit" value="Register"> 
                <%
                session.setAttribute("UsernameCheck", null); 
                session.setAttribute("PasswordCheck", null);
                %>
                </form>

        </article>
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
            </ul>
        </footer>
    </body>
</html>
