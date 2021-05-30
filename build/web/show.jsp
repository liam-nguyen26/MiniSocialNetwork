<%-- 
    Document   : show
    Created on : Sep 21, 2020, 5:01:24 PM
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail page</title>
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
        <a href="SearchServlet?txtSearchValue=${lastSearchValue}">Go back to search page</a>

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


        <c:set var="post" value="${requestScope.POST}"/>
        <c:set var="totalLikes" value="${requestScope.LIKE_EMO}"/>
        <c:set var="totalDislikes" value="${requestScope.DIS_LIKE_EMO}"/>
        <c:set var="listComment" value="${requestScope.COMMENT_LIST}"/>
        <c:set var="postID" value="${requestScope.ARTICLE_ID}"/>

        <h2>Title: ${post.title}</h2>
        <br>
        
        <c:url var="deleteArticle" value="DispatchController">
            <c:param name="btAction" value="deleteArticleConfirm"></c:param> 
            <c:param name="articleId" value="${postID}"></c:param> 
        </c:url>  
        
        <a href="${deleteArticle}">Delete Article</a>
        
        <br>
        <h4>Created by: <u>${post.fullName}</u> </h4>
        <br>

        <p>${post.description}</p>
        <br> 
        <img src="data:image/png;base64,${post.base64Img}" width="800" height="600"/>
        <br>

        <c:url var="likeAction" value="DispatchController">
            <c:param name="btAction" value="Get Emotion Action"></c:param> 
            <c:param name="articleId" value="${postID}"></c:param> 
            <c:param name="emotion" value="Like"></c:param> 
        </c:url>   

        <c:url var="dislikeAction" value="DispatchController">
            <c:param name="btAction" value="Get Emotion Action"></c:param> 
            <c:param name="articleId" value="${postID}"></c:param> 
            <c:param name="emotion" value="Dislike"></c:param> 
        </c:url>   

        <a href="${likeAction}">Likes</a>: : ${totalLikes} ---------  
        <a href="${dislikeAction}">Dislike</a>: ${totalDislikes}

        <br>
        <form action="DispatchController" method="POST">
            <label for="commentContent">Review of W3Schools:</label>
            <textarea id="commentContent" name="txtComment" rows="4" cols="30"></textarea>
            <br>
            <input type="hidden" name="articleId" value="${postID}" />
            <input type="hidden" name="lastSearchValue" value="${lastSearchValue}" />
            <input type="submit" value="Comment" name="btAction"/>
        </form>
        <br>
        <c:if test="${not empty listComment}">
            <table border="1">
                <thead>
                    <tr>
                        <th>FullName</th>
                        <th>Comment Content</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    
                    <c:forEach var="comment" items="${listComment}">
                        <tr>
                            <td>${comment.fullName}</td>
                            <td>${comment.content}</td>
                            <c:url var="deleteCommentLink" value="DispatchController">
                                <c:param name="btAction" value="deleteCommentConfirmPage"></c:param> 
                                <c:param name="commentId" value="${comment.commentId}"></c:param> 
                                <c:param name="articleId" value="${postID}"></c:param> 
                            </c:url>   
                            <td>
                                <a href="${deleteCommentLink}">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

    </body>
</html>
