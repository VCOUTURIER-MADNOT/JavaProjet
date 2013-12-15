/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Boutique.Classes.Produit;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Valentin
 */
public class AjouterProduitServlet extends HttpServlet {

    private String URLOk = "/WEB-INF/ajouterproduit.jsp";
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nomProduit = request.getParameter("nom");
        String descProduit = request.getParameter("description");
        int prixProduit = Integer.parseInt(request.getParameter("prix"));
        String nomBoutique = URLDecoder.decode(request.getParameter("nomBoutique"), "UTF-8");
        
        Gestionnaire ge = new Gestionnaire();
        
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        
        if(session == null || session.getAttribute("login") == null || !session.getAttribute("login").equals(ge.getBoutiqueByName(nomBoutique).getAdmin().getLogin()))
        {
            request.setAttribute("msg", "Vous n'avez pas le droit d'ajouter de produit à cette boutique");
            RequestDispatcher rd = request.getRequestDispatcher(this.URLOk);
            rd.forward(request, response); 
        }
        else
        {
            String login = (String) session.getAttribute("login");
            
            String rep = ge.ajoutProduit(login + "'s Shop", new Produit(nomProduit, descProduit, prixProduit));
            
            request.setAttribute("msg", rep);
            RequestDispatcher rd = request.getRequestDispatcher(this.URLOk);
            rd.forward(request, response);
        }
        
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
        processRequest(request, response);
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
