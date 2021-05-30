/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblotp;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class TblOtpDTO implements Serializable {
    private int id;
    private String otp;
    private String userId;

    public TblOtpDTO() {
    }

    public TblOtpDTO(int id, String otp, String userId) {
        this.id = id;
        this.otp = otp;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
    
}
