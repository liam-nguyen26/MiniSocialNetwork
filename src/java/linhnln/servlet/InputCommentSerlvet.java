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
import linhnln.tblcomment.TblCommentDAO;
import linhnln.tblnotification.TblNotificationDAO;
import linhnln.tblusers.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class InputCommentSerlvet extends HttpServlet {

    private final String SHOW_DETAIL = "DisplayingDetailServlet";
    private final String LOGIN_PAGE = "login.html";
    static final Logger LOGGER = Logger.getLogger(InputCommentSerlvet.class);

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
        HttpSession session = request.getSession();
        String url = LOGIN_PAGE;
        //neu nhu ko co session se hien ve login page
        String lastSearchValue = request.getParameter("lastSearchValue");
        try {
            //for input comment
            request.setCharacterEncoding("UTF-8");
            String content = new String(request.getParameter("txtComment").getBytes("iso-8859-1"), "utf-8");
            int articleId = Integer.parseInt(request.getParameter("articleId"));
            TblArticleDAO articleDao = new TblArticleDAO();
            String toUserId = articleDao.getUserId(articleId);

            String fromUserId = "";
            if (session != null) {
                TblUsersDTO userDto = (TblUsersDTO) session.getAttribute("USER");
                if (userDto != null) {
                    fromUserId = userDto.getEmail();
                }
            }

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            TblCommentDAO commentDao = new TblCommentDAO();
            Integer res = commentDao.insertNewComment(content, currentTime, fromUserId, articleId);

            //for input noti
            TblNotificationDAO notiDao = new TblNotificationDAO();
            Integer checkNoti
                    = notiDao.insertNewNoti(toUserId, articleId, currentTime, "Comment", res, fromUserId);
            if (res != null && checkNoti != null) {
                url = SHOW_DETAIL + "?articleId=" + articleId
                        + "&lastSearchValue=" + lastSearchValue;
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            LOGGER.error("NumberFormatException: " + ex.getMessage());
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
