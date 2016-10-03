<%-- 
    Document   : login.jsp
    Created on : Sep 28, 2014, 12:04:14 PM
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
    </nav>
    <body>
        <header>
        <h1>I n s t a G r i m</h1>
        <h2>Login</h2>
        </header>
        
        <article>
            <form method="POST"  action="Login">
                <ul>
                    <font color="red">${InvalidLogin}</font>
                    <p>User Name <input type="text" name="username"></p>
                    <p>Password <input type="password" name="password"></p>
                </ul>
                <br/>
                <input type="submit" value="Login"> 
            </form>
            <%
                session.setAttribute("InvalidLogin", null);
            %>
            <ul>
                <p><a href="/Instagrim/Register">or Register!</a></p>
            </ul>
        </article>
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
    </body>
</html>
