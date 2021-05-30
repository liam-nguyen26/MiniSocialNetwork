<%-- 
    Document   : authDeleteCommentPage
    Created on : Sep 30, 2020, 2:31:13 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Comment Confirm</title>
    </head>
    <body>
        <c:if test="${empty sessionScope}">
            <c:redirect url="login.html"/>
        </c:if>

        <p style="color:red">welcome, ${sessionScope.USER.fullName}</p>

        <br>

        <p style="color:red">Are you sure to delete this comment?</p>

        <br>

        <c:url var="deleteCommentController" value="DispatchController">
            <c:param name="btAction" value="deleteComment"></c:param> 
            <c:param name="commentId" value="${param.commentId}"></c:param> 
            <c:param name="articleId" value="${param.articleId}"></c:param> 
        </c:url>

        <a href="${deleteCommentController}">Yes, I want to delete this comment</a>

        <br>

        <c:url var="goBackToDetailPage" value="DispatchController">
            <c:param name="btAction" value="displayDetail"></c:param> 
            <c:param name="articleId" value="${param.articleId}"></c:param> 
        </c:url>

        <a href="${goBackToDetailPage}">No, Go back</a>
    </body>
</html>
