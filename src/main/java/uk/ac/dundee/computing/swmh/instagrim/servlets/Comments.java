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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import uk.ac.dundee.computing.swmh.instagrim.lib.CassandraHosts;
import uk.ac.dundee.computing.swmh.instagrim.models.PicModel;


/**
 *
 * @author Administrator
 */
@WebServlet(name = "Comments", urlPatterns = {"/Comments/*"})
@MultipartConfig
public class Comments extends HttpServlet {
    private Cluster cluster=null;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub
        cluster = CassandraHosts.getCluster();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException{ 
            HttpSession session=request.getSession();
            String uri = request.getRequestURI();
            String[] parts = uri.split("/");
            if(parts.length != 4){
                response.sendRedirect("/Instagrim/Login");
            }else{
                session.setAttribute("uuid", parts[3]);
                PicModel tm = new PicModel();
                tm.setCluster(cluster);
                String commentArray[] = tm.getComment(parts[3]);
                session.setAttribute(parts[3], commentArray);
                RequestDispatcher rd = request.getRequestDispatcher("/comments.jsp");
                rd.forward(request, response);
            }
            
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session=request.getSession();
            String uuid = (String)session.getAttribute("uuid");
            String comment = request.getParameter("commentInput");
            PicModel tm = new PicModel();
            tm.setCluster(cluster);
            tm.setComment(uuid, comment);
            RequestDispatcher rd = request.getRequestDispatcher("/comments.jsp");
            rd.forward(request, response);
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
