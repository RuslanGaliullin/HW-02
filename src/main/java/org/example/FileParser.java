package org.example;

import utilities.DepFile;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

public class FileParser {
    public static void addFileDependacies(DepFile file, String root) {
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder data = new StringBuilder();
            while (scanner.hasNext()) {
                data.append(scanner.nextLine());
            }
            var beginning = data.indexOf("require '");
            int ending = 0;
            while (beginning != -1) {
                beginning += 9 + ending;
                ending = data.substring(beginning).indexOf("'");
                if (ending == -1) {
                    break;
                }
                ending += beginning;
                file.addDependencies(root + "/" + data.substring(beginning, ending));
                beginning = data.substring(ending).indexOf("require '");
            }
        } catch (FileNotFoundException ignored) {
        }
    }

    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
