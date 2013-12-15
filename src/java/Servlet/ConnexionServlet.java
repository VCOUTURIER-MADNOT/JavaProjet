/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import JavaRMI.Classes.Boutique;
import JavaRMI.Classes.Utilisateur;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author valentin
 */
public class ConnexionServlet extends HttpServlet {

    private String URL = "/WEB-INF/index.jsp";
    
    private Gestionnaire gestionnaire = new Gestionnaire();
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
        request.getRequestDispatcher(this.URL).forward(request, response);
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
        String login = request.getParameter("login");
        String mdp = request.getParameter("mdp");
        
        if(gestionnaire.isValidAuthentication(login, mdp))
        {
            Utilisateur u = gestionnaire.getUtilisateur(login);
            Gestionnaire ge = new Gestionnaire();
            Boutique b = ge.getBoutiqueByAdmin(login) != null ? ge.getBoutiqueByAdmin(login) : ge.getBoutiques().get(0) ;
            HttpSession session = request.getSession(true);
            session.setAttribute("produits", b != null ? ge.getProduits(b.getNom()) : null);
            session.setAttribute("boutiques", ge.getBoutiques());
            session.setAttribute("login", login);
            session.setAttribute("nom", u.getNom());
            session.setAttribute("admin", u.getUserLevel() >= 3);
            session.setAttribute("boutiqueDefaut", b != null ? b.getNom() : "");
            session.setAttribute("aBoutique", ge.getBoutiqueByAdmin(login) != null ? "oui" : "non");
            request.setAttribute("msg", "Réussi");
        }
        else
        {
            request.setAttribute("msg", "Echec");
        }
        
        
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
