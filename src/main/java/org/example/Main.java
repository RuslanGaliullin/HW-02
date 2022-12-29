package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var root = getRootDirectory();
        Handler giga = new Handler(root);
        giga.startFolderAnalysis();
        System.out.println(giga);
    }

    private static String getRootDirectory() {
        Scanner scanner;
        System.out.println("Enter the absolute path to the root directory, where .txt files are located\n");
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}