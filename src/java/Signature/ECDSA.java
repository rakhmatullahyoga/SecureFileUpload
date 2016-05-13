/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Signature;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.ECPointUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;

/**
 *
 * @author Rakhmatullah Yoga S
 */
public class ECDSA {
    public static boolean isValidSignature(byte[] pubKey, byte[] message,
            byte[] signature) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        Signature ecdsaVerify = Signature.getInstance("SHA1withECDSA", new BouncyCastleProvider());
        ecdsaVerify.initVerify(getPublicKeyFromHex(pubKey));
        ecdsaVerify.update(message);
        return ecdsaVerify.verify(signature);
    }
    
    public static PublicKey getPublicKeyFromHex(byte[] pubKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("prime256v1");
        KeyFactory kf = KeyFactory.getInstance("ECDSA", new BouncyCastleProvider());
        ECNamedCurveSpec params = new ECNamedCurveSpec("prime256v1", spec.getCurve(), spec.getG(), spec.getN());
        ECPoint point =  ECPointUtil.decodePoint(params.getCurve(), pubKey);
        ECPublicKeySpec pubKeySpec = new ECPublicKeySpec(point, params);
        ECPublicKey pk = (ECPublicKey) kf.generatePublic(pubKeySpec);
        return pk;
    }

    public static void main(String[] args) {
        try {
            String message = "aaa";
            String publicKeyStr = "04423a7a5b5040f702719efe38a62a266105864f5d0e3abd5252cc6482ca098320f8008f92c8181be470d79f8c01b47ca12c32d4381ecd994daaa1a589432bfe64";
            String signatureStr = "3044022073eed6324d2c842781c5a883aaa55c7c56ef990b84d4f02fb331f559b10ceca102201f5daa665a657ec0b73b9f60b7995773ace150927f3584f729ae96deac945c29";
            boolean verified = isValidSignature(DatatypeConverter.parseHexBinary(publicKeyStr), message.getBytes("UTF-8"), DatatypeConverter.parseHexBinary(signatureStr));
            
            System.out.println(verified);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException | InvalidKeySpecException | UnsupportedEncodingException ex) {
            System.out.println("Verification failed!");
            Logger.getLogger(ECDSA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
