/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.tblcommenthavingtbluserstblarticle;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import linhnln.utils.DBHelper;

/**
 *
 * @author Administrator
 */
public class TblCommentHavingTblUsersTblArticleDAO implements Serializable {

    public List<TblCommentHavingTblUsersTblArticleDTO> listComment;

    public List<TblCommentHavingTblUsersTblArticleDTO> getListComment() {
        return listComment;
    }

    public void getComments(int articleId) throws NamingException, SQLException {
        Connection conn = null;
        PreparedStatement smt = null;
        ResultSet rs = null;
        
        try {
            conn = DBHelper.makeConnection();
            String sql = "Select U.FullName, C.Content, C.Stt, C.UserId, C.Id "
                    + "From tblComment C, tblArticle A, tblUsers U "
                    + "Where C.UserId = U.Email And "
                    + "C.ArticleId = A.Id And A.Id = ?";
            smt = conn.prepareStatement(sql);
            smt.setInt(1, articleId);
            rs = smt.executeQuery();
            while (rs.next()) {
                String fullName = rs.getString("FullName");
                String content = rs.getNString("Content");
                String stt = rs.getString("Stt");
                if (this.listComment == null) {
                    this.listComment = new ArrayList<>();
                }
                String userId = rs.getString("UserId");
                int commentId = rs.getInt("Id");
                if (stt.equals("Active")) {
                    TblCommentHavingTblUsersTblArticleDTO dto = 
                    new TblCommentHavingTblUsersTblArticleDTO(fullName, content, commentId, userId);
                    this.listComment.add(dto);
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
