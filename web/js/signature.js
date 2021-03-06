/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function doGenerate() {
    var f1 = document.form;
    var curve = "secp256r1";
    var ec = new KJUR.crypto.ECDSA({"curve": curve});
    var keypair = ec.generateKeyPairHex();

    f1.prvkey.value = keypair.ecprvhex;
    f1.pubkey.value = keypair.ecpubhex;
}

function doSign() {
    var start = new Date().getTime();
    var f1 = document.form;
    var prvkey = f1.prvkey.value;
    var curve = "secp256r1";
    var sigalg = "SHA1withECDSA";
    var msg = f1.msg.value;
    var sig = new KJUR.crypto.Signature({"alg": sigalg});

    sig.initSign({'ecprvhex': prvkey, 'eccurvename': curve});
    sig.updateHex(msg);
    var sigValueHex = sig.sign();
    var end = new Date().getTime();
    var time = end - start;
    console.log("Signing duration : "+time);
    f1.sigval1.value = sigValueHex;
}