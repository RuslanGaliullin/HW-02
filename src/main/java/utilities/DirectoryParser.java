package utilities;

import org.main.FileParser;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DirectoryParser {
    public static Optional<Set<DependentFile>> getFiles(String directoryPath, String extension) {
        var list = new DependentFile(directoryPath).listFiles();
        if (list == null) {
            return Optional.empty();
        }
        return Optional.of(Stream.of(list)
                .filter(member -> !member.isDirectory())
                .filter(member -> FileParser.getExtension(member.getName()).orElse("").equals(extension))
                .collect(Collectors.toSet()));
    }

    public static Optional<Set<String>> getDirs(String directoryPath) {
        var list = new DependentFile(directoryPath).listFiles();
        if (list == null) {
            return Optional.empty();
        }
        return Optional.of(Stream.of(list)
                .filter(File::isDirectory)
                .map(File::getAbsolutePath).collect(Collectors.toSet()));
    }


}
