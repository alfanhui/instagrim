<%-- 
    Document   : account
    Created on : Sep 28, 2014, 7:01:44 PM
    Author     : Stuart Huddy
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
            <h3>Change Profile Picture</h3> <input type="file" name="upfile"><p><font color="red">${uploadBad}</font></p>
            <br>
            <h3>Change Password:</h3><p><font color="green">${passwordOk}</font></p><p><font color="red">${passwordBad}</font></p>
            <p>        Old Password:<input type="password" name="oldPassword"><br></p><p><font color="red">${PasswordCheck}</font></p>
            <p>        New Password:<input type="password" name="newPassword"><br></p><p><font color="red">${newPasswordCheck}</font></p></p>
            <p>Confirm New Password:<input type="password" name="passwordCheck"><br></p>
            <br>
            <h3>Delete Account</h3>
                <input type="radio" name="delete" value="no"> No<br>
                <input type="radio" name="delete" value="yes"> Yes<br>
            </p>
            
            <input type="submit" value="Submit">        
        </form>
    </body>
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
    </body>
</html>
