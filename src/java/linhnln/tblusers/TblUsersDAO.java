/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblusers;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;
import linhnln.utils.DBHelper;
import linhnln.utils.EncryptHelper;

/**
 *
 * @author Administrator
 */
public class TblUsersDAO implements Serializable {

    public TblUsersDTO checkLogin(String email, String password) throws SQLException, NamingException, NoSuchAlgorithmException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        TblUsersDTO dto = null;
        String encryptedPassword = EncryptHelper.hashPassword(password);
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Email, FullName, Passwrd, Rolee, Stt "
                    + "From tblUsers "
                    + "Where Email = ? And Passwrd = ?";
            smt = conn.prepareStatement(sql);
            smt.setString(1, email);
            smt.setString(2, encryptedPassword);
            rs = smt.executeQuery();
            if (rs.next()) {
                String fullName = rs.getString("FullName");
                String role = rs.getString("Rolee");
                String status = rs.getString("Stt");
                if (status.equals("Active")) {
                    dto = new TblUsersDTO(email, fullName, password, role, status);
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
        return dto;
    }

    public String getFullname(String email) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select FullName "
                    + "From tblUsers "
                    + "Where Email = ?";
            smt = conn.prepareStatement(sql);
            smt.setString(1, email);
            rs = smt.executeQuery();
            if (rs.next()) {
                String fullName = rs.getNString("FullName");
                return fullName;
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

    public Integer create(String email, String fullName, String password,
            String role, String stt) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Insert Into tblUsers(Email, FullName, Passwrd, "
                    + "Rolee, Stt) "
                    + "Values(?,?,?,?,?)";
            smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            smt.setString(1, email);
            smt.setNString(2, fullName);
            smt.setString(3, password);
            smt.setString(4, role);
            smt.setString(5, stt);
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

    public boolean changeStatusAfterConfirm(String userId) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement smt = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Update tblUsers "
                    + "Set Stt = ? "
                    + "Where Email = ?";
            smt = conn.prepareStatement(sql);
            smt.setString(1, "Active");
            smt.setString(2, userId);
            int row = smt.executeUpdate();
            if (row > 0) {
                return true;
            }
        } finally {
            if (smt != null) {
                smt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }
}
