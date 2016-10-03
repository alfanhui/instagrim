<%-- 
    Document   : index
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Administrator
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="uk.ac.dundee.computing.swmh.instagrim.stores.*" %>
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
            <h2>Account Details</h2>
        </header>
    <body>        
        <form method="POST" enctype="multipart/form-data" action="Account">
            <h3>Change Profile Picture</h3> <input type="file" name="upfile"> <p>
            <br>
            <h3>Change Password:</h3>
            <p>    Old Password:<input type="password" name="oldPassword"><br>
            <p>    New Password:<input type="password" name="newPassword"><br>
            <p>Confirm Password:<input type="password" name="CheckPassword"></br></p>
            
            <input type="submit" value="Submit">        
        </form>
    </body>
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
    </body>
</html>
