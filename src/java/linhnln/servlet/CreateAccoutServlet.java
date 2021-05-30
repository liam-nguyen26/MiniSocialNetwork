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
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import linhnln.tblotp.TblOtpDAO;
import linhnln.tblusers.TblUsersCreateError;
import linhnln.tblusers.TblUsersDAO;
import linhnln.utils.AuthGmailHelper;
import linhnln.utils.EncryptHelper;
import linhnln.utils.RandomOtpHelper;
import linhnln.utils.SendGoogleEmailHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "CreateAccoutServlet", urlPatterns = {"/CreateAccoutServlet"})
public class CreateAccoutServlet extends HttpServlet {

    private final String ERROR_PAGE = "createNewAccount.jsp";
    private final String CONFIRM_PAGE = "confirmGmail.jsp";
    static final Logger LOGGER = Logger.getLogger(CreateAccoutServlet.class);

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
        
        request.setCharacterEncoding("UTF-8");
        
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullName = new String(request.getParameter("txtFullname").
                                                getBytes("iso-8859-1"), "utf-8");
        String url = ERROR_PAGE;
        boolean foundError = false;
        TblUsersCreateError errors = new TblUsersCreateError();
        try {
            if (!AuthGmailHelper.checkGmail(email)) {
                foundError = true;
                errors.setEmailRegextError("Please input the right form "
                        + "of gmail: example@gmail.com");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                foundError = true;
                errors.setPasswordLengthError("Password is "
                        + "requried from 6 to 30");
            } else if (!confirm.trim().equals(password)) {
                foundError = true;
                errors.setConfirmLengthError("Confirm must"
                        + " match the password");
            }
            if (fullName.trim().length() < 5 || fullName.trim().length() > 50) {
                foundError = true;
                errors.setFullnameLengthError("Fullname is required "
                        + "from 5 to 50");
            }
            if (foundError) {
                request.setAttribute("CREATE_ERROR", errors);
            } else {
                TblUsersDAO dao = new TblUsersDAO();
                String encryptedPassword = EncryptHelper.hashPassword(password);
                Integer result = dao.create(email, fullName, encryptedPassword,
                        "Member", "New");
                if (result != null) {
                    //nếu khác null thì đã có trong db rồi nên lấy email người dùng nhập lun
                    TblOtpDAO otpDao = new TblOtpDAO();
                    String otp = RandomOtpHelper.getRandomNumberString();
                    Integer checkInsertOtp = otpDao.insertNewOtp(otp, email);
                    if (checkInsertOtp != null) {
                        boolean isSend = SendGoogleEmailHelper.sendMail(email, otp);
                        if (isSend) {
                            request.setAttribute("USER_ID", email);
                            url = CONFIRM_PAGE;
                        }
                    }
                }
            }

        } catch (SQLException ex) {
            String msg = ex.getMessage();
            LOGGER.error("SQLException: " + msg);
            if (msg.contains("duplicate")) {
                errors.setEmailRegextError(email + " is existed!");
                request.setAttribute("CREATE_ERROR", errors);
            }
        } catch (NamingException ex) {
            LOGGER.error("NamingException: " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.error("NoSuchAlgorithmException: " + ex.getMessage());
        } catch (MessagingException ex) {
            LOGGER.error("MessagingException: " + ex.getMessage());
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
