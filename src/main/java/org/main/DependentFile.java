package org.main;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Класс, который расширяет File полем с зависимостями и методадами по работе с ними
 */
public class DependentFile extends File {
    public Set<String> getDependencies() {
        return dependencies;
    }

    // Зависимости a.k.a. require <>
    private final Set<String> dependencies = new HashSet<>();

    public DependentFile(String pathname) {
        super(pathname);
    }

    /**
     * Метод, эквивалентен методу из File public File[] listFiles()
     *
     * @return Массив из файлов и директорий
     */
    public DependentFile[] listFiles() {
        var list = super.listFiles();
        if (list == null) {
            return null;
        }

        DependentFile[] result = new DependentFile[list.length];
        for (int i = 0; i < list.length; ++i) {
            result[i] = new DependentFile(list[i].getAbsolutePath());
        }
        return result;
    }

    /**
     * Метод проверки зависимости объекта от файла other
     *
     * @param other файл, зависимость от кого проверяет метод
     * @return зависит файл или нет
     */
    public boolean isDependentOn(DependentFile other) {
        if (other == null) {
            return false;
        }
        return dependencies.contains(other.getAbsolutePath());
    }

    /**
     * Метод, чтобы добавить файл, от которого зависит объект этого файла
     *
     * @param file новая зависимость
     */
    public void addDependencies(String file) {
        dependencies.add(file);
    }
}
