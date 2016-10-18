<%-- 
    Document   : UsersPics
    Created on : Sep 24, 2014, 2:52:48 PM
    Author     : Administrator
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
            <h2>Comments</h2>
        </header>
        <article>
            <ul>
        <%
            
            %>
            <tr>
                <td><a href="/Instagrim/Image/${uuid}" ><img src="/Instagrim/Thumb/${uuid}"></a><td>
                <td>
                    <textarea name="comments" rows = "19" cols="70" readonly> <% 
                        String commentArray[] = (String[])session.getAttribute("comments");
                        for(int i = 0; i<commentArray.length;i++){
                            %>
                            <%=commentArray[i]%>
                        <%}%>
                    </textarea> <br>
                    <form method="POST"  action="Comment">
                    <input type="text" name="commentInput" placeholder="type here to comment">
                    </form>
                </td>
            </tr>
                <%          

        %>
        <ul>
        </article>
        
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
      </body>
</html>
