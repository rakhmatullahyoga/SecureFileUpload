/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import Signature.ECDSA;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
            String publicKeyStr = "04da9399786008e4315cbd794d21e9b05562238f7fe02c7eeec15f560335886c95045de63b74d56b834306c31c25655e2396bdccd7d427303980b4bc40f64896fb";
            String signatureStr = "3046022100ca7dd07a17114fefca5fe4754fc1325f5340d07146084273d19cacddc96dcdf7022100d209e607896533434108bf8c62c15a90d0b1852cbc46d047d1e2e4d9f3429266";
            File file = new File("./upload/15834.jpg");
            byte[] data = new byte[(int)file.length()];
            FileInputStream in = new FileInputStream(file);
            in.read(data);
            boolean verified = ECDSA.isValidSignature(DatatypeConverter.parseHexBinary(publicKeyStr), data, DatatypeConverter.parseHexBinary(signatureStr));
            
            System.out.println(verified);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException | InvalidKeySpecException | UnsupportedEncodingException ex) {
            System.out.println("Verification failed!");
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
