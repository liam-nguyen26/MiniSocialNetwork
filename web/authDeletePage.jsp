<%-- 
    Document   : authDeletePage
    Created on : Sep 26, 2020, 10:48:46 AM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm delete article page</title>
    </head>
    <body>
        <c:if test="${empty sessionScope}">
            <c:redirect url="login.html"/>
        </c:if>

        <p style="color:red">welcome, ${sessionScope.USER.fullName}</p>

        <br>
            
        <p style="color:red">Are you sure to delete this Article?</p>
        
        <br>

        <c:url var="deleteArticleController" value="DispatchController">
            <c:param name="btAction" value="deleteThisAritcle"></c:param> 
            <c:param name="articleId" value="${param.articleId}"></c:param> 
        </c:url>

        <a href="${deleteArticleController}">Yes, I want to delete this post</a>

        <br>

        <c:url var="goBackToDetailPage" value="DispatchController">
            <c:param name="btAction" value="displayDetail"></c:param> 
            <c:param name="articleId" value="${param.articleId}"></c:param> 
        </c:url>

        <a href="${goBackToDetailPage}">No, Go back</a>

    </body>
</html>
