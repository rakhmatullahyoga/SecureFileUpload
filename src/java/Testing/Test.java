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
            String publicKeyStr = "0425ccf4c7eb19202f21315a0a21227fc1e0e7a32ac3f67eb6739bb1c28a2010755b06a8e490fd4bfa90316841d588302f0d4715bc864d3dae202251bacce0921c";
            String signatureStr = "3045022071cedac5cf9819ad6a7f9ce9f49df18271963c890b19259f7684b40201d333f1022100a4512511b1533de503a148ac2c22573a3387df436a37d6108aa1e0a4655ebd6e";
            File file = new File("./upload/Total_Oil_Supplynew.csv");
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
