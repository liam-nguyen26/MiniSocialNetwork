<%-- 
    Document   : deleteCommentError
    Created on : Sep 28, 2020, 3:25:20 PM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete comment error</title>
    </head>
    <body>
        <c:if test="${empty sessionScope}">
            <c:redirect url="login.html"/>
        </c:if>
        <font color = "red"> 
        you are not allow to delete
        <br>
        </font>
        <c:url var="detailPost" value="DispatchController">
            <c:param name="btAction" value="displayDetail"></c:param> 
            <c:param name="articleId" value="${requestScope.POST_ID}"></c:param> 
        </c:url>  
        <a href="${detailPost}">Click here to go back</a>
    </body>
</html>
