package utilities;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DepFile extends File {
    private final Set<String> dependencies = new HashSet<>();

    public DepFile(String pathname) {
        super(pathname);
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public DepFile[] listFiles() {
        var list = super.listFiles();
        if (list == null) {
            return null;
        }
        DepFile[] result = new DepFile[list.length];
        for (int i = 0; i < list.length; ++i) {
            result[i] = new DepFile(list[i].getAbsolutePath());
        }
        return result;
    }

    public boolean dependsOn(DepFile other) {
        if (other == null) {
            return false;
        }
        return dependencies.contains(other.getAbsolutePath());
    }

    public void addDependencies(String file) {
        dependencies.add(file);
    }
}
