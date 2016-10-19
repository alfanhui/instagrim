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
import uk.ac.dundee.computing.swmh.instagrim.models.User;
import uk.ac.dundee.computing.swmh.instagrim.stores.LogedIn;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {
    Cluster cluster=null;
    
    @Override
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        User us=new User();
        us.setCluster(cluster);
        session.setAttribute("PasswordCheck", null);
        session.setAttribute("UsernameCheck", null);
        String username=request.getParameter("username");
        username= username.toLowerCase();
        String password=request.getParameter("password");
        String passwordCheck=request.getParameter("passwordCheck");
        String email=request.getParameter("email");
        if(!password.equals(passwordCheck)){
            //Password Check failed
            session.setAttribute("PasswordCheck", "Passwords do not match!");
            response.sendRedirect("/Instagrim/register.jsp");
        }else if(!us.IsValidUsername(username)){
            session.setAttribute("UsernameCheck", "Username already taken!");
            response.sendRedirect("/Instagrim/register.jsp");
        }else if(!us.IsValidPassword(password)){
            //Password Check failed
            session.setAttribute("PasswordCheck", "Password must be at least 6 characters long.");
            response.sendRedirect("/Instagrim/register.jsp");
        }else{
            us.RegisterUser(username, password, email);
            LogedIn lg= new LogedIn();
            lg.setLogedin();
            lg.setUsername(username);            
            session.setAttribute("LogedIn", lg);
            response.sendRedirect("/Instagrim");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{ 
            RequestDispatcher rd=request.getRequestDispatcher("register.jsp");
	    rd.forward(request,response);
    }

    
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Register Sevlet: Takes information for user, "
                + "checks to see if details are valid,"
                + "proceeds to log user in and return to index."
                + "If anything fails, return back to register screen with error messages.";
    }// </editor-fold>

}
