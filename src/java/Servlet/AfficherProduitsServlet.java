/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import Boutique.Classes.Produit;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
public class AfficherProduitsServlet extends HttpServlet {

    private String URL = "/WEB-INF/afficherproduits.jsp";
    
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
        
        String nomBoutique = URLDecoder.decode(request.getParameter("boutique"), "UTF-8");
        if(nomBoutique == null || nomBoutique.equals(""))
        {
            request.setAttribute("msg", "Cette boutique n'existe pas !");
            request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
        }
        else
        {
           Gestionnaire ge = new Gestionnaire();
           ArrayList<Produit> produits = ge.getProduits(nomBoutique);
           request.setAttribute("produits", produits);
           request.setAttribute("msg", "NomBoutique :" + nomBoutique + "; produits : " + ge.getProduits(nomBoutique));

           // On réalise un suivi de la boutique afin de gérer plus facilement les commandes
           HttpSession session = request.getSession(false);
           if(session != null && session.getAttribute("login") != null)
           {
               session.setAttribute("boutiqueActuelle", ge.getBoutiqueByName(nomBoutique));
           }

           response.setContentType("text/html;charset=UTF-8");
           RequestDispatcher rd = request.getRequestDispatcher(this.URL);
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
