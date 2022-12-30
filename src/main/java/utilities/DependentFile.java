package utilities;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DependentFile extends File {
    private final Set<String> dependencies = new HashSet<>();

    public DependentFile(String pathname) {
        super(pathname);
    }

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

    public boolean dependsOn(DependentFile other) {
        if (other == null) {
            return false;
        }
        return dependencies.contains(other.getAbsolutePath());
    }

    public void addDependencies(String file) {
        dependencies.add(file);
    }
}
