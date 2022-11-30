package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\nikita\\Desktop\\GitHub\\basejava\\.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File dir = new File("C:\\Users\\nikita\\Desktop\\GitHub\\basejava\\src\\com\\urise\\webapp");
        System.out.println(dir.isDirectory());
        for (String name : Objects.requireNonNull(dir.list())) {
            System.out.println(name);
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//Сделайте рекурсивный обход и вывод имени файлов в каталогах и подкаталогах (корневой каталог- ваш проект)
//        String path = "C:\\Users\\nikita\\Desktop\\GitHub\\basejava\\src";
//        list(path);

        print(dir);
    }

    public static void list(String path) {
        File dir = new File(path);
        String[] sDirList = dir.list();
        int i;
        for (i = 0; i < sDirList.length; i++) {
            File f1 = new File(path +
                    File.separator + sDirList[i]);
            if (f1.isFile())
                System.out.println(path +
                        File.separator + sDirList[i]);
            else {
                list(path +
                        File.separator + sDirList[i]);
            }
        }
    }

    public static void print(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("File:     " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println();
                    System.out.println("Directory: " + file.getName());
                    print(file);
                }
            }
        }
    }
}
