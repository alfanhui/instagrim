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
            <h1>InstaGrim ! </h1>
            <h2>Your world in Black and White</h2>
        </header>
        <nav>
            <ul>
                    <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                                
                    %>
                <h3><%=lg.getUsername()%></h3>
                
                <% if(lg.hasProfilePic()){ %>
                <img src="/Instagrim/Thumb/<%=lg.getProfileUUID()%>" width="80" height="80"/>
                <%}else{ %>
                    <img src="profiledefault.png" alt="profile picture" width="80" height="58" />
                <%}%>
                <li><a href="upload.jsp">Upload</a></li>
                <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
               
                <li><a href="account.jsp">Settings</a></li>
                <form method="POST"  action="Logout">
                <ul>
                </ul>
                <input type="submit" value="Logout"> 
                
            </form>
                    <%}
                            }else{
                                %>
                 <li><a href="register.jsp">Register</a></li>
                <li><a href="login.jsp">Login</a></li>
                <%
                                        
                            
                    }%>
            </ul>
        </nav>
        <footer>
            <ul>
                <li class="footer"><a href="/Instagrim">Home</a></li>
                <li>&COPY; SWMH</li>
            </ul>
        </footer>
    </body>
</html>
