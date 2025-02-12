package com.example.demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvColumnComparator {
    public static void main(String[] args) {
        try {
            InputStream inputStream = CsvColumnComparator.class.getClassLoader().getResourceAsStream("input.csv");
            if (inputStream == null) {
                System.out.println("Archivo no encontrado.");
                return;
            }

            List<String>[] columns = readCsvColumns(inputStream);
            List<String> column1 = columns[0];
            List<String> column2 = columns[1];

            System.out.println("El array de la columna 1 es: " + column1);
            System.out.println("El array de la columna 2 es: " + column2);

            printElementsInSecondNotInFirst(column1, column2);
            printElementsInFirstNotInSecond(column1, column2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String>[] readCsvColumns(InputStream inputStream) {
        List<String> column1 = new ArrayList<>();
        List<String> column2 = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 1) column1.add(values[0].trim());
                if (values.length >= 2) column2.add(values[1].trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new List[]{column1, column2};
    }

    private static void printElementsInSecondNotInFirst(List<String> list1, List<String> list2) {
        System.out.println("Elementos presentes en el array 2 que no están en el array 1:");
        for (String element : list2) {
            if (!list1.contains(element)) {
                System.out.println(element);
            }
        }
    }

    private static void printElementsInFirstNotInSecond(List<String> list1, List<String> list2) {
        System.out.println("Elementos presentes en el array 1 que no están en el array 2:");
        for (String element : list1) {
            if (!list2.contains(element)) {
                System.out.println(element);
            }
        }
    }
}
