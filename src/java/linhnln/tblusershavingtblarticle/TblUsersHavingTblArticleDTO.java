/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblusershavingtblarticle;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class TblUsersHavingTblArticleDTO implements Serializable{
    private String title;
    private String description;
    private String base64Img;
    private String fullName;

    public TblUsersHavingTblArticleDTO() {
    }

    public TblUsersHavingTblArticleDTO(String title, String description, String base64Img, String FullName) {
        this.title = title;
        this.description = description;
        this.base64Img = base64Img;
        this.fullName = FullName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBase64Img() {
        return base64Img;
    }

    public void setBase64Img(String base64Img) {
        this.base64Img = base64Img;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String FullName) {
        this.fullName = FullName;
    }
    
    
    
    
}
