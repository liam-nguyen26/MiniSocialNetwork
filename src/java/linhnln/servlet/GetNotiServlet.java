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
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import linhnln.tblarticle.TblArticleDAO;
import linhnln.tblnotification.TblNotificationDAO;
import linhnln.tblnotification.TblNotificationDTO;
import linhnln.tblnotificationhavingtblusers.TblNotificationHavingTblUsersDTO;
import linhnln.tblusers.TblUsersDAO;
import linhnln.tblusers.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class GetNotiServlet extends HttpServlet {

    private final String SHOW_PAGE = "noti.jsp";
    static final Logger LOGGER = Logger.getLogger(GetNotiServlet.class);

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
        String url = SHOW_PAGE;
        String userId = "";
        try {
            //get nguoi duoc thong bao
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUsersDTO userDto = (TblUsersDTO) session.getAttribute("USER");
                if (userDto != null) {
                    userId = userDto.getEmail();
                }
            }

            //get list noti
            TblNotificationDAO notiDao = new TblNotificationDAO();
            notiDao.getNoti(10, userId);
            List<TblNotificationDTO> listNoti = notiDao.getListNoti();

            List<TblNotificationHavingTblUsersDTO> listNotiWithArticleId
                    = new ArrayList<>();

            TblUsersDAO userDao = new TblUsersDAO();
            TblArticleDAO articleDao = new TblArticleDAO();

            if (listNoti != null) {
                for (TblNotificationDTO noti : listNoti) {
                    String fromUserId = noti.getFromUserId();
                    int articleId = noti.getArticleId();

                    String fullName = userDao.getFullname(fromUserId);
                    String title = articleDao.getTitle(articleId);
                    Timestamp dateAction = noti.getDateOfNoti();
                    String typeOfAction = noti.getTypeOfAction();
                    
                    
                    if (!userId.equals(fromUserId)) {
                        TblNotificationHavingTblUsersDTO fullNoti
                                = new TblNotificationHavingTblUsersDTO(articleId, dateAction, typeOfAction, title, fullName);
                        listNotiWithArticleId.add(fullNoti);
                    }
                }
                request.setAttribute("LIST_NOTI", listNotiWithArticleId);
            }

        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
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
