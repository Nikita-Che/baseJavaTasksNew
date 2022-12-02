package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ResumeTestData {
    public static void main(String[] args) throws MalformedURLException {
        createResume("UUID", "FullName");
    }

    public static Resume createResume(String uuid, String fullName) {

        Map<ContactType, String> contacts = new HashMap<>();
        contacts.put(ContactType.PHONE, "8-351-222-22-21");
        contacts.put(ContactType.MOBILE, "8-950-733-90-74");
        contacts.put(ContactType.HOME_PHONE, "8+351-794-35-80");
        contacts.put(ContactType.EMAIL, "nekitozzz@rambler.ru");
        contacts.put(ContactType.HOME_PAGE, "http://nchsoft.ru");
        contacts.put(ContactType.SKYPE, "chertkov_nikita");
        contacts.put(ContactType.GITHUB, "https://github.com/Nikita-Che");
        contacts.put(ContactType.LINKEDIN, "none");
        contacts.put(ContactType.STACKOVERFLOW, "none");

        String content = "Сильные стороны: Лидер, высоко работоспособен, умею организовывать и создавать команду, " +
                "умею делегировать, гибок в работе, честен, целеустремлен.";
        TextSection personalTextSection = new TextSection(content);
        String content1 = "Руководитель Интернет магазина Stomolnia.ru" +
                "Участие Создание и продвижение с нуля интернет магазина, объединяющего в себя структуру всей компании. ";
        TextSection objectiveTextSection = new TextSection(content1);

        List<String> achivementlist = new ArrayList<>();
        achivementlist.add("Организация команды и успешная реализация Java проектов");
        achivementlist.add("Организация онлайн стажировок и ведение проектов");
        ListSection achivementlistSection = new ListSection(achivementlist);

        List<String> qualifications = new ArrayList<>();
        qualifications.add("Джава, Джава 8, Джава 88, Джава69");
        qualifications.add("Языки: Джава, Питон, Обезьяна, Плюсы, Осел");
        ListSection qualiflistSection = new ListSection(qualifications);

        LocalDate startDate = LocalDate.of(2022, 11, 15);
        LocalDate endDate = LocalDate.of(2022, 12, 30);
        Organization.Period period = new Organization.Period("Ментор", "Организация процесса обучения", startDate, endDate);
        List<Organization.Period> periodList = new ArrayList<>();
        periodList.add(period);
        String url = "https://JavaRush.ru";
        URL web = null;
        try {
            web = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Organization organization = new Organization("JavaRush", web, periodList);
        LocalDate startDate1 = LocalDate.of(2021, 12, 1);
        LocalDate endDate1 = DateUtil.NOW;
        Organization.Period period1 = new Organization.Period("Старший разработчик", "Организация процесса разработки", startDate1, endDate1);
        List<Organization.Period> periodList1 = new ArrayList<>();
        periodList1.add(period1);
        String url1 = "https://JavaOps.ru";
        URL web1 = null;
        try {
            web1 = new URL(url1);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Organization organization1 = new Organization("JavaOps.ru", web1, periodList1);
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);
        organizationList.add(organization1);
        OrganizationSection expOrganizationSection = new OrganizationSection(organizationList);

        LocalDate startDate2 = LocalDate.of(2020, 11, 15);
        LocalDate endDate2 = LocalDate.of(2022, 12, 30);
        Organization.Period period2 = new Organization.Period("Студент", "Обучение в университете", startDate2, endDate2);
        List<Organization.Period> periodList2 = new ArrayList<>();
        periodList2.add(period2);
        String url2 = "https://CHEL-GU.ru";
        URL web2 = null;
        try {
            web2 = new URL(url2);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Organization edu1 = new Organization("CHEL-GU", web2, periodList2);
        List<Organization> educationList = new ArrayList<>();
        educationList.add(edu1);
        OrganizationSection educationSection = new OrganizationSection(educationList);

        Map<SectionType, AbstractSection> sectionsWorker = new EnumMap<SectionType, AbstractSection>(SectionType.class);
        sectionsWorker.put(SectionType.PERSONAL, personalTextSection);
        sectionsWorker.put(SectionType.OBJECTIVE, objectiveTextSection);
        sectionsWorker.put(SectionType.ACHIEVEMENT, achivementlistSection);
        sectionsWorker.put(SectionType.QUALIFICATIONS, qualiflistSection);
        sectionsWorker.put(SectionType.EXPERIENCE, expOrganizationSection);
        sectionsWorker.put(SectionType.EDUCATION, educationSection);

        Resume resume = new Resume(uuid, fullName);
        resume.addSections(sectionsWorker);
        resume.addContacts(contacts);

        return resume;
    }
}
