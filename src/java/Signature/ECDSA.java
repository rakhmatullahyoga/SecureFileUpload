/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Signature;

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
    private static final String SIGN_ALGO = "SHA256withECDSA";
    private static final String CURVE = "prime256v1";
    private static final String KEY_ALGO = "ECDSA";
    
    public static boolean isValidSignature(byte[] pubKey, byte[] message,
            byte[] signature) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, SignatureException, InvalidKeySpecException {
        Signature ecdsaVerify = Signature.getInstance(SIGN_ALGO, new BouncyCastleProvider());
        ecdsaVerify.initVerify(getPublicKeyFromHex(pubKey));
        ecdsaVerify.update(message);
        return ecdsaVerify.verify(signature);
    }
    
    public static PublicKey getPublicKeyFromHex(byte[] pubKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec(CURVE);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGO, new BouncyCastleProvider());
        ECNamedCurveSpec params = new ECNamedCurveSpec(CURVE, spec.getCurve(), spec.getG(), spec.getN());
        ECPoint point =  ECPointUtil.decodePoint(params.getCurve(), pubKey);
        ECPublicKeySpec pubKeySpec = new ECPublicKeySpec(point, params);
        ECPublicKey pk = (ECPublicKey) kf.generatePublic(pubKeySpec);
        return pk;
    }
}
