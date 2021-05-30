/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblnotification;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import linhnln.utils.DBHelper;

/**
 *
 * @author Administrator
 */
public class TblNotificationDAO implements Serializable {

    public Integer insertNewNoti(String email, int articleId,
            Timestamp dateOfNoti, String typeOfAction, Integer actionId, String fromUserId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;

        try {
            conn = DBHelper.makeConnection();
            String sql = "Insert Into tblNotification "
                    + "(UserId, ArticleId, DateOfNoti, TypeOfAction, Stt, ActionId, FromUserId) "
                    + "Values(?, ?, ?, ?, ?, ?, ?)";
            smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            smt.setString(1, email);
            smt.setInt(2, articleId);
            smt.setTimestamp(3, dateOfNoti);
            smt.setString(4, typeOfAction);
            smt.setString(5, "Active");
            smt.setInt(6, actionId);
            smt.setString(7, fromUserId);
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

    private List<TblNotificationDTO> listNoti;

    public List<TblNotificationDTO> getListNoti() {
        return listNoti;
    }

    public void getNoti(int rows, String email) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;

        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Top (?) Id, UserId, ArticleId, DateOfNoti, "
                    + "TypeOfAction, Stt, ActionId, FromUserId "
                    + "From tblNotification "
                    + "Where UserId = ? "
                    + "Order By DateOfNoti Desc ";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, rows);
            smt.setString(2, email);
            rs = smt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                int articleId = rs.getInt("ArticleId");
                Timestamp dateOfNoti = rs.getTimestamp("DateOfNoti");
                String typeOfAction = rs.getString("TypeOfAction");
                String status = rs.getString("Stt");
                int actionId = rs.getInt("ActionId");
                String fromUserId = rs.getString("FromUserId");
                if (this.listNoti == null) {
                    this.listNoti = new ArrayList<>();
                }
                if (status.equals("Active")) {
                    TblNotificationDTO dto = new TblNotificationDTO(id, email, 
                        articleId, dateOfNoti, typeOfAction, status, actionId, fromUserId);
                    this.listNoti.add(dto);
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
    }
}
