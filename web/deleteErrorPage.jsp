<%-- 
    Document   : deleteErrorPage
    Created on : Sep 28, 2020, 9:35:13 AM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Error Page</title>
    </head>
    <body>
        <c:if test="${empty sessionScope}">
            <c:redirect url="login.html"/>
        </c:if>
        <font color = "red"> 
        you are not allow to delete this post
        <br>
        </font>
        <c:url var="detailPost" value="DispatchController">
            <c:param name="btAction" value="displayDetail"></c:param> 
            <c:param name="articleId" value="${requestScope.POST_ID}"></c:param> 
        </c:url>  
        <a href="${detailPost}">Click here to go back</a>
    </body>
</html>
