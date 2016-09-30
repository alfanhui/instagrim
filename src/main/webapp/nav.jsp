<%-- 
    Document   : nav
    Created on : Sep 30, 2016, 5:47:03 PM
    Author     : Alfanhui
--%>

<%@page import="uk.ac.dundee.computing.swmh.instagrim.stores.LoggedIn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <ul>
                    <%
                        
                        LoggedIn lg = (LoggedIn) session.getAttribute("LoggedIn");
                        if (lg != null) {
                            String UserName = lg.getUsername();
                            if (lg.getlogedin()) {
                                
                    %>
                <h3><%=lg.getUsername()%></h3>
                
                <% if(lg.hasProfilePic()){ %>
                <img src="/Instagrim/Thumb/<%=lg.getProfileUUID()%>" width="100" height="100"/>
                <%}else{ %>
                    <img src="resources/profiledefault.png" alt="profile picture" width="80" height="58" />
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
</html>
