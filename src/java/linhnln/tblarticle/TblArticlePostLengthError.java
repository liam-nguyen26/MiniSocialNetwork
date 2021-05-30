/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblarticle;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class TblArticlePostLengthError implements Serializable {
    private String titleLengthError;
    private String descriptionLengthError;
    private String imgFileError;

    public String getTitleLengthError() {
        return titleLengthError;
    }

    public void setTitleLengthError(String titleLengthError) {
        this.titleLengthError = titleLengthError;
    }

    public String getDescriptionLengthError() {
        return descriptionLengthError;
    }

    public void setDescriptionLengthError(String descriptionLengthError) {
        this.descriptionLengthError = descriptionLengthError;
    }

    public String getImgFileError() {
        return imgFileError;
    }

    public void setImgFileError(String imgFileError) {
        this.imgFileError = imgFileError;
    }
    
    
}
