package utility;

import org.main.DependentFile;

import java.io.File;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Класс-набор с методами для обработки директории
 */
public final class DirectoryUtils {
    /**
     * Найти все файлы в директории с данным расширением
     *
     * @param directoryPath директория, в которой ведется поиск
     * @param extension     расширение, которое должно быть у файлов
     * @return множество всех файлов в директории
     */
    public static Optional<Set<DependentFile>> getFiles(String directoryPath, String extension) {
        var list = new DependentFile(directoryPath).listFiles();
        if (list == null) {
            return Optional.empty();
        }
        return Optional.of(Stream.of(list)
                .filter(member -> !member.isDirectory())
                .filter(member -> FileUtils.getExtension(member.getName()).orElse("").equals(extension))
                .collect(Collectors.toSet()));
    }

    /**
     * Найти все поддиректории в директории
     *
     * @param directoryPath директория, в которой ведется поиск
     * @return множество всех поддиректорий в директории
     */
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
