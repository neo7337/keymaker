package com.app.keymaker;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Transaction {

    public String transactionId;
    public PublicKey sender;
    public PublicKey reciepient;
    public Map<String, String> userData;
    public byte[] signature;

    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0;

    public Transaction(PublicKey from, PublicKey to, HashMap<String, String> userData) {
        this.sender = from;
        this.reciepient = to;
        this.userData = userData;
    }

    private String calulateHash() {
        sequence++;
        return Encrypt.applySha256(
                Encrypt.getStringFromKey(sender) + Encrypt.getStringFromKey(reciepient) + userData.values() + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = Encrypt.getStringFromKey(sender) + Encrypt.getStringFromKey(reciepient) + userData.values();
        signature = Encrypt.applyECSig(privateKey,data);
    }

    public boolean verifiySignature() {
        String data = Encrypt.getStringFromKey(sender) + Encrypt.getStringFromKey(reciepient) + userData.values();
        return Encrypt.verifyECSig(sender, data, signature);
    }

    public boolean processTransaction() {

        if(verifiySignature() == false) {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        transactionId = calulateHash();
        outputs.add(new TransactionOutput( this.reciepient, (HashMap<String, String>)userData,transactionId));

        return true;
    }

}