/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.utils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrator
 */
public class AuthGmailHelper implements Serializable {

    public static boolean checkGmail(String email) {
        Pattern p = Pattern.compile("^[A-Za-z].*?@gmail\\.com$");
        //^: đánh dấu bắt đầu
        //A-Za-z: các chữ cái trong bảng
        //. : khớp bất kỳ ký tự nào trong []
        // *: xảy ra 0 hoặc nhiều lần
        //đuôi là gmail
        // \\. : dấu chấm
        //cuối chuỗi là com
        //$: kết thúc chuỗi

        Matcher m = p.matcher(email);
        return m.find();
    }
}
