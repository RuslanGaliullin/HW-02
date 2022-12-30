package org.main;

import utilities.DependentFile;
import utilities.DirectoryParser;

import java.io.FileNotFoundException;
import java.util.*;


public class FileCollector {
    private final String root;
    private boolean cycle = false;
    private final Set<DependentFile> files = new HashSet<>();

    public FileCollector(String root) {
        this.root = root;
    }

    public void startFolderAnalysis() {
        collectAllFiles();
        for (var o : files) {
            FileParser.addFileDependencies(o, root);
        }
        cycle = findCycle();
        if (cycle) {
            System.out.println("Cycle detected");
        }
    }

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

    @Override
    public String toString() {
        if (cycle) {
            return "There is a cycle in dependencies";
        }
        StringBuilder fileList = new StringBuilder();
        StringBuilder data = new StringBuilder();
        List<DependentFile> queue = getOrder();
        for (var i : queue) {
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

    private List<DependentFile> getOrder() {
        List<DependentFile> result = new ArrayList<>(files);
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
