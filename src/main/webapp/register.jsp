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
    <nav>
            <jsp:include page="nav.jsp"></jsp:include>   
    </nav>
    <body>
        <header>
        <h1>I n s t a G r i m</h1>
        <h2>Register</h2>
        </header>
        
       
        <article>
            <form method="POST" action="Register">
                <ul>
                    <p>User Name       <input type="text" name="username"><font color="red">${UsernameCheck}</font></p>
                    <p>Password        <input type="password" name="password"><font color="red">${PasswordCheck}</font></p> 
                    <p>Confirm Password<input type="password" name="passwordCheck"></p>
                    <p>Email Address   <input type="email" name="email"></p>
                    <input type="submit" value="REGISTER">
                </ul>  
                <%
                session.setAttribute("UsernameCheck", null); 
                session.setAttribute("PasswordCheck", null);
                %>
                </form>

        </article>
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
    </body>
</html>
