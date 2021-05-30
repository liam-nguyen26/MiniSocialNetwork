/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


/**
 *
 * @author Administrator
 */
public class EncodingEmailHelper implements Serializable {

    public static String encodeEmail(String email) throws UnsupportedEncodingException {
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8.toString());
        return encodedEmail;
    }

    public static String decodeEmail(String encodedBytes) throws UnsupportedEncodingException {
        String decodeEmail = URLDecoder.decode(encodedBytes, StandardCharsets.UTF_8.toString());
        return decodeEmail;
    }


}
