<%-- 
    Document   : upload
    Created on : Sep 22, 2014, 6:31:50 PM
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
        <jsp:include page="nav.jsp"></jsp:include>   
    </nav>
    <body>
        <header>
            <h1>I n s t a G r i m</h1>
            <h2>Upload Photos</h2>
        </header>
        <article>
            <ul>
            <p><font color="green">${uploadok}</font></p>
            <p><font color="red">${uploadbad}</font></p>
            <form method="POST" enctype="multipart/form-data" action="Image">
                <p>Files to upload:</p>
                <input type="file" name="upfile0"></br>
                <input type="file" name="upfile1"></br>
                <input type="submit" value="UPLOAD" width="150" height="150">
            </form>
            <%
                session.setAttribute("uploadok", null);
                session.setAttribute("uploadbad", null);
            %>
            </ul>
        </article>
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
    </body>
</html>
