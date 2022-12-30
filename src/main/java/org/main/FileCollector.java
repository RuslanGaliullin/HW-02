package org.main;

import utilities.DirectoryParser;
import utilities.FileParser;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Основной класс для сбора текстовых файлов и их конкатенации в соответствии с зависимостями
 */
public class FileCollector {
    // Директория, в которой идет сбор файлов
    private final String root;
    // Наличие цикла в директории объекта
    private boolean cycle = false;
    // Файлы в директории объекта
    private final Set<DependentFile> files = new HashSet<>();
    // Список файлов в соответствии с зависимостями
    private List<DependentFile> dependencyList = new ArrayList<>();

    public FileCollector(String root) {
        this.root = root;
    }

    /**
     * Метода для запуска сбора файлов и проверки их цикличности
     */
    public void startFolderAnalysis() {
        collectAllFiles();
        for (var o : files) {
            FileParser.addFileDependencies(o, root);
        }
        cycle = findCycle();
        if (cycle) {
            System.out.println("Cycle detected");
        } else {
            dependencyList = getOrder();
        }
    }

    /**
     * Метод для обнаружения цикла в зависимостях
     *
     * @return true или false - есть цикл или нет
     */
    private boolean findCycle() {
        for (var i : files) {
            for (var j : files) {
                if (i != j && i.dependsOn(j) && j.dependsOn(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Метод для сбора всех файлов .txt в корневой директории
     */
    private void collectAllFiles() {
        var directories = new TreeSet<String>();
        DirectoryParser.getFiles(root, "txt").ifPresent(files::addAll);
        DirectoryParser.getDirs(root).ifPresent(directories::addAll);
        while (directories.size() > 0) {
            DirectoryParser.getFiles(directories.first(), "txt").ifPresent(files::addAll);
            DirectoryParser.getDirs(directories.first()).ifPresent(directories::addAll);
            directories.remove(directories.first());
        }
    }

    /**
     * Перегруженный метод для конкатенации всех файлов
     *
     * @return строка-результат конкатенаций
     */
    @Override
    public String toString() {
        if (cycle) {
            return "There is a cycle in dependencies";
        }
        StringBuilder fileList = new StringBuilder();
        StringBuilder data = new StringBuilder();
        for (var i : dependencyList) {
            // Если захотите увидеть список зависимостей
            // fileList.append(i.getAbsolutePath()).append('\n');
            try (Scanner scanner = new Scanner(i)) {
                while (scanner.hasNext()) {
                    data.append(scanner.nextLine()).append('\n');
                }
            } catch (FileNotFoundException ignored) {
            }
        }
        return fileList.append(data).toString();
    }

    /**
     * Метод для сортировки всех файлов по зависимостям
     *
     * @return отсортированный список
     */
    private List<DependentFile> getOrder() {
        ArrayList<DependentFile> result = new ArrayList<>(files);
        if (files.size() == 1) {
            return result;
        }
        // Алгоритм сортировкой вставками: пытаемся опустить в списки каждый файл как можно ниже
        for (int i = result.size() - 2; i >= 0; --i) {
            var currentFile = result.get(i);
            for (int j = i + 1; j <= result.size(); ++j) {
                if (j == result.size() || result.get(j).dependsOn(currentFile)) {
                    result.set(j - 1, currentFile);
                    break;
                }
                result.set(j - 1, result.get(j));
            }
        }
        return result;
    }
}
