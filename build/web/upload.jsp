<%-- 
    Document   : upload
    Created on : Sep 21, 2020, 3:16:21 PM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Upload Page</title>
    </head>
    <body>
        <c:if test="${empty sessionScope}">
            <c:redirect url="login.html"/>
        </c:if>

        <font color="red">
        welcome, ${sessionScope.USER.fullName}
        </font>
        <br>

        <c:set var="lastSearchValue" value="${param.lastSearchValue}"/>
        <c:url var="searchPage" value="DispatchController">
            <c:param name="btAction" value="SearchPage"></c:param> 
            <c:param name="txtSearchValue" value="${param.lastSearchValue}"></c:param> 
        </c:url>
        <a href="${searchPage}">Go back to search page</a>

        <br>
        <c:url var="logout" value="DispatchController">
            <c:param name="btAction" value="Logout"></c:param> 
        </c:url>

        <a href="${logout}">Logout</a>

        <br>
        <c:url var="uploadPage" value="DispatchController">
            <c:param name="btAction" value="uploadPage"></c:param> 
        </c:url>   

        <a href="${uploadPage}">Post Article</a>

        <br>
        <c:url var="notificationPage" value="DispatchController">
            <c:param name="btAction" value="notiPage"></c:param> 
        </c:url>  

        <a href="${notificationPage}">Notification page</a>

        <form action="DispatchController" method="post" enctype="multipart/form-data">
            <c:set var="errors" value="${requestScope.POST_ERROR}"/>
            Title: <input type="text" name="txtTitle" value="" /> <br>
            <c:if test="${not empty errors.titleLengthError}">
                <font color="red">
                ${errors.titleLengthError}
                </font><br>
            </c:if>
            Description: <input type="text" name="txtDescription" value="" /> <br>
            <c:if test="${not empty errors.descriptionLengthError}">
                <font color="red">
                ${errors.descriptionLengthError}
                </font><br>
            </c:if>
            Please choose your image file: <br>
            <c:if test="${not empty errors.imgFileError}">
                <font color="red">
                ${errors.imgFileError}
                </font><br>
            </c:if>
            <input type="file" name="file" />
            <input type="submit" value="Upload" name="btAction" />
        </form>



    </body>
</html>
