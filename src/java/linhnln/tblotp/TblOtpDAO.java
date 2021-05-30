/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblotp;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;
import linhnln.utils.DBHelper;

/**
 *
 * @author Administrator
 */
public class TblOtpDAO implements Serializable {

    public Integer insertNewOtp(String otp, String userId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Insert Into tblOtp "
                    + "(Otp, UserId) "
                    + "Values (?,?)";
            smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            smt.setString(1, otp);
            smt.setString(2, userId);
            int row = smt.executeUpdate();
            if (row > 0) {
                rs = smt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (smt != null) {
                smt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    public String getOtpForThisUser(String userId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Otp "
                    + "From tblOtp "
                    + "Where UserId = ?";
            smt = conn.prepareStatement(sql);
            smt.setString(1, userId);
            rs = smt.executeQuery();
            if (rs.next()) {
                String otp = rs.getString("Otp");
                return otp;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (smt != null) {
                smt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }
}
