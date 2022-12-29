package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {
//        for (var i : Objects.requireNonNull(df.listFiles())) {
//            if (!i.isDirectory()) {
//            }
//        }
        Handler giga = new Handler("/Users/ruslangaliullin/IdeaProjects/HW-02/src/test");
        giga.startFolderAnalysis();
        System.out.println(giga);
    }
}