<%-- 
    Document   : search
    Created on : Sep 17, 2020, 8:13:04 AM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <c:if test="${empty sessionScope}">
            <c:redirect url="login.html"/>
        </c:if>

        <font color="red">
            welcome, ${sessionScope.USER.fullName}
        </font>
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

        <form action="DispatchController" method="POST">
            Search: <input type="text" name="txtSearchValue" value="" />
            <input type="submit" value="Search" name="btAction"/>
        </form>

        <c:set var="searchValue" value="${param.txtSearchValue}"/>

        <c:if test="${not empty searchValue}">
            <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
            <c:set var="noOfPages" value="${requestScope.TOTAL_PAGES}"/>
            <c:if test="${not empty result}">
                <table border="1" class="table table-hover">
                    <thead>
                        <tr>                            
                            <th>Title</th>
                            <th>Description</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" items="${result}">
                            <tr>
                                <c:url var="detailLink" value="DispatchController">
                                    <c:param name="lastSearchValue" value="${searchValue}"></c:param> 
                                    <c:param name="articleId" value="${dto.id}"></c:param> 
                                    <c:param name="btAction" value="displayDetail"></c:param> 
                                </c:url>
                                <td><a href="${detailLink}">${dto.title}</a></td>
                                <td>
                                    ${fn:substring(dto.description, 0, 100)}...
                                    <br>
                                    <img src="data:image/png;base64,${dto.basse64Image}" width="100" height="50" alt="imgg"/>
                                    <br>
                                    ${basse64Image}
                                </td>
                                <td>${dto.dateCreated}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <c:forEach begin="1" end="${noOfPages}" var="i">
                    <c:set var="currentPageNo" value="${i}"/>
                    <c:url var="nextPageLink" value="GetNextPageServlet">
                        <c:param name="currentPage" value="${currentPageNo}"></c:param> 
                        <c:param name="txtSearchValue" value="${searchValue}"></c:param>
                    </c:url>
                    <ul class="pagination">                
                        <li><a href="${nextPageLink}">${i}</a></li>
                    </ul>
                </c:forEach>
            </c:if>
        </c:if>



    </body>
</html>
