<%-- 
    Document   : usersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Stuart Huddy
--%>

<%@page import="java.util.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="uk.ac.dundee.computing.swmh.instagrim.stores.*" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagrim</title>
        <link rel="stylesheet" type="text/css" href="/Instagrim/Styles.css" />
    </head>
    <nav>
        <jsp:include page="nav.jsp"></jsp:include>   
    </nav>
    <body>
        <header>
            <h1>I n s t a G r i m</h1>
            <h2>Your Stream</h2>
        </header>
        <article>
            <ul>
        <%
            java.util.LinkedList<Pic> lsPics = (java.util.LinkedList<Pic>) request.getAttribute("Pics");
            if (lsPics == null) {
        %>
        <p>No Pictures found</p>
        <%
        } else {
            Iterator<Pic> iterator;
            iterator = lsPics.iterator();
            int counter = 0;
            while (iterator.hasNext()) {
                Pic p = (Pic) iterator.next();
                %>
                <a href="/Instagrim/Comments/<%=p.getSUUID()%>" ><img src="/Instagrim/Thumb/<%=p.getSUUID()%>"></a> 
                <%if(counter==3){
                    counter=0;%>
                    </br>
                    <p></p>
                <%}                

            }
        }
        %>
        <ul>
        </article>
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
      </body>
</html>
