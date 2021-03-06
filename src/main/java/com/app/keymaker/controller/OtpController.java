package com.app.keymaker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.app.keymaker.Block;
import com.app.keymaker.Transaction;
import com.app.keymaker.TransactionOutput;
import com.app.keymaker.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.keymaker.model.User;
import com.app.keymaker.model.UserRepository;
import com.app.keymaker.service.*;
import com.app.keymaker.utility.*;

@Controller
public class OtpController {
    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    int count1 = 0;
    String previous;
    Block currentBlock;
    public static int difficulty = 0;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OtpService otpService;

    @Autowired
    public EmailService myEmailService;

    @Autowired
    private UserRepository repository;

    @RequestMapping(value = "/generateOTP", method = RequestMethod.GET)
    public @ResponseBody String generateOtp(@RequestParam("email") String email) {
        try {
            String username = email;
            int otp = otpService.generateOTP(username);
            logger.info("OTP : " + otp);
            myEmailService.sendOtpMessage(username, "OTP - KeyMaker",
                    "UserName : " + username + " OTP : " + String.valueOf(otp));
            // repository.deleteAll();

            Query query = new Query();
            query.addCriteria(Criteria.where("email").is(username));
            Update update = new Update();
            update.set("otp", otp);
            boolean saveFlag = false;
            int count = 0;
            // repository.upsert(query, User.class);
            for (User user : repository.findAll()) {
                // System.out.println(user.getOTP());
                count++;
                if (String.valueOf(email).equalsIgnoreCase(user.getEmail())) {
                    repository.deleteById(user.getID());
                    logger.info("Updating Node : " + username + " " + otp);
                    repository.save(new User(username, String.valueOf(otp)));
                    saveFlag = false;
                    break;
                } else {
                    saveFlag = true;
                }
            }

            if (count == 0) {
                logger.info("Saving New Node : " + username + " " + otp);
                repository.save(new User(username, String.valueOf(otp)));
            } else {
                if (saveFlag == true) {
                    logger.info("Else Saving New Node : " + username + " " + otp);
                    repository.save(new User(username, String.valueOf(otp)));
                }
            }

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failure in sending OTP. Pelase contact the adminsitrator";
        }
    }

    @RequestMapping(value = "/validateOtp", method = RequestMethod.GET)
    public @ResponseBody String validateOtp(@RequestParam("otpnum") int otpnum) {
        final String SUCCESS = "Success";
        final String FAIL = "Unsuccessful";
        logger.info(" Otp Number : " + otpnum);

        for (User user : repository.findAll()) {
            System.out.println(user.getOTP());
            if (String.valueOf(otpnum).equalsIgnoreCase(user.getOTP())) {
                return SUCCESS + "," + generateAuthKey(user.getEmail(), user.getOTP());
            }
        }
        return FAIL;
    }

    public String generateAuthKey(String email, String otp) {

        Wallet customerWallet = new Wallet();
        String FAIL = "Fail";
        Block block;
        Wallet newWallet = new Wallet();

        KeyGenerator keyGen = new KeyGenerator();
        String generatedKey = keyGen.randomKeyGenerator();
        Map<String, String> newUserData = new HashMap<String, String>();
        newUserData.put(email, generatedKey);
        Transaction newTransaction = new Transaction(newWallet.publicKey, customerWallet.publicKey,
                (HashMap<String, String>) newUserData);
        newTransaction.generateSignature(newWallet.privateKey);
        newTransaction.transactionId = "0";
        newTransaction.outputs.add(new TransactionOutput(newTransaction.reciepient,
                (HashMap<String, String>) newTransaction.userData, newTransaction.transactionId));
        if (!newTransaction.verifiySignature())
            return FAIL;

        // System.out.println("COUNT VALUE : " + count1);
        if (count1 == 0) {
            block = new Block("0");
            currentBlock = block;
            // System.out.println("Current Hash : " + currentBlock.hash + " PreviousHash : "
            // + currentBlock.previousHash);
        } else {
            // System.out.println("Previous " + previous);
            block = new Block(previous);
            currentBlock = block;
            // System.out.println("else Current Hash : " + currentBlock.hash + "
            // PreviousHash : " + currentBlock.previousHash);
        }
        previous = currentBlock.hash;
        count1++;
        // System.out.println("Current Hash : " + block.hash + " PreviousHash : " +
        // block.previousHash);
        block.addTransaction(newTransaction);
        addBlock(currentBlock);

        if (isChainValid(currentBlock)) {
            return generatedKey;
        }
        return FAIL;
    }

    public static void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        blockchain.add(newBlock);
    }

    public static Boolean isChainValid(Block block) {
        Block currentBlock = block;
        Block previousBlock = block;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for (int i = 1; i < blockchain.size(); i++) {

            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);
            if (!previousBlock.hash.equals(currentBlock.previousHash)) {
                System.out.println("#Previous Hashes not equal");
                return false;
            }
            if (!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
                System.out.println("#This block hasn't been mined");
                return false;
            }

            for (int t = 0; t < currentBlock.transactions.size() - 1; t++) {
                Transaction currentTransaction = currentBlock.transactions.get(t);

                if (!currentTransaction.verifiySignature()) {
                    System.out.println("#Signature on Transaction(" + t + ") is Invalid");
                    return false;
                }

                if (currentTransaction.outputs.get(0).reciepient != currentTransaction.reciepient) {
                    System.out.println("#Transaction(" + t + ") output reciepient is not who it should be");
                    return false;
                }
                if (currentTransaction.outputs.get(1).reciepient != currentTransaction.sender) {
                    System.out.println("#Transaction(" + t + ") output 'change' is not sender.");
                    return false;
                }
            }
        }
        System.out.println("Blockchain is valid");
        return true;
    }
}