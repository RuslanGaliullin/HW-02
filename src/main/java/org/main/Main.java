package org.main;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var root = getRootDirectory();
        // Запуск анализа при существовании директории
        if (new File(root).exists()) {
            FileCollector merger = new FileCollector(root);
            merger.startFolderAnalysis();
            System.out.println(merger);
        } else {
            System.out.println("Given directory does not exist\n");
        }
    }

    /**
     * Метод для получения от пользователя корневой директории
     *
     * @return абсолютный путь к директории
     */
    private static String getRootDirectory() {
        Scanner scanner;
        System.out.println("Enter the absolute path to the root directory, where .txt files are located\n");
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}