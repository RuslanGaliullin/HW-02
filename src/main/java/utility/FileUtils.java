package utility;

import org.main.DependentFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Класс-набор с методами для обработки файлов
 */
public final class FileUtils {
    /**
     * Метод, для сохранения зависимостей из файла
     *
     * @param file текстовый файл, в котором надо найти зависимости
     * @param root корневая директория, относительно которой прописаны зависимости
     */
    public static void addFileDependencies(DependentFile file, String root) {
        try (Scanner scanner = new Scanner(file)) {
            StringBuilder data = new StringBuilder();
            while (scanner.hasNext()) {
                data.append(scanner.nextLine());
            }

            // Поиск всех подстрок вида:require ’*’
            var beginning = data.indexOf("require ’");
            int ending = 0;
            while (beginning != -1) {
                beginning += 9 + ending;
                ending = data.substring(beginning).indexOf("’");
                if (ending == -1) {
                    break;
                }
                ending += beginning;
                // Добавление зависимости по абсолютному пути
                file.addDependencies(root + File.pathSeparator + data.substring(beginning, ending));
                beginning = data.substring(ending).indexOf("require ’");
            }

        } catch (FileNotFoundException ignored) {
        }
    }

    /**
     * Метод для определения расширения файла
     *
     * @param filename файл, у которого надо определить расширение
     * @return расширение файла
     */
    public static Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
