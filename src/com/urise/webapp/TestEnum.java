package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public enum TestEnum {
    ENUM;

    public static void main(String[] args) {
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle());
        }
        Gen<Integer> gen = new Gen<Integer>(4);
        gen.showType();
    }

    static class Gen<T> {
        T ob; // объявление объекта типа T

        // Передать конструктору ссылку на объект типа T
        Gen(T o) {
            ob = o;
        }

        // Вернуть ob
        T getob() {
            return ob;
        }

        // Показать тип T
        void showType() {
            System.out.println("Тип T: " + ob.getClass().getName());
        }
    }
}



