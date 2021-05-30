/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnln.tblusers.TblUsersDAO;
import linhnln.tblusers.TblUsersDTO;
import linhnln.utils.EncodingEmailHelper;
import org.apache.log4j.Logger;


/**
 *
 * @author Administrator
 */
public class LoginServlet extends HttpServlet {

    private final String SEARCH_PAGE = "search.jsp";
    private final String INVALID_PAGE = "invalid.html";
    static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
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
        PrintWriter out = response.getWriter();
        String url = "";
        String email = request.getParameter("txtEmail").trim();
        String password = request.getParameter("txtPassword").trim();
        try {
            TblUsersDAO dao = new TblUsersDAO();
            TblUsersDTO dto = dao.checkLogin(email, password);
            if (dto != null) {
                url = SEARCH_PAGE;
                //for remember password
                String encodedEmail = EncodingEmailHelper.encodeEmail(email);
                Cookie cookie = new Cookie(encodedEmail, password); //pw ko co hashed khi luu vao cookie
                cookie.setMaxAge(60 * 3);
                response.addCookie(cookie);
                
                HttpSession session  = request.getSession(true);
                session.setAttribute("USER", dto);
                session.setAttribute("FULL_NAME", dto.getFullName());
            } else {
                url = INVALID_PAGE;
            }

        } catch (SQLException ex) {
            LOGGER.error("SQLException: "+ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: "+ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException: "+ex.getMessage());
        } finally {
            response.sendRedirect(url);
            out.close();
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
