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
    <body>
        <header>
            <h1>InstaGrim</h1>
            <h2>Your world in Black and White</h2>
        </header>
       <nav>
        <jsp:include page="nav.jsp"></jsp:include>   
        </nav>
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
                <li>&COPY; SWMH</li>
            </ul>
        </footer>
    </body>
</html>
