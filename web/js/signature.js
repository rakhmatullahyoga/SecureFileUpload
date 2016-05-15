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
  var f1 = document.form;
  var prvkey = f1.prvkey.value;
  var curve = "secp256r1";
  var sigalg = "SHA256withECDSA";
  var msg = f1.msg.value;
  
  console.log(msg);

  var sig = new KJUR.crypto.Signature({"alg": sigalg});
  sig.initSign({'ecprvhex': prvkey, 'eccurvename': curve});
  sig.updateString(msg);
  var sigValueHex = sig.sign();
  
  f1.sigval1.value = sigValueHex;
}

function doVerify() {
//  var f1 = document.form;
//  var pubkey = f1.pubkey.value;
//  var curve = f1.curve1.value;
//  var sigalg = f1.sigalg1.value;
//  var msg = f1.msg.value;
//  var sigval = f1.sigval1.value;
//
//  var sig = new KJUR.crypto.Signature({"alg": sigalg, "prov": "cryptojs/jsrsa"});
//  sig.initVerifyByPublicKey({'ecpubhex': pubkey, 'eccurvename': curve});
//  sig.updateString(msg);
//  var result = sig.verify(sigval);
//  if (result) {
//    alert("valid ECDSA signature");
//  } else {
//    alert("invalid ECDSA signature");
//  }
}