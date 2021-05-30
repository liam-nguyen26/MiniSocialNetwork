/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblarticle;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Administrator
 */
public class TblArticleDTO implements Serializable {
    private int id;
    private String title; 
    private String description; 
    private String basse64Image; 
    private Timestamp dateCreated; 
    private String stt;
    private String email;

    public TblArticleDTO() {
    }

    public TblArticleDTO(int id, String title, String description, String basse64Image, Timestamp dateCreated, String stt, String email) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.basse64Image = basse64Image;
        this.dateCreated = dateCreated;
        this.stt = stt;
        this.email = email;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }
    
    

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getBasse64Image() {
        return basse64Image;
    }

    public void setBasse64Image(String basse64Image) {
        this.basse64Image = basse64Image;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    

    
    
    
    
    
    
}
