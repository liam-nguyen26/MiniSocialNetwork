<%-- 
    Document   : noti
    Created on : Sep 24, 2020, 2:29:57 PM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notification Page</title>
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

        <c:set var="listNoti" value="${requestScope.LIST_NOTI}"/>
        <c:if test="${not empty listNoti}">
            <table border="1">
                <thead>
                    <tr>
                        <th>Title</th>
                        <th>Action</th>
                        <th>Date</th>
                    </tr>
                </thead>

                <tbody> 
                    <c:set var="Comment" value="Comment"/>
                    <c:set var="Emotion" value="Emotion"/>
                    <c:forEach var="noti" items="${listNoti}">
                        <tr>
                            <c:url var="detailArticle" value="DispatchController">
                                <c:param name="btAction" value="displayDetail"></c:param> 
                                <c:param name="articleId" value="${noti.articleId}"></c:param> 
                            </c:url>
                            
                            <td>
                                <a href="${detailArticle}">${noti.title}</a>
                            </td>
                            <td>
                                <c:if test="${noti.typeOfAction eq 'Comment'}">
                                    Commented by
                                </c:if>
                                <c:if test="${noti.typeOfAction eq 'Emotion'}">
                                    Reacted by
                                </c:if>
                                ${noti.fullName}
                            </td>
                            <td>
                                ${noti.dateOfNoti}
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </c:if>
        <c:if test="${empty listNoti}">
            No notification yet.
        </c:if>

    </body>
</html>
