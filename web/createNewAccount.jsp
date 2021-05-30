<%-- 
    Document   : createNewAccount
    Created on : Jul 11, 2020, 8:41:13 PM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Account</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h1>Create new Account</h1>
        <form action="DispatchController" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERROR}"/>
            Email* <input type="text" name="txtEmail" 
                          value="${param.txtEmail}" />  <br>

            <c:if test="${not empty errors.emailRegextError}">
                <font color="red">
                ${errors.emailRegextError}
                </font><br>
            </c:if>

            <c:if test="${not empty errors.emailIsExisted}">
                <font color="red">
                ${errors.emailIsExisted}
                </font><br>
            </c:if>

            Password* <input type="password" name="txtPassword" value="" /><br>
            <c:if test="${not empty errors.passwordLengthError}">
                <font color="red">
                ${errors.passwordLengthError}
                </font><br>
            </c:if>

            confirm* <input type="password" name="txtConfirm" value="" />   <br>        
            <c:if test="${not empty errors.confirmLengthError}">
                <font color="red">
                ${errors.confirmLengthError}
                </font><br>
            </c:if>

            Full name <input type="text" name="txtFullname" 
                             value="${param.txtFullname}" />     <br>            
            <c:if test="${not empty errors.fullnameLengthError}">
                <font color="red">
                ${errors.fullnameLengthError}
                </font><br>
            </c:if>

            <input type="submit" value="Create new account" name="btAction" />
            <input type="reset" value="reset" />
        </form>
        <a href="login.html">Click here to go to Login page</a>
    </body>
</html>
