package utility;

import org.junit.jupiter.api.Test;
import org.main.DependentFile;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class DirectoryUtilsTest {

    @Test
    void getFiles() {
        var testedResult = DirectoryUtils.getFiles("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle", "txt");
        assert testedResult.isPresent();
        var correctResult = new DependentFile[]{new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/five.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/seven.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/six.txt")};
        assertEquals(testedResult.get(), new TreeSet<>(List.of(correctResult)));
    }

    @Test
    void getDirs() {
        var testedResult = DirectoryUtils.getDirs("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle");
        assert testedResult.isPresent();
        var correctResult = new String[]{"/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne",
                "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo"};
        assertEquals(testedResult.get(), new TreeSet<>(List.of(correctResult)));
    }
}