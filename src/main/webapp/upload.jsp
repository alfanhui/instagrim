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
            <form method="POST" enctype="multipart/form-data" action="Image">
                File to upload: <input type="file" name="upfile">
                <input type="submit" value="Press"> to upload the file!
            </form>
        </article>
        <footer>
            <jsp:include page="footer.jsp"></jsp:include>
        </footer>
    </body>
</html>
