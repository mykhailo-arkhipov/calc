package org.coreCalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class CalcThread implements Runnable {

    @Override
    public void run() {
                // запускаем try с ресурсами, при пустой строке завершаем работу
                // решил добавить выход по exit, единственное отклонение от требований
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            String input;
            while (!((input = br.readLine()).equals("exit"))){
                System.out.println(Calculator.calc(input));
            }

        } catch (IOException e) {
            throw new RuntimeException("throws Exception"); // вывод как в условии
        }

    }
}
