/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblnotificationhavingtblusers;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Administrator
 */
public class TblNotificationHavingTblUsersDTO implements Serializable {
    private int id;
    private String email;
    private int articleId;
    private Timestamp dateOfNoti;
    private String typeOfAction;
    private String status;
    private int actionId;
    private String title;
    private String fullName;

    public TblNotificationHavingTblUsersDTO() {
    }


    public TblNotificationHavingTblUsersDTO(int articleId, Timestamp dateOfNoti, String typeOfAction, String title, String fullName) {
        this.articleId = articleId;
        this.dateOfNoti = dateOfNoti;
        this.typeOfAction = typeOfAction;
        this.title = title;
        this.fullName = fullName;
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

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    
}
