/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblarticle;

import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.naming.NamingException;
import linhnln.utils.DBHelper;

/**
 *
 * @author Administrator
 */
public class TblArticleDAO implements Serializable {

    public List<TblArticleDTO> listArticle;

    public List<TblArticleDTO> getListArticle() {
        return listArticle;
    }

    public void searchArticleByTitle(String searchValue, int rowsToSkip,
            int recordPerPage) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        String base64Img = "";
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Id, Title, Descript, Img, DateCreated, Stt, Email "
                    + "From tblArticle "
                    + "Where Stt = 'Active' And Title Like ? "
                    + "Order By DateCreated Desc Offset ? Rows Fetch Next ? Rows Only";
            smt = conn.prepareStatement(sql);
            smt.setNString(1, '%' + searchValue + '%');
            smt.setInt(2, rowsToSkip);
            smt.setInt(3, recordPerPage);
            rs = smt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String title = rs.getNString("Title");
                String description = rs.getNString("Descript");
                byte[] img = rs.getBytes("Img");
                Timestamp dateCreated = rs.getTimestamp("DateCreated");
                String stt = rs.getString("Stt");
                String email = rs.getString("Email");

                if (this.listArticle == null) {
                    this.listArticle = new ArrayList<>();
                }
                if (img != null) {
                    base64Img = Base64.getEncoder().encodeToString(img);
                }

                if (stt.equals("Active")) {
                    TblArticleDTO dto = new TblArticleDTO(id, title,
                            description, base64Img, dateCreated, stt, email);
                    this.listArticle.add(dto);
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

    public int getTotalRows(String searchValue) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        int totalRows = 0;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Count(Id) As numberOfRows "
                    + "From tblArticle "
                    + "Where Stt = 'Active' And Title Like ?";
            smt = conn.prepareStatement(sql);
            smt.setString(1, '%' + searchValue + '%');
            rs = smt.executeQuery();
            if (rs.next()) {
                totalRows = rs.getInt("numberOfRows");
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
        return totalRows;
    }

    public Integer insertNewArticle(String title, String description,
            InputStream img, Timestamp date, String stt, String email) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;

        try {
            conn = DBHelper.makeConnection();
            String sql = "Insert Into tblArticle "
                    + "(Title, Descript, Img, DateCreated, Stt, Email) "
                    + "Values (?,?,?,?,?,?)";
            smt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            smt.setNString(1, title);
            smt.setNString(2, description);
            smt.setBinaryStream(3, img);
            smt.setTimestamp(4, date);
            smt.setString(5, stt);
            smt.setString(6, email);
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

    public String getTitle(int articleId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Title "
                    + "From tblArticle "
                    + "Where Id = ?";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, articleId);
            rs = smt.executeQuery();
            if (rs.next()) {
                String title = rs.getNString("Title");
                return title;
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

    public String getUserId(int articleId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select Email "
                    + "From tblArticle "
                    + "Where Id = ?";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, articleId);
            rs = smt.executeQuery();
            if (rs.next()) {
                String title = rs.getString("Email");
                return title;
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

    public boolean deleteArticle(int articleId) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement smt = null;
        try {
            conn = DBHelper.makeConnection();
            String sql = "Update tblArticle "
                    + "Set Stt = ? "
                    + "Where Id = ?";
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
