package com.urise.webapp;

import com.urise.webapp.model.*;

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
        contacts.put(ContactType.PHONE, "89505096");
        contacts.put(ContactType.MOBILE, "123");
        contacts.put(ContactType.HOME_PHONE, "321");
        contacts.put(ContactType.EMAIL, "vasya@vasya");
        contacts.put(ContactType.HOME_PAGE, "vasya.ru");
        contacts.put(ContactType.SKYPE, "vasilisk");
        contacts.put(ContactType.GITHUB, "vasyaGihub");
        contacts.put(ContactType.LINKEDIN, "VasyaLinkedin");
        contacts.put(ContactType.STACKOVERFLOW, "Vasya Stack");

        String content = "PERSONAL";
        TextSection personalTextSection = new TextSection(content);
        String content1 = "OBJECTIVE";
        TextSection objectiveTextSection = new TextSection(content1);

        List<String> achivementlist = new ArrayList<>();
        achivementlist.add("ACHIEVEMENT");
        ListSection achivementlistSection = new ListSection(achivementlist);

        List<String> qualifications = new ArrayList<>();
        qualifications.add("QUALIFICATIONS");
        ListSection qualiflistSection = new ListSection(qualifications);

        LocalDate startDate = LocalDate.of(2005, 11, 12);
        LocalDate endDate = LocalDate.of(2005, 11, 15);
        Organization.Period period = new Organization.Period("title", "description", startDate, endDate);
        Organization.Period period1 = new Organization.Period("title1", "description1", startDate, endDate);
        List<Organization.Period> periodList = new ArrayList<>();
        periodList.add(period);
        periodList.add(period1);
        String url = "https://github.com/Nikita-Che";
        URL web = null;
        try {
            web = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Organization organization = new Organization("organization", web, periodList);
        Organization organization1 = new Organization("organization1", web, periodList);
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);
        organizationList.add(organization1);
        OrganizationSection expOrganizationSection = new OrganizationSection(organizationList);

        OrganizationSection expOrganizationSection1 = new OrganizationSection(organizationList);

        Map<SectionType, AbstractSection> sectionsWorker = new EnumMap<SectionType, AbstractSection>(SectionType.class);
        sectionsWorker.put(SectionType.PERSONAL, personalTextSection);
        sectionsWorker.put(SectionType.OBJECTIVE, objectiveTextSection);
        sectionsWorker.put(SectionType.ACHIEVEMENT, achivementlistSection);
        sectionsWorker.put(SectionType.QUALIFICATIONS, qualiflistSection);
        sectionsWorker.put(SectionType.EXPERIENCE, expOrganizationSection);
        sectionsWorker.put(SectionType.EDUCATION, expOrganizationSection1);

        Resume resume = new Resume(uuid, fullName);
        resume.addSections(sectionsWorker);
//        resume.sections = sectionsWorker;
        resume.addContacts(contacts);
//        resume.contacts = contacts;
//        System.out.println(resume);

        return resume;
    }
}
