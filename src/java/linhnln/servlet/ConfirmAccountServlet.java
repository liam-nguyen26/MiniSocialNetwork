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
import linhnln.tblotp.TblOtpDAO;
import linhnln.tblusers.TblUsersCreateError;
import linhnln.tblusers.TblUsersDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class ConfirmAccountServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(ConfirmAccountServlet.class);
    private final String SUCCESS_PAGE = "createAccountSuccess.html";
    private final String ERROR_PAGE = "confirmGmail.jsp";

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
        
        String email = request.getParameter("txtEmail");
        String otpFromUserInputed = request.getParameter("txtOtp");

        String url = ERROR_PAGE;
        TblUsersCreateError errors = new TblUsersCreateError();
        
        try {
            TblOtpDAO otpDao = new TblOtpDAO();
            String otpOnDb = otpDao.getOtpForThisUser(email);

            if (!otpOnDb.equals(otpFromUserInputed)) {
                errors.setOtpMatchError("Your OTP is not matched! Please input again");
                request.setAttribute("OTP_ERROR", errors);
                request.setAttribute("USER_ID", email);
            } else {
                TblUsersDAO userDao = new TblUsersDAO();
                boolean changeStatus = userDao.changeStatusAfterConfirm(email);
                if (changeStatus) {
                    url = SUCCESS_PAGE;
                }
            }
            
        } catch (SQLException ex) {
            LOGGER.error("SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url); //for error in req scope
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
