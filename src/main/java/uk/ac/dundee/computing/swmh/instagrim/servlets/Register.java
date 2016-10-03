/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.dundee.computing.swmh.instagrim.servlets;

import com.datastax.driver.core.Cluster;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import uk.ac.dundee.computing.swmh.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.swmh.instagrim.models.PicModel;
import uk.ac.dundee.computing.swmh.instagrim.models.User;
import uk.ac.dundee.computing.swmh.instagrim.stores.LoggedIn;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "Register", urlPatterns = {"/Register"})
public class Register extends HttpServlet {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        User us=new User();
        us.setCluster(cluster);
        
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String passwordCheck=request.getParameter("passwordCheck");
        String email=request.getParameter("email");
        if(!password.equals(passwordCheck)){
            //Password Check failed
            session.setAttribute("PasswordCheck", "Passwords do not match!");
            response.sendRedirect("/Instagrim/register.jsp");
        }else if(!us.IsValidUsername(username)){
            session.setAttribute("UsernameCheck", "Username already used!");
            response.sendRedirect("/Instagrim/register.jsp");
        }else{
        us.RegisterUser(username, password, email);
        LoggedIn lg= new LoggedIn();
        lg.setLogedin();
        lg.setUsername(username);            
        session.setAttribute("LoggedIn", lg);
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
        return "Short description";
    }// </editor-fold>

}
