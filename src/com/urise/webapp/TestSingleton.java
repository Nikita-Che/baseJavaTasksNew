package com.urise.webapp;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        getInstance().toString();
        TestEnum testEnum = TestEnum.valueOf("ENUM");
        System.out.println(testEnum);
        System.out.println(testEnum.ordinal());
    }
}
