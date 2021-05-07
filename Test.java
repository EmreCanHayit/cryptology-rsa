package com.company;

import java.util.ArrayList;

import static com.company.Main.*;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> fails = new ArrayList<>();
        int i, counter = 0, testSize = 1000;

        for (i = 0; i < testSize; i++) {
            System.out.println("-------------------------->"+i+"<--------------------------");
            cryptology();
            if (!message.equals(String.valueOf(messageASCII)) || message.equals(String.valueOf(testEncryptASCII))) {
                counter++;
                fails.add(i);
            }
        }

        System.out.println("-------------------------->Result<--------------------------");
        System.out.println(i + " attempts " + counter + " false results");
        if (fails.size() != 0) {
            System.out.println("False results;");
            for (i = 0; i < fails.size(); i++) {
                System.out.println(fails.get(i));
            }
        }
    }
}
