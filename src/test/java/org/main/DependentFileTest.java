package org.main;

import org.junit.jupiter.api.Test;
import utility.FileUtils;

import static org.junit.jupiter.api.Assertions.*;

class DependentFileTest {

    @Test
    void isDependentOn() {
        var baseFile = new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_cycle/subOne/one.txt");
        var depFile = new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_cycle/two.txt");
        FileUtils.addFileDependencies(depFile, "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_cycle");
        assertTrue(depFile.isDependentOn(baseFile));
        baseFile = new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/subTwo/three.txt");
        depFile = new DependentFile("/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle/five.txt");
        FileUtils.addFileDependencies(depFile, "/Users/ruslangaliullin/Downloads/RuslanGaliullin/HW-02-HW_02/src/test/main_no_cycle");
        assertTrue(depFile.isDependentOn(baseFile));
        assertFalse(baseFile.isDependentOn(depFile));
    }
}