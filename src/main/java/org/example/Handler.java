package org.example;

import utilities.DepFile;

import java.io.FileNotFoundException;
import java.util.*;


public class Handler {
    private final String root;
    private boolean cycle = false;
    private final Set<DepFile> files = new TreeSet<>();
    private final Set<DepFile> lowestFiles = new TreeSet<>();

    public Handler(String root) {
        this.root = root;
    }

    public void startFolderAnalysis() {
        collectAllFiles();
        for (var o : files) {
            FileParser.addFileDependacies(o, root);
        }
        cycle = findCycle();
        if (!cycle) {
            System.out.println("Cycle detected");
        } else {
            System.out.println(files.size());
            System.out.println(lowestFiles.size());
            for (var o : lowestFiles) {
                System.out.println(o.getAbsolutePath());
            }
        }
    }

    private boolean findCycle() {
        for (var i : files) {
            for (var j : files) {
                if (i != j && i.DependsOn(j) && j.DependsOn(i)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void collectAllFiles() {
        var directories = new TreeSet<String>();
        DirectoryParser.getTextFiles(root).ifPresent(files::addAll);
        DirectoryParser.getDirs(root).ifPresent(directories::addAll);
        while (directories.size() > 0) {
            DirectoryParser.getTextFiles(directories.first()).ifPresent(files::addAll);
            DirectoryParser.getDirs(directories.first()).ifPresent(directories::addAll);
            directories.remove(directories.first());
        }
    }

    @Override
    public String toString() {
        if (cycle) {
            return null;
        }
        StringBuilder fileList = new StringBuilder();
        StringBuilder data = new StringBuilder();
        List<DepFile> queue = getOrder().orElse(new ArrayList<>());
        for (var i : queue) {
            fileList.append(i.getAbsolutePath()).append('\n');
            try (Scanner scanner = new Scanner(i)) {
                while (scanner.hasNext()) {
                    data.append(scanner.nextLine());
                }
            } catch (FileNotFoundException ignored) {
            }
        }
        return fileList.append(data).toString();
    }

    private Optional<List<DepFile>> getOrder() {
//        List<DepFile> result = new ArrayList<>(files);
//        for (int i = 1; i < result.size(); ++i) {
//            for (int j = 0; j < i; ++j) {
//                if(result.get(i).DependsOn())
//            }
//        }
        return Optional.empty();
    }
}
