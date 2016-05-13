/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Signature.ECDSA;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;


/**
 *
 * @author Rakhmatullah Yoga S
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String message = "aaa";
            String publicKeyStr = "04423a7a5b5040f702719efe38a62a266105864f5d0e3abd5252cc6482ca098320f8008f92c8181be470d79f8c01b47ca12c32d4381ecd994daaa1a589432bfe64";
            String signatureStr = "3044022073eed6324d2c842781c5a883aaa55c7c56ef990b84d4f02fb331f559b10ceca102201f5daa665a657ec0b73b9f60b7995773ace150927f3584f729ae96deac945c29";
            boolean verified = ECDSA.isValidSignature(DatatypeConverter.parseHexBinary(publicKeyStr), message.getBytes("UTF-8"), DatatypeConverter.parseHexBinary(signatureStr));
            
            System.out.println(verified);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException | InvalidKeySpecException | UnsupportedEncodingException ex) {
            System.out.println("Verification failed!");
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
