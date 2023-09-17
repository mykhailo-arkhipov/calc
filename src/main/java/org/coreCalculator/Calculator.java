package org.coreCalculator;
import java.util.HashMap;
import java.util.Map;

/*
Никаких вольностей не допускал, дополнительных выводов не добавлял и т.п.
Всё по условию.

- Класс Calculator - в статическом блоке инициализируем карту парами цифр для конвертации
    метод String calc(String input) принимает строку, вычисляет, отправляет ответ
    метод String convertToRoman(int value) конвертирует результат вычисления

- Класс CalcThread - поток для чтения и вызова Solution.calc() и выброса исключения
 */

public class Calculator {
    static Map<String, Integer> romanToArabic = new HashMap<>();

    static {
                //1-10, 10-90 и 100 (для случая 10*10)
        romanToArabic.put("I", 1);
        romanToArabic.put("II", 2);
        romanToArabic.put("III", 3);
        romanToArabic.put("IV", 4);
        romanToArabic.put("V", 5);
        romanToArabic.put("VI", 6);
        romanToArabic.put("VII", 7);
        romanToArabic.put("VIII", 8);
        romanToArabic.put("IX", 9);
        romanToArabic.put("X", 10);
        romanToArabic.put("XX", 20);
        romanToArabic.put("XXX", 30);
        romanToArabic.put("XL", 40);
        romanToArabic.put("L", 50);
        romanToArabic.put("LX", 60);
        romanToArabic.put("LXX", 70);
        romanToArabic.put("LXXX", 80);
        romanToArabic.put("XC", 90);
        romanToArabic.put("C", 100);
    }

    public static void main(String[] args) {
                // запускаем поток, рекурсивно вызывающий calc()
        Thread calcProcessThread = new Thread(new CalcThread());
        calcProcessThread.start();
    }
    public static String calc(String input) {
                // первое число, второе, результат
        int a, b, res;
        boolean isDigitalOperation = false;
        String[] splittedString = input.split(" ");

        if (splittedString.length != 3) throw new RuntimeException();

        if (Character.isDigit(splittedString[0].charAt(0)) &&
                Character.isDigit(splittedString[2].charAt(0))) {
                // если а и б начинаются с цифр, то:
            a = Integer.parseInt(splittedString[0]);
            if (a < 1 || a > 10) throw new RuntimeException();

            b = Integer.parseInt(splittedString[2]);
            if (b < 1 || b > 10) throw new RuntimeException();
                // флаг для парсера, истина - результат не конвертируем
            isDigitalOperation = true;
        }
        else {
                // проверяем на возможность конвертации
            for (int i = 0; i < 3; i += 2) {
                if (!romanToArabic.containsKey(splittedString[i])) {
                    throw new RuntimeException();
                }
            }

            a = romanToArabic.get(splittedString[0]);
            if (a > 10) throw new RuntimeException();
            b = romanToArabic.get(splittedString[2]);
            if (b > 10) throw new RuntimeException();
        }

                // вычисляем привычным способом

        switch (splittedString[1]){
            case "*":
                res = a * b;
                break;
            case "+":
                res = a + b;
                break;
            case "-":
                res = a - b;
                break;
            case "/":
                res = a / b;
                break;
            default:
                throw new RuntimeException();
        }

                // цифры или нет? возвращаем правильную строку-результат
        if (isDigitalOperation){
            return String.valueOf(res);
        }
        else{
            if (res < 1) throw new RuntimeException();
            return convertToRoman(res);
        }

    }

                // метод только для конвертации результата
    public static String convertToRoman(int value){

        if (value == 100) return "C";
                // десятки и единицы запускаем на конвертацию
        int des = value - (value % 10), ed = value % 10;
        String sDes = "", sEd = "";

        for (Map.Entry<String, Integer> entry : romanToArabic.entrySet()){
                // если не видим ключа, строка пустая. Иначе десятки + единицы
            if (entry.getValue() == des) sDes = entry.getKey();
            if (entry.getValue() == ed) sEd = entry.getKey();

        }
        return sDes + sEd;
    }
}