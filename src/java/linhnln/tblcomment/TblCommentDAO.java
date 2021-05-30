/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblcomment;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.naming.NamingException;
import linhnln.utils.DBHelper;

/**
 *
 * @author Administrator
 */
public class TblCommentDAO implements Serializable {

    public Integer insertNewComment(String content, Timestamp date,
            String userId, int articleId) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Insert Into tblComment "
                    + "(Content, DateCreated, Stt, UserId, ArticleId) "
                    + "Values (?,?,?,?,?)";
            smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            smt.setNString(1, content);
            smt.setTimestamp(2, date);
            smt.setString(3, "Active");
            smt.setString(4, userId);
            smt.setInt(5, articleId);

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

    public boolean deleteComment(int commentId) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement smt = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Update tblComment "
                    + "Set Stt = ? "
                    + "Where Id = ?";
            smt = conn.prepareStatement(sql);
            smt.setString(1, "Deleted");
            smt.setInt(2, commentId);
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

    public boolean deleteCommentForDeleteArticle(int articleId) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement smt = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Update tblComment "
                    + "Set Stt = ? "
                    + "Where ArticleId = ?";
            smt = conn.prepareStatement(sql);
            smt.setString(1, "Deleted");
            smt.setInt(2, articleId);
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

    public String getUserIdOfComment(int commentId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select UserId "
                    + "From tblComment "
                    + "Where Id = ?";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, commentId);
            rs = smt.executeQuery();
            if (rs.next()) {
                String email = rs.getString("UserId");
                return email;
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
