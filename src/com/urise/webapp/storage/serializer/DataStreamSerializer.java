package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializerStrategie {
//    public static void main(String[] args) throws IOException {
//        Resume resume = ResumeTestData.createResume("12", "Pidor");
//        DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("f://java.txt")));
//        doWrite(resume, dos);
//    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sectionMap = resume.getSections();
            dos.writeInt(sectionMap.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sectionMap.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                AbstractSection sections = entry.getValue();
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        dos.writeUTF(((TextSection) sections).getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) sections).getItems();
                        dos.writeInt(list.size());
                        for (String s : list) {
                            dos.writeUTF(s);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {}}}}}
//                    case EXPERIENCE, EDUCATION -> {
//                        OrganizationSection exp = (OrganizationSection) entry.getValue();
//                        addListOraginizationWithPeriods(dos, exp);
//    private static void addListOraginizationWithPeriods(DataOutputStream dos, OrganizationSection exp) throws
//            IOException {
//        List<Organization> organizationList = exp.getOrganizationList();
//        for (Organization organization : organizationList) {
//            dos.writeUTF(organization.getName());
//            URL url = organization.getWebsite();
//            dos.writeUTF(url.toString());
//            List<Organization.Period> periods = organization.getPeriods();
//            for (Organization.Period period : periods) {
//                dos.writeUTF(period.getTitle());
//                dos.writeUTF(period.getDescription());
//                dos.writeUTF(valueOf(period.getStartDate()));
//                dos.writeUTF(valueOf(period.getEndDate()));
//            }
//        }
//    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

//            Map<SectionType, AbstractSection> sectionMap = new HashMap<>();
//            int size1 = dis.readInt();
//            for (int i = 0; i < size1; i++) {
                SectionType sectionType = (SectionType) new Object();
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
//                        TextSection textSection = new TextSection();
//                        textSection.setContent(dis.readUTF());
//                        sectionMap.put(sectionType, textSection);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
//                        ListSection listSection = new ListSection();
//                        List<String> items = new ArrayList<>();
//                        items.add(dis.readUTF());
//
//                        listSection.setItems(items);
//                        sectionMap.put(sectionType, listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                    }
                }
//            }
//            resume.addSections(sectionMap);
            return resume;
        }
    }
}
//                        OrganizationSection exp = new OrganizationSection();
//                        List<Organization> organizationList = new ArrayList<>();
//
//                        Organization organization = new Organization();
//                        organization.setName(dis.readUTF());
//                        URL url = new URL(dis.readUTF());
//                        organization.setWebsite(url);
//                        List<Organization.Period> periods = new ArrayList<>();
//                        Organization.Period period = new Organization.Period();
//                        period.setTitle(dis.readUTF());
//                        period.setDescription(dis.readUTF());
//                        period.setStartDate(LocalDate.parse(dis.readUTF()));
//                        period.setEndDate(LocalDate.parse(dis.readUTF()));
//                        periods.add(period);
//                        organization.setPeriods(periods);
//                        organizationList.add(organization);
//                        exp.setOrganizationList(organizationList);
//
//                        resume.addSection(sectionType, exp);
