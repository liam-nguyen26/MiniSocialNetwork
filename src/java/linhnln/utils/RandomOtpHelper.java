/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package linhnln.utils;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Administrator
 */
public class RandomOtpHelper implements Serializable {

    public static String getRandomNumberString() {
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);
    }
}
