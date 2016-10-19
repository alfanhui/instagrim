/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.dundee.computing.swmh.instagrim.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Stuart Huddy
 */
@WebServlet(name = "Home", urlPatterns = {"/Home"})
public class Home {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
	    rd.forward(request,response);    
    }
    
   /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Home Servlet: Currently for REST purposes only.";
    }// </editor-fold>

    
}
