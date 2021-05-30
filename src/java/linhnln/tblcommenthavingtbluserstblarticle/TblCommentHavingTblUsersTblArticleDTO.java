/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblcommenthavingtbluserstblarticle;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class TblCommentHavingTblUsersTblArticleDTO implements Serializable {
    private String fullName;
    private String content;
    private int commentId;
    private String userId;
    private String stt;
    
    public TblCommentHavingTblUsersTblArticleDTO() {
    }

    public TblCommentHavingTblUsersTblArticleDTO(String fullName, String content) {
        this.fullName = fullName;
        this.content = content;
    }

    public TblCommentHavingTblUsersTblArticleDTO(String fullName, String content, int commentId, String userId) {
        this.fullName = fullName;
        this.content = content;
        this.commentId = commentId;
        this.userId = userId;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }
    

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
