/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnln.tblcomment.TblCommentDAO;
import linhnln.tblusers.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class DeleteCommentServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(DeleteCommentServlet.class);
    private final String SHOW_PAGE = "DisplayingDetailServlet";
    private final String ERROR_PAGE = "deleteCommentError.jsp";

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
        String url = ERROR_PAGE;
        try {
            int articleId = Integer.parseInt(request.getParameter("articleId"));
            int commentId = Integer.parseInt(request.getParameter("commentId"));
            String userIdGoingToDeleteComment = "";
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUsersDTO userGoingToDeleteComment = (TblUsersDTO) session.getAttribute("USER");
                userIdGoingToDeleteComment = userGoingToDeleteComment.getEmail();
            }

            //chỉ có chủ cmt mới đc xóa
            TblCommentDAO commentDao = new TblCommentDAO();
            String emailOfComment = commentDao.getUserIdOfComment(commentId);

            if (userIdGoingToDeleteComment.equals(emailOfComment)) {
                TblCommentDAO dao = new TblCommentDAO();
                boolean check = dao.deleteComment(commentId);
                if (check) {
                    url = SHOW_PAGE + "?articleId=" + articleId;
                }
            } else {
                request.setAttribute("POST_ID", articleId);
            }
        } catch (NumberFormatException ex) { //wrong input in url from user
            LOGGER.error("NumberFormatException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NumberFormatException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
