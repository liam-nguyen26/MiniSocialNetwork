/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblnotification;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Administrator
 */
public class TblNotificationDTO implements Serializable {

    private int id;
    private String email;
    private int articleId;
    private Timestamp dateOfNoti;
    private String typeOfAction;
    private String status;
    private int actionId;
    private String fromUserId;
    
    public TblNotificationDTO() {
    }

    public TblNotificationDTO(int id, String email, int articleId, Timestamp dateOfNoti, String typeOfAction, String status, int actionId, String fromUserId) {
        this.id = id;
        this.email = email;
        this.articleId = articleId;
        this.dateOfNoti = dateOfNoti;
        this.typeOfAction = typeOfAction;
        this.status = status;
        this.actionId = actionId;
        this.fromUserId = fromUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }
    

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Timestamp getDateOfNoti() {
        return dateOfNoti;
    }

    public void setDateOfNoti(Timestamp dateOfNoti) {
        this.dateOfNoti = dateOfNoti;
    }

    public String getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(String typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
