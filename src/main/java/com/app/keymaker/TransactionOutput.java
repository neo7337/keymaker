package com.app.keymaker;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

public class TransactionOutput {
    public String id;
    public PublicKey reciepient;
    public Map<String, String> userData;
    public String parentTransactionId;

    public TransactionOutput(PublicKey reciepient, HashMap<String,String> value, String parentTransactionId) {
        this.reciepient = reciepient;
        this.userData = value;
        this.parentTransactionId = parentTransactionId;
        this.id = Encrypt.applySha256(Encrypt.getStringFromKey(reciepient)+(userData.values())+parentTransactionId);
    }

    public boolean isMine(PublicKey publicKey) {
        return (publicKey == reciepient);
    }

}