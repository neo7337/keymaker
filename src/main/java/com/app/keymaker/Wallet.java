package com.app.keymaker;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.HashMap;

public class Wallet {

    public PrivateKey privateKey;
    public PublicKey publicKey;

    public Wallet(){
        generateKeyPair();
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG","SUN");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
            keyGen.initialize(112, random);   //256 bytes provides an acceptable security level
            KeyPair keyPair = keyGen.generateKeyPair();
            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Transaction addTransactionToReceiver(PublicKey recipient,HashMap<String, String> value ) {
        Transaction newTransaction = new Transaction(publicKey, recipient , value);
        newTransaction.generateSignature(privateKey);

        return newTransaction;
    }

}