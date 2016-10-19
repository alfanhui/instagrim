<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.swmh.instagrim.stores.*" %>
<%@page import="uk.ac.dundee.computing.swmh.instagrim.servlets.*" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="Styles.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <nav>
        <jsp:include page="nav.jsp"></jsp:include>   
    </nav>
    <body>
        <header>
            <h1>I n s t a G r i m</h1>
            <h2>Your world in Black & White</h2>
        </header>
        <%
            if(session.getAttribute("homeScreen") == null){
        %>
        <h3>Random Pic of the Day</h3>         
        <a href="/Instagrim/Comments/${homeScreen}" ><img src="/Instagrim/Thumb/${homeScreen}"></a> 
        <%
            }
        %>
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
    </body>
</html>
