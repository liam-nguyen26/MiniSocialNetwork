/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblemotion;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import javax.naming.NamingException;
import linhnln.utils.DBHelper;

/**
 *
 * @author Administrator
 */
public class TblEmotionDAO implements Serializable {

    public int getLikeEmotion(int articleId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        int totalLikes = 0;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Sum(Case When Emotion = 1 "
                    + "Then 1 Else 0 End) As TrueCount "
                    + "From tblEmotion "
                    + "Where ArticleId = ? And Stt = ?";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, articleId);
            smt.setString(2, "Active");
            rs = smt.executeQuery();
            if (rs.next()) {
                totalLikes = rs.getInt("TrueCount");
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
        return totalLikes;

    }

    public int getDislikeEmotion(int articleId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        int totalDislikes = 0;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Sum(Case When Emotion = 0 "
                    + "Then 1 Else 0 End) As FalseCount "
                    + "From tblEmotion "
                    + "Where ArticleId = ? And Stt = ?";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, articleId);
            smt.setString(2, "Active");
            rs = smt.executeQuery();
            if (rs.next()) {
                totalDislikes = rs.getInt("FalseCount");
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
        return totalDislikes;
    }

    public TblEmotionDTO checkEmotionIsExisted(String userId, int articleId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        TblEmotionDTO dto = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Id, UserId, ArticleId, Emotion "
                    + "From tblEmotion "
                    + "Where UserId = ? and ArticleId = ? And Stt = ?";
            smt = conn.prepareStatement(sql);
            smt.setString(1, userId);
            smt.setInt(2, articleId);
            smt.setString(3, "Active");
            rs = smt.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("Id");
                Boolean emotion = (Boolean) rs.getObject("Emotion");
                dto = new TblEmotionDTO(id, userId, articleId, emotion);
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

    public Integer insertNewEmotion(String userId, int articleId, boolean emotion) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Insert Into tblEmotion "
                    + "(UserId, ArticleId, Emotion, Stt) "
                    + "Values (?,?,?,?)";
            smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            smt.setString(1, userId);
            smt.setInt(2, articleId);
            smt.setBoolean(3, emotion);
            smt.setString(4, "Active");
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

    public boolean updateEmotion(String userId, int articleId, Boolean emotion) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Update tblEmotion "
                    + "Set Emotion = ? "
                    + "Where UserId = ? And ArticleId = ?";
            smt = conn.prepareStatement(sql);
            if (emotion == null) {
                smt.setNull(1, Types.BOOLEAN);
            } else {
                smt.setBoolean(1, emotion);
            }
            smt.setString(2, userId);
            smt.setInt(3, articleId);
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

    public boolean deleteEmotionForDeleteArticle(int articleId) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement smt = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Update tblEmotion "
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

}
