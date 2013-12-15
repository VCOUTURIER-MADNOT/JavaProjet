/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import JavaRMI.Classes.Boutique;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
public class AjouterCommandeServlet extends HttpServlet {

    private String URLOk = "/WEB-INF/index.jsp";
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
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(false);
        response.setContentType("text/html;charset=UTF-8");
        
        if(session == null || session.getAttribute("login") == null )
        {
            request.setAttribute("msg", "Vous n'avez pas le droit de commander");
            RequestDispatcher rd = request.getRequestDispatcher(this.URLOk);
            rd.forward(request, response); 
        }
        else
        {
            String login = (String) session.getAttribute("login");
            
            Gestionnaire ge = new Gestionnaire();
            
            // Recuperation de l'arraylist de string
            ArrayList<String> produits = new ArrayList<>();
            String nomProduitsBrut = (String)request.getParameter("produits");
            // L'expression reguliere est "\\\\t" car "\t" dans une barre d'adresse devient "\\t"
            String[] nomProduits = nomProduitsBrut.split("\\\\t");
            produits.addAll(Arrays.asList(nomProduits));
            
            // On ajoute la commande
            Boutique b = (Boutique)ge.getBoutiqueByName((String)session.getAttribute("boutiqueActuelle"));
            String rep = ge.ajouterCommande(produits, (String)session.getAttribute("login"), b.getNom());
            
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
