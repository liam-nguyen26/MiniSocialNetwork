/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import linhnln.tblarticle.TblArticleDAO;
import linhnln.tblarticle.TblArticlePostLengthError;
import linhnln.tblusers.TblUsersDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
@MultipartConfig
public class PostArticleServlet extends HttpServlet {

    static final Logger LOGGER = Logger.getLogger(PostArticleServlet.class);
    private final String ERROR_PAGE = "upload.jsp";
    private final String SHOW_CONTROLLER = "DisplayingDetailServlet";

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
        
        
        HttpSession session = request.getSession(); //dang mo session roi ko lo

        String title = new String(request.getParameter("txtTitle")
                                            .getBytes("iso-8859-1"), "utf-8");

        String descript = new String(request.getParameter("txtDescription")
                                            .getBytes("iso-8859-1"), "utf-8");
        TblUsersDTO user = (TblUsersDTO) session.getAttribute("USER");
        String userId = user.getEmail();

        Part filePart = request.getPart("file");
        InputStream fileContent = filePart.getInputStream();

        String url = "";
        boolean foundError = false;
        TblArticlePostLengthError errors = new TblArticlePostLengthError();

        try {
            if (title.trim().isEmpty()) {
                foundError = true;
                errors.setTitleLengthError("You have to input your artile title");
            }
            if (descript.trim().isEmpty()) {
                foundError = true;
                errors.setDescriptionLengthError("You have to input your description article");
            }

            if (!(fileContent.available() == 0 || filePart.getContentType().startsWith("image"))) {
                foundError = true;
                errors.setImgFileError("You have to choose image file");
            }

            if (foundError) {
                url = ERROR_PAGE;
                request.setAttribute("POST_ERROR", errors);
            } else {
                TblArticleDAO dao = new TblArticleDAO();
                Timestamp currentDate = new Timestamp(System.currentTimeMillis());
                Integer result = dao.insertNewArticle(title, descript, fileContent, currentDate, "Active", userId);
                if (result != null) {
                    url = SHOW_CONTROLLER + "?articleId=" + result;
                }
            }

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
