/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnln.tblarticle.TblArticleDAO;
import linhnln.tblemotion.TblEmotionDAO;
import linhnln.tblemotion.TblEmotionDTO;
import linhnln.tblnotification.TblNotificationDAO;
import linhnln.tblusers.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class GetEmotionServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(GetEmotionServlet.class);
    private final String SHOW_DETAIL_PAGE = "DisplayingDetailServlet";
    private final String NOT_FOUND_PAGE = "notFoundPost.html";

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
        String url = NOT_FOUND_PAGE;
        try {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            int articleId = Integer.parseInt(request.getParameter("articleId"));
            String emotion = request.getParameter("emotion");
            Boolean emotionInsertIntoDabase = null;
            
            TblArticleDAO articleDao = new TblArticleDAO();
            String userIdOfArticle = articleDao.getUserId(articleId); //get user created this post

            HttpSession session = request.getSession(false);
            String fromUserId = "";
            
            if (session != null) {
                TblUsersDTO userDto = (TblUsersDTO) session.getAttribute("USER");
                if (userDto != null) {
                    fromUserId = userDto.getEmail();
                }
            }

            if (emotion.equals("Like")) {
                emotionInsertIntoDabase = true;
            } else if (emotion.equals("Dislike")) {
                emotionInsertIntoDabase = false;
            }

            //for input noti
            TblNotificationDAO notiDao = new TblNotificationDAO();

            //check if already like or dislike
            TblEmotionDAO dao = new TblEmotionDAO();
            TblEmotionDTO recordEmotionDto
                    = dao.checkEmotionIsExisted(fromUserId, articleId);

            if (recordEmotionDto == null) {
                Integer newEmotionInserted = dao.insertNewEmotion(fromUserId,
                        articleId, emotionInsertIntoDabase);
                Integer checkNoti
                        = notiDao.insertNewNoti(userIdOfArticle, articleId, 
                        currentTime, "Emotion", newEmotionInserted, fromUserId);
                
                if (newEmotionInserted != null && checkNoti != null) {
                    url = SHOW_DETAIL_PAGE + "?articleId=" + articleId;
                }
            } else {
                Boolean emotionOnDb = recordEmotionDto.getEmotion();
                Integer emotionIdOnDb = recordEmotionDto.getId();
                boolean isSuccess = false;
                Integer checkNoti = null;
                if (emotionOnDb == null) {
                    if (emotionInsertIntoDabase == true) {
                        isSuccess = dao.updateEmotion(fromUserId, articleId, true);
                        checkNoti
                                = notiDao.insertNewNoti(userIdOfArticle, articleId, 
                                currentTime, "Emotion", emotionIdOnDb, fromUserId);
                    } else if (emotionInsertIntoDabase == false) {
                        isSuccess = dao.updateEmotion(fromUserId, articleId, false);
                        checkNoti
                                = notiDao.insertNewNoti(userIdOfArticle, articleId, 
                                currentTime, "Emotion", emotionIdOnDb, fromUserId);
                    }

                } else {
                    if (emotionOnDb.equals(emotionInsertIntoDabase)) {
                        isSuccess = dao.updateEmotion(fromUserId, articleId, null);
                        checkNoti
                                = notiDao.insertNewNoti(userIdOfArticle, articleId, 
                                currentTime, "Emotion", emotionIdOnDb, fromUserId);
                    } else {
                        isSuccess = dao.updateEmotion(fromUserId, articleId, emotionInsertIntoDabase);
                        checkNoti
                                = notiDao.insertNewNoti(userIdOfArticle, articleId, 
                                currentTime, "Emotion", emotionIdOnDb, fromUserId);
                    }
                }

                if (isSuccess && checkNoti != null) {
                    url = SHOW_DETAIL_PAGE + "?articleId=" + articleId;
                }
            }

        } catch (NumberFormatException ex) {
            LOGGER.error("NumberFormatException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
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
