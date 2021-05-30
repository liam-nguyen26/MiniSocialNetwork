/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
@MultipartConfig
public class DispatchController extends HttpServlet {

    private final String LOGIN_PAGE = "login.html";
    private final String INVALID_PAGE = "invalid.html";
    private final String LOGIN_CONTROLLER = "LoginServlet";
    private final String LOGOUT_CONTROLLER = "LogoutServlet";
    private final String CREATE_ACCOUNT_CONTROLLER = "CreateAccoutServlet";
    private final String FIRST_TIME_CONTROLLER = "FirstTimeServlet";
    private final String SEARCH_CONTROLLER = "SearchServlet";
    private final String POST_ARTICLE_CONTROLLER = "PostArticleServlet";
    private final String POST_ARTICLE_PAGE = "upload.jsp";
    private final String DISPLAY_DETAIL_CONTROLLER = "DisplayingDetailServlet";
    private final String INPUT_COMMENT_CONTROLLER = "InputCommentSerlvet";
    private final String PROCESS_EMOTION_CONTROLLER = "GetEmotionServlet";
    private final String GET_NOTI_CONTROLLER = "GetNotiServlet";
    private final String DELETE_COMMENT_CONTROLLER = "DeleteCommentServlet";
    private final String DELETE_ARTICLE_CONTROLLER = "DeleteArticleServlet";
    private final String DELETE_ARTICLE_CONFIRM_PAGE = "authDeletePage.jsp";
    private final String DELETE_COMMENT_CONFIRM_PAGE = "authDeleteCommentPage.jsp";
    private final String CONFIRM_ACCOUNT_CONTROLLER = "ConfirmAccountServlet";

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
        String button = request.getParameter("btAction");
        String url = "";
        try {
            if (button == null) {
                url = FIRST_TIME_CONTROLLER;
            } else if (button.equals("Login")) {
                url = LOGIN_CONTROLLER;
            } else if (button.equals("Create new account")) {
                url = CREATE_ACCOUNT_CONTROLLER;
            } else if (button.equals("Search")) {
                url = SEARCH_CONTROLLER;
            } else if (button.equals("Upload")) {
                url = POST_ARTICLE_CONTROLLER;
            } else if (button.equals("Logout")) {
                url = LOGOUT_CONTROLLER;
            } else if (button.equals("uploadPage")) {
                url = POST_ARTICLE_PAGE;
            } else if (button.equals("displayDetail")) {
                url = DISPLAY_DETAIL_CONTROLLER;
            } else if (button.equals("Comment")) {
                url = INPUT_COMMENT_CONTROLLER;
            } else if (button.equals("SearchPage")) {
                url = SEARCH_CONTROLLER;
            } else if (button.equals("Get Emotion Action")) {
                url = PROCESS_EMOTION_CONTROLLER;
            } else if (button.equals("notiPage")) {
                url = GET_NOTI_CONTROLLER;
            } else if (button.equals("deleteComment")) {
                url = DELETE_COMMENT_CONTROLLER;
            } else if (button.equals("deleteArticleConfirm")) {
                url = DELETE_ARTICLE_CONFIRM_PAGE;
            } else if (button.equals("deleteThisAritcle")) {
                url = DELETE_ARTICLE_CONTROLLER;
            } else if (button.equals("Confirm")) {
                url = CONFIRM_ACCOUNT_CONTROLLER;
            } else if (button.equals("loginPage")) {
                url = LOGIN_PAGE;
            } else if (button.equals("invalidPage")) {
                url = INVALID_PAGE;
            } else if (button.equals("deleteCommentConfirmPage")) {
                url = DELETE_COMMENT_CONFIRM_PAGE;
            }
  
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
