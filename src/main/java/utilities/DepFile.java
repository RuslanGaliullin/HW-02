package utilities;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class DepFile extends File {
    private Set<String> dependencies = new TreeSet<>();

    public DepFile(String pathname) {
        super(pathname);
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(Set<String> dependencies) {
        this.dependencies = dependencies;
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

    public boolean DependsOn(DepFile other) {
        if (other == null) {
            return false;
        }
        return dependencies.contains(other.getAbsolutePath());
    }

    public void addDependencies(String file) {
        dependencies.add(file);
    }
}
