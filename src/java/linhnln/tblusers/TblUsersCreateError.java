/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblusers;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class TblUsersCreateError implements Serializable {
    private String emailRegextError;
    private String passwordLengthError;
    private String confirmLengthError;
    private String fullnameLengthError;
    private String emailIsExisted;
    private String otpMatchError;
    
    //loi o ngoai chu ko phai o server nen ko co constructor

    public String getOtpMatchError() {
        return otpMatchError;
    }

    public void setOtpMatchError(String otpMatchError) {
        this.otpMatchError = otpMatchError;
    }
    
    

    public String getEmailRegextError() {
        return emailRegextError;
    }

    public void setEmailRegextError(String emailRegextError) {
        this.emailRegextError = emailRegextError;
    }

    public String getPasswordLengthError() {
        return passwordLengthError;
    }

    public void setPasswordLengthError(String passwordLengthError) {
        this.passwordLengthError = passwordLengthError;
    }

    public String getConfirmLengthError() {
        return confirmLengthError;
    }

    public void setConfirmLengthError(String confirmLengthError) {
        this.confirmLengthError = confirmLengthError;
    }

    public String getFullnameLengthError() {
        return fullnameLengthError;
    }

    public void setFullnameLengthError(String fullnameLengthError) {
        this.fullnameLengthError = fullnameLengthError;
    }

    public String getEmailIsExisted() {
        return emailIsExisted;
    }

    public void setEmailIsExisted(String emailIsExisted) {
        this.emailIsExisted = emailIsExisted;
    }

    
}
