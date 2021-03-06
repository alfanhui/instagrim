package uk.ac.dundee.computing.swmh.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import uk.ac.dundee.computing.swmh.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.swmh.instagrim.lib.Convertors;
import uk.ac.dundee.computing.swmh.instagrim.models.PicModel;
import uk.ac.dundee.computing.swmh.instagrim.stores.LogedIn;
import uk.ac.dundee.computing.swmh.instagrim.stores.Pic;

/**
 * Servlet implementation class Image
 */
@WebServlet(urlPatterns = {
    "/Image",
    "/Image/*",
    "/Thumb/*",
    "/Images",
    "/Images/*",
    "/Upload"
})
@MultipartConfig

public class Image extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private Cluster cluster;
    private HashMap CommandsMap = new HashMap();
    

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Image() {
        super();
        // TODO Auto-generated constructor stub
        CommandsMap.put("Image", 1);
        CommandsMap.put("Images", 2);
        CommandsMap.put("Thumb", 3);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        
        String args[] = Convertors.SplitRequestPath(request);
        int command;
        if(args[1].equals("Upload")){
            RequestDispatcher rd = request.getRequestDispatcher("upload.jsp");
            rd.forward(request, response);
        }else{
            try {
                command = (Integer) CommandsMap.get(args[1]);
            } catch (Exception et) {
                error("Bad Operator", response);
                return;
            }
            switch (command) {
                case 1:
                    DisplayImage(Convertors.DISPLAY_PROCESSED,args[2], response);
                    break;
                case 2:
                    DisplayImageList(args[2], request, response);
                    break;
                case 3:
                    DisplayImage(Convertors.DISPLAY_THUMB,args[2],  response);
                    break;
                default:
                    error("Bad Operator", response);
            }  
        }
    }

    private void DisplayImageList(String User, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        java.util.LinkedList<Pic> lsPics = tm.getPicsForUser(User);
   
        RequestDispatcher rd = request.getRequestDispatcher("/usersPics.jsp");
        request.setAttribute("Pics", lsPics);
        rd.forward(request, response);
    }
    
    private void DisplayImage(int type,String Image, HttpServletResponse response) throws ServletException, IOException {
        PicModel tm = new PicModel();
        tm.setCluster(cluster);
        Pic p = tm.getPic(type,java.util.UUID.fromString(Image));
        OutputStream out = response.getOutputStream();
        response.setContentType(p.getType());
        response.setContentLength(p.getLength());
        //out.write(Image);
        InputStream is = new ByteArrayInputStream(p.getBytes());
        BufferedInputStream input = new BufferedInputStream(is);
        byte[] buffer = new byte[8192];
        for (int length = 0; (length = input.read(buffer)) > 0;) {
            out.write(buffer, 0, length);
        }
        out.close();
    }
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{       
        HttpSession session=request.getSession();
        session.setAttribute("uploadok", null);
        session.setAttribute("uploadbad", null);
        
        for (Part part : request.getParts()) {
            System.out.println("Part Name " + part.getName());
            String type = part.getContentType();             
            String filename = part.getSubmittedFileName();
            System.out.print("TYPE" + type);
            if(type.startsWith("application/octet-stream")){
                continue;
            }else if(!type.startsWith("image")){
                session.setAttribute("uploadbad", "Opps, you can only upload .png .jpg .jpeg .gif files!");
                continue;
            }
            InputStream is = request.getPart(part.getName()).getInputStream();
            int i = is.available();
            LogedIn lg= (LogedIn)session.getAttribute("LogedIn");
            if(lg.getLogedin()){
                String username=lg.getUsername();
                if(i > 0){
                    byte[] b = new byte[i + 1];
                    is.read(b);
                    System.out.println("Length : " + b.length);
                    PicModel tm = new PicModel();
                    tm.setCluster(cluster);
                    try{
                        tm.insertPic(b, type, filename, username, false);
                    }catch(Exception e){
                        session.setAttribute("uploadbad", e);
                        response.sendRedirect("/Instagrim/Upload");
                    }
                    is.close();
                }
                session.setAttribute("uploadok", "Uploaded successful!");
                RequestDispatcher rd = request.getRequestDispatcher("upload.jsp");
                rd.forward(request, response);
            }
        }
        if(session.getAttribute("uploadbad")!= null){ //Bad file
            RequestDispatcher rd = request.getRequestDispatcher("upload.jsp");
            rd.forward(request, response);   
        }else{                                      //Bad username
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);   
        }
    }

    
    private void error(String mess, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;
        out = new PrintWriter(response.getOutputStream());
        out.println("<h1>You have an error in your input</h1>");
        out.println("<h2>" + mess + "</h2>");
        out.close();
        return;
    }
    
   /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Image Servlet: deals with all image related I/O. Indirectly speaks to the database.";
    }// </editor-fold>

    
    
}
