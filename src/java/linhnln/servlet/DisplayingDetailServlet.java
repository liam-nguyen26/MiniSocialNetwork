/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import linhnln.tblcommenthavingtbluserstblarticle.TblCommentHavingTblUsersTblArticleDAO;
import linhnln.tblcommenthavingtbluserstblarticle.TblCommentHavingTblUsersTblArticleDTO;
import linhnln.tblemotion.TblEmotionDAO;
import linhnln.tblusershavingtblarticle.TblUsersHavingTblArticleDAO;
import linhnln.tblusershavingtblarticle.TblUsersHavingTblArticleDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class DisplayingDetailServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(DisplayingDetailServlet.class);
    private final String DETAIL_PAGE = "show.jsp";
    private final String INVALID_PAGE = "notFoundPost.html";

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
        //request dispatch dang giu cai lastSearchValue tu search.jsp
        String url = INVALID_PAGE;
        try {
            int articleId = Integer.parseInt(request.getParameter("articleId"));
            TblUsersHavingTblArticleDAO articleDao = new TblUsersHavingTblArticleDAO();
            TblUsersHavingTblArticleDTO articleDto = 
                            articleDao.getAritcleDetailWithUserName(articleId);

            if (articleDto != null) {
                url = DETAIL_PAGE;
                //for emotion
                TblEmotionDAO emotionDao = new TblEmotionDAO();
                int totalLikes = emotionDao.getLikeEmotion(articleId);
                int totalDislike = emotionDao.getDislikeEmotion(articleId);

                //for comment
                TblCommentHavingTblUsersTblArticleDAO commentDao
                        = new TblCommentHavingTblUsersTblArticleDAO();
                commentDao.getComments(articleId);
                List<TblCommentHavingTblUsersTblArticleDTO> listComment
                        = commentDao.getListComment();

                request.setAttribute("POST", articleDto);
                request.setAttribute("LIKE_EMO", totalLikes);
                request.setAttribute("DIS_LIKE_EMO", totalDislike);
                request.setAttribute("COMMENT_LIST", listComment);
                request.setAttribute("ARTICLE_ID", articleId);
            }
        } catch (NumberFormatException ex) {
            LOGGER.error("NumberFormatException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
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
