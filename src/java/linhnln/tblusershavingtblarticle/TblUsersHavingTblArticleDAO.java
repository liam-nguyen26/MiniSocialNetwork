/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblusershavingtblarticle;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import javax.naming.NamingException;
import linhnln.utils.DBHelper;

/**
 *
 * @author Administrator
 */
public class TblUsersHavingTblArticleDAO implements Serializable {

    //chắc chắn active rồi mới hiện trong data grid, qua đây ko cần check nữa
    public TblUsersHavingTblArticleDTO getAritcleDetailWithUserName(int articleId) throws SQLException, NamingException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        TblUsersHavingTblArticleDTO dto = null;
        String base64Img = "";
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select tblArticle.Title, tblArticle.Descript, "
                    + "tblArticle.Img, tblUsers.FullName "
                    + "From tblUsers, tblArticle "
                    + "Where tblUsers.Email = tblArticle.Email "
                    + "And tblArticle.Id = ? And tblArticle.Stt = ?";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, articleId);
            smt.setString(2, "Active");
            rs = smt.executeQuery();
            if (rs.next()) {
                String title = rs.getNString("Title");
                String description = rs.getNString("Descript");
                byte[] img = rs.getBytes("Img");
                String fullName = rs.getNString("FullName");
                if (img != null) {
                    base64Img = Base64.getEncoder().encodeToString(img);
                }
                dto = new TblUsersHavingTblArticleDTO(title, description, base64Img, fullName);
                
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

}
