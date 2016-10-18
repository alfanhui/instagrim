<%-- 
    Document   : nav
    Created on : Sep 30, 2016, 5:47:03 PM
    Author     : Alfanhui
--%>

<%@page import="uk.ac.dundee.computing.swmh.instagrim.stores.LogedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <ul>
        <%LogedIn lg = (LogedIn) session.getAttribute("LogedIn");
            if (lg != null) {
                String UserName = lg.getUsername();
                    if (lg.getLogedin()) {
                        %>
                        <h3><%=lg.getUsername()%></h3>        
                        <% if(lg.hasProfilePic()){ %>
                            <img src="/Instagrim/Thumb/<%=lg.getProfileUUID()%>" width="100" height="100"/>
                        <%}else{%>
                            <img src="/Instagrim/resources/profiledefault.png" alt="profile picture" width="80" height="58" />
                        <%}%>
                    <li><a href="/Instagrim/Upload">Upload</a></li>
                    <li><a href="/Instagrim/Images/<%=lg.getUsername()%>">Your Images</a></li>
                    <li><a href="/Instagrim/Account">Settings</a></li>
                    <form method="GET" action="/Instagrim/Logout">
                        <input type="submit" value="LOGOUT"> 
                    </form>
                    <%}
            }else{%>
            <li><a href="/Instagrim/Register">Register</a></li>
            <li><a href="/Instagrim/Login">Login</a></li>
            <%}%>
    </ul>
</html>
