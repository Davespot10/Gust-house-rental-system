package com.example.mppproject.utility;

import java.util.Random;

public class RandomGenerator {
   private static Random rnd = new Random();
    private static String AlphaNumericString ="abcdefghijklmnopqrstuvxyz";


    public static int generateAccount(){
        int number = rnd.nextInt(999999999);
        return number;
    }

    public static String generateName(int n){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    public static String generateEmail(){
        StringBuilder sb = new StringBuilder();
        generateName(5);
        return sb.toString() + rnd.nextInt(99) + "@gmail.com";
    }
}
