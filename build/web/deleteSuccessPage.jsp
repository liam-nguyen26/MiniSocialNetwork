<%-- 
    Document   : deleteSuccessPage
    Created on : Sep 26, 2020, 11:50:01 AM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Delete Successfully</title>
    </head>
    <body>
        <c:if test="${empty sessionScope}">
            <c:redirect url="login.html"/>
        </c:if>
        You have delete this article successfully
        <br>

        <a href="search.jsp">click here to go to search page</a>
    </body>
</html>
