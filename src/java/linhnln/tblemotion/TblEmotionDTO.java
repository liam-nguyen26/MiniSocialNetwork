/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblemotion;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class TblEmotionDTO implements Serializable {
    private int id;
    private String userId;
    private int articleId; 
    private Boolean emotion;
    
    public TblEmotionDTO() {
    }

    public TblEmotionDTO(int id, String userId, int articleId, Boolean emotion) {
        this.id = id;
        this.userId = userId;
        this.articleId = articleId;
        this.emotion = emotion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Boolean getEmotion() {
        return emotion;
    }

    public void setEmotion(Boolean emotion) {
        this.emotion = emotion;
    }
    
}
