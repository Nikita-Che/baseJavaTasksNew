package com.urise.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"), //textSection
    OBJECTIVE("Позиция"),       //textSection
    ACHIEVEMENT("Достижения"),  //textSection
    QUALIFICATIONS("Квалификация"), //textSection
    EXPERIENCE("Опыт работы"),  //organizationSection
    EDUCATION("Образование"); //organizationSection

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
