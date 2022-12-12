package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MainFileTest {
    public static void main(String[] args) {
//        File file = new File("C:\\Users\\nikita\\Desktop\\GitHub\\basejava\\storage");
//        file.setWritable(true);
//        file.setReadable(true);
//        System.out.println(file.canRead());
//        System.out.println(file.canWrite());
//        System.out.println(file.getName());
//        System.out.println(file.getPath());
//        System.out.println(file.getAbsolutePath());
//        System.out.println(file.getAbsoluteFile());
//        System.out.println(file.isFile());
//        System.out.println(file.isDirectory());
//        System.out.println(file.isHidden());

//        Resume resume = ResumeTestData.createResume("1 ", "Nikita Chertkov");
//        System.out.println(resume);
        Consumer<String> print = System.out::println;
        print.accept("pidor");
        Consumer<List<String>> listConsumer = System.out::println;
        List<String> stringList = new ArrayList<>();
        stringList.add("s");
        listConsumer.accept(stringList);
    }
}
