/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.swmh.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.InputStream;
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
import uk.ac.dundee.computing.swmh.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.swmh.instagrim.models.PicModel;
import uk.ac.dundee.computing.swmh.instagrim.models.User;
import uk.ac.dundee.computing.swmh.instagrim.stores.LogedIn;


/**
 *
 * @author Administrator
 */
@WebServlet(name = "Account", urlPatterns = {"/Account"})
@MultipartConfig
public class Account extends HttpServlet {
    private Cluster cluster=null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        session.setAttribute("uploadbad", null);
        session.setAttribute("passwordBad", null);
        session.setAttribute("passwordOk", null);
        session.setAttribute("passwordCheck", null);
        session.setAttribute("newPasswordCheck", null);
        User us=new User();
        us.setCluster(cluster);
        
        LogedIn lg= (LogedIn)session.getAttribute("LogedIn");
       
        if(lg != null){
            String username=lg.getUsername();
            
            //Update Password
                
                String oldPassword=request.getParameter("newPassword");
                String newPassword=request.getParameter("oldPassword");
                String passwordCheck=request.getParameter("passwordCheck");
                boolean isValid=us.IsValidUser(username, oldPassword);
                if(isValid){
                    if(!newPassword.equals(passwordCheck)){
                        //Password Check failed
                        System.out.println("FAILED CHECK");
                        session.setAttribute("PasswordCheck", "Passwords do not match!");
                        response.sendRedirect("/Instagrim/account.jsp");
                    }else if(!us.IsValidPassword(newPassword)){
                        //Password Check failed
                        session.setAttribute("newPasswordCheck", "Password must be at least 6 characters long.");
                        response.sendRedirect("/Instagrim/account.jsp");
                    }else{
                        if(us.updatePassword(username, newPassword)){
                            session.setAttribute("passwordOk", "Password updated!");
                        }
                        else{
                            session.setAttribute("passwordBad", "Password update failed!");
                        }
                    }
                }

                //Delete Account
                if(request.getParameter("delete")!=null){
                    String deleteAccount = request.getParameter("delete");
                    if(deleteAccount.equals("yes")){
                        if(session.getAttribute("LogedIn") != null){
                            if(us.removeUser(username)){
                                lg.setLogedout();
                                session.setAttribute("LogedIn", null);
                                RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
                                rd.forward(request,response);
                            }
                        }
                    }
                }

            for (Part part : request.getParts()) {
                System.out.println("Part Name " + part.getName());

                //Profile Picture
                if(part.getContentType() != null){
                    String type = part.getContentType();
                    if(type.startsWith("image")){
                        String filename = part.getSubmittedFileName();
                        InputStream is = request.getPart(part.getName()).getInputStream();
                        int i = is.available();            
                        if (username == null){
                            System.out.print("Error, no user login");
                            response.sendError(HttpServletResponse.SC_NOT_FOUND);
                        }
                        if (i > 0) {
                            byte[] b = new byte[i + 1];
                            is.read(b);
                            System.out.println("Length : " + b.length);
                            PicModel tm = new PicModel();
                            tm.setCluster(cluster);
                            tm.insertPic(b, type, filename, username, true);
                            lg.setProfileUUID(us.getProfileUUID(username));
                            is.close();
                        }
                    }else if(type.startsWith("application/octet-stream")){
                        session.setAttribute("uploadBad", null);
                    }else{
                        session.setAttribute("uploadBad", "Opps, you can only upload .png .jpg .jpeg .gif files!");
                    }
                }
            }
        }
        RequestDispatcher rd=request.getRequestDispatcher("account.jsp");
        rd.forward(request, response);
    }
   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{ 
            RequestDispatcher rd=request.getRequestDispatcher("account.jsp");
	    rd.forward(request,response);
    }
    
    
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
