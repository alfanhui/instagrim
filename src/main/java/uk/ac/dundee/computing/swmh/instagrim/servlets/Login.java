/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.swmh.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.swmh.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.swmh.instagrim.models.PicModel;
import uk.ac.dundee.computing.swmh.instagrim.models.User;
import uk.ac.dundee.computing.swmh.instagrim.stores.LogedIn;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    Cluster cluster=null;


    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        session.setAttribute("InvalidLogin", null);
        String username=request.getParameter("username");
        username= username.toLowerCase();
        String password=request.getParameter("password");
        User us=new User();
        us.setCluster(cluster);
        boolean isValid=us.IsValidUser(username, password);
        
        System.out.println("Session in servlet "+session);
        if (isValid){
            LogedIn lg= new LogedIn();
            lg.setLogedin();
            lg.setUsername(username);
            lg.setEmail(us.getEmail(username));
            lg.setProfileUUID(us.getProfileUUID(username));
            session.setAttribute("LogedIn", lg);
            System.out.println("Session in servlet "+session);
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            session.setAttribute("homeScreen", tm.getRandomPic()); //Random Home screen picture - should be server variable
            RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
	    rd.forward(request,response);
           
            
        }else{
            session.setAttribute("InvalidLogin", "Invalid Username or Password");
            response.sendRedirect("/Instagrim/Login");
        }
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{
            RequestDispatcher rd=request.getRequestDispatcher("login.jsp");
	    rd.forward(request,response);
    }
    
    
    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login Servlet: Takes username & password, "
                + "checks if valid, and sets login values. "
                + "Returns user to index or login(if details are invalid";
    }// </editor-fold>

}
