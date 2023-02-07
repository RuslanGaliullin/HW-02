package utility;

import org.junit.jupiter.api.Test;
import org.main.DependentFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void getExtension() {
        var files = new String[]{"/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/one.txt",
                "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/photo.png",
                "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/two.txt",
                "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo/four.txt",
                "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo/three.txt",
                "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/five.txt",
                "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/seven.txt",
                "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/six.txt"};
        var correctResult = new String[]{"txt", "png", "txt", "txt", "txt", "txt", "txt", "txt"};
        for (int i = 0; i < files.length; ++i) {
            assert FileUtils.getExtension(files[i]).isPresent();
            assertEquals(FileUtils.getExtension(files[i]).get(), correctResult[i]);
        }
    }

    @Test
    void addFileDependencies() {
        var baseFile = new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/one.txt");
        FileUtils.addFileDependencies(baseFile, "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle");
        var actualDependencies = new ArrayList<>(baseFile.getDependencies().stream().toList());
        actualDependencies.sort(String::compareTo);
        var correctDependencies = new ArrayList<>(Arrays.stream((new String[]
                {"/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/two.txt",
                        "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo/three.txt",
                        "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/six.txt"})).toList());
        correctDependencies.sort(String::compareTo);
        assertEquals(actualDependencies, correctDependencies);
    }
}