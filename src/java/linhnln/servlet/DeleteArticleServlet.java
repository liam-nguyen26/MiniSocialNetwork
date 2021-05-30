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
import linhnln.tblarticle.TblArticleDAO;
import linhnln.tblcomment.TblCommentDAO;
import linhnln.tblemotion.TblEmotionDAO;
import linhnln.tblusers.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class DeleteArticleServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(DeleteCommentServlet.class);
    private final String DELETE_SUCCESS_PAGE = "deleteSuccessPage.jsp";
    private final String NOT_FOUND_ARTICLE_PAGE = "notFoundPost.html";
    private final String DELETE_ERROR_PAGE = "deleteErrorPage.jsp";

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
        String url = NOT_FOUND_ARTICLE_PAGE;
        try {
            int articleId = Integer.parseInt(request.getParameter("articleId"));
            HttpSession session = request.getSession(false);
            String fromUserId = "";
            if (session != null) {
                TblUsersDTO userDto = (TblUsersDTO) session.getAttribute("USER");
                if (userDto != null) {
                    fromUserId = userDto.getEmail();
                }
            }
            //user xoa phai la nguoi tao ra post nay
            TblArticleDAO articleDao = new TblArticleDAO();
            String userIdOfArticle = articleDao.getUserId(articleId);
            
            if (fromUserId != null && fromUserId.equals(userIdOfArticle)) {
                TblCommentDAO commentDao = new TblCommentDAO();
                boolean deleteComment = commentDao.deleteCommentForDeleteArticle(articleId);

                TblEmotionDAO emotionDao = new TblEmotionDAO();
                boolean deleteEmotion = emotionDao.deleteEmotionForDeleteArticle(articleId);

                boolean deleteArticle = articleDao.deleteArticle(articleId);

                if (deleteArticle && deleteComment && deleteEmotion) {
                    url = DELETE_SUCCESS_PAGE;
                }
            } else {
                url = DELETE_ERROR_PAGE;
                request.setAttribute("POST_ID", articleId);
            }

        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            LOGGER.error("NumberFormatException: " + ex.getMessage());
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
