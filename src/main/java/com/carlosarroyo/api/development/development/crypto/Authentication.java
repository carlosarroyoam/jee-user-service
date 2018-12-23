/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crypto;

/**
 *
 * @author Carlos
 */
public class Authentication {

    // This should be updated every year or two.
    private static final UpdatableBCrypt bcrypt = new UpdatableBCrypt(11);

    public static String passwordHash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyHash(String password, String hash) {
        return bcrypt.verifyHash(password, hash);
    }

}
