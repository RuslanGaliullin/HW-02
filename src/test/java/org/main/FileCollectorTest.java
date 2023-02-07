package org.main;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileCollectorTest {

    @Test
    void startFolderAnalysisNoCycle() {
        var collector = new FileCollector("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle");
        collector.startFolderAnalysis();
        assertFalse(collector.isCycled());
        var correctFiles = new DependentFile[]{new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/one.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/two.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo/four.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo/three.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/five.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/seven.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/six.txt")};
        assertEquals(collector.getFiles(), new HashSet<>(List.of(correctFiles)));
    }

    @Test
    void startFolderAnalysisCycle() {
        var collector = new FileCollector("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_cycle");
        collector.startFolderAnalysis();
        var correctFiles = new DependentFile[]{new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_cycle/subOne/one.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_cycle/two.txt")};
        assertEquals(collector.getFiles(), new HashSet<>(List.of(correctFiles)));
        assertTrue(collector.isCycled());
    }

    @Test
    void getFiles() {
        var collector = new FileCollector("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle");
        collector.startFolderAnalysis();
        var correctFiles = new DependentFile[]{new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/one.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subOne/subSubOne/two.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo/four.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo/three.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/five.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/seven.txt"),
                new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/six.txt")};
        assertEquals(collector.getFiles(), new HashSet<>(List.of(correctFiles)));
    }

    @Test
    void isCycled() {
        var collector = new FileCollector("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_cycle");
        collector.startFolderAnalysis();
        assertTrue(collector.isCycled());

        collector = new FileCollector("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle");
        collector.startFolderAnalysis();
        assertFalse(collector.isCycled());
    }

    @Test
    void startFolderAnalysis() {
        startFolderAnalysisCycle();
        startFolderAnalysisNoCycle();
    }

    @Test
    void testToString() {
        var collector = new FileCollector("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle");
        collector.startFolderAnalysis();
        var correctResult = """
                
                now dep

                require ’subTwo/three.txt’

                smth plain text require ’five.txt’require ’seven.txt’

                just firstFile require ’subTwo/four.txt’

                six text file

                dfs text file require ’subOne/subSubOne/two.txt’ require ’subTwo/three.txt’ require ’six.txt’

                seven text file require ’five.txt’
                """;
        assertEquals(collector.toString(), correctResult);
    }
}