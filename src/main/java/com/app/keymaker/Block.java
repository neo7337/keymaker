package com.app.keymaker;

import java.util.ArrayList;
import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    public String blockchainStructure;
    private long timeStamp;
    private int tmp;

    public Block(String previousHash ) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedhash = Encrypt.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(tmp) +
                        blockchainStructure
        );
        return calculatedhash;
    }

    public void mineBlock(int difficulty) {
        blockchainStructure = Encrypt.getStructure(transactions);
        String target = Encrypt.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            tmp ++;
            hash = calculateHash();
        }
        System.out.println();
    }

    public boolean addTransaction(Transaction transaction) {
        if(transaction == null) return false;
        if((previousHash != "0")) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}
