<%-- 
    Document   : confirmGmail
    Created on : Sep 26, 2020, 5:09:50 PM
    Author     : Administrator
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirm gmail page</title>
    </head>
    <body>
        <form action="DispatchController" method="POST">
            Input your verification code from your gmail: <input type="text" name="txtOtp" value="" />
            <c:set var="otpError" value="${requestScope.OTP_ERROR}"/>
            <c:if test="${not empty otpError}">
                <br>
                <font color="red">
                ${otpError.otpMatchError}
                </font>
                <input type="hidden" name="txtEmail" value="${requestScope.USER_ID}" />
            </c:if>
            <br>
            <input type="hidden" name="txtEmail" value="${requestScope.USER_ID}" />            
            <input type="submit" value="Confirm" name="btAction" />
            <br> 
        </form>

    </body>
</html>
