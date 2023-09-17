package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.SortedMap;

class CalcThread implements Runnable {

    public CalcThread() {
    }

    @Override
    public void run() {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            String input;
            while (!((input = br.readLine()).isEmpty())){
                System.out.println(Calculator.calc(input));
            }

        } catch (IOException e) {
            throw new RuntimeException("throws Exception"); // вывод как в условии
        }

    }
}
