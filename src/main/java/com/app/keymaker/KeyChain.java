package com.app.keymaker;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class KeyChain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 0;
    public static Wallet walletuserA;
    public static Wallet walletuserB;
    public static Transaction occuringTransaction;


    public static void main(String[] args) {

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        walletuserA = new Wallet();
        walletuserB = new Wallet();
        Map<String, String> userAData = new HashMap<String, String>();
        Map<String, String> userAData1 = new HashMap<String, String>();
        userAData.put("userA@user@user.com", "A3628684A");
        userAData.put("userB@user@user.com", "B8768784B");

        System.out.println("**************************");
        System.out.println("Private and public keys:");
        System.out.println(Encrypt.getStringFromKey(walletuserA.privateKey));
        System.out.println(Encrypt.getStringFromKey(walletuserA.publicKey));
        System.out.println("**************************");
        System.out.println();

        occuringTransaction = new Transaction(walletuserA.publicKey, walletuserB.publicKey, (HashMap<String, String>) userAData);
        occuringTransaction.generateSignature(walletuserA.privateKey);

        occuringTransaction.transactionId = "0";
        occuringTransaction.outputs.add(new TransactionOutput(occuringTransaction.reciepient, (HashMap<String, String>) occuringTransaction.userData, occuringTransaction.transactionId));

        System.out.println("**************************");
        System.out.print("Is signature verified ---- ");
        System.out.println(occuringTransaction.verifiySignature());
        System.out.println("**************************");
        System.out.println();

        System.out.println("**************************");

        System.out.println("Creating and Mining  block... ");
        Block block = new Block("0");
        System.out.println("Starting Block");
        block.addTransaction(occuringTransaction);
        addBlock(block);


        Block block1 = new Block(block.hash);
        System.out.println("\nWalletA is Attempting to send funds userAData to WalletB...");
        block1.addTransaction(walletuserA.addTransactionToReceiver(walletuserB.publicKey, (HashMap<String, String>) userAData));
        addBlock(block1);

        Block block2 = new Block(block1.hash);
        System.out.println("\nWalletA Attempting to send more funds userAData1 than it has...");
        block2.addTransaction(walletuserA.addTransactionToReceiver(walletuserB.publicKey, (HashMap<String, String>)userAData1));
        addBlock(block2);

        isChainValid();

    }

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i=1; i < blockchain.size(); i++) {

            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                System.out.println("#Previous Hashes not equal");
                return false;
            }
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                System.out.println("#This block hasn't been mined");
                return false;
            }

            TransactionOutput tempOutput;
            for(int t=0; t <currentBlock.transactions.size()-1; t++) {
                Transaction currentTransaction = currentBlock.transactions.get(t);

                if(!currentTransaction.verifiySignature()) {
                    System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                    return false;
                }

                if( currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
                    System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
                    return false;
                }
                if( currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
                    System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
                    return false;
                }

            }

        }
        System.out.println("Blockchain is valid");
        return true;
    }
}