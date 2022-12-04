package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.SectionType.valueOf;

public class DataStreamSerializer implements SerializerStrategie {
//    public static void main(String[] args) throws IOException {
//        Resume resume = ResumeTestData.createResume("12", "Pidor");
//        DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("d://java.txt")));
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
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection textSection = (TextSection) entry.getValue();
                        dos.writeUTF(textSection.getContent());
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection section = (ListSection) entry.getValue();
                        List<String> list = section.getItems();
                        for (String s : list) {
                            dos.writeUTF(s);
                        }
                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection exp = (OrganizationSection) entry.getValue();
                        addListOraginizationWithPeriods(dos, exp);
                    }
                }
            }
        }
    }

    private static void addListOraginizationWithPeriods(DataOutputStream dos, OrganizationSection exp) throws IOException {
        List<Organization> organizationList = exp.getOrganizationList();
        for (Organization organization : organizationList) {
            dos.writeUTF(organization.getName());
            URL url = organization.getWebsite();
            dos.writeUTF(url.toString());
            List<Organization.Period> periods = organization.getPeriods();
            for (Organization.Period period : periods) {
                dos.writeUTF(period.getTitle());
                dos.writeUTF(period.getDescription());
                dos.writeUTF(String.valueOf(period.getStartDate()));
                dos.writeUTF(String.valueOf(period.getEndDate()));
            }
        }
    }

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

            int size2 = dis.readInt();
            for (int i = 0; i < size2; i++) {
                String se = dis.readUTF();
                SectionType sectionType = SectionType.valueOf(String.valueOf(valueOf(se))); //todo ошбика где то. SectionType не так должен находиться
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection textSection = new TextSection();
                        textSection.setContent(dis.readUTF());
                        resume.addSection(sectionType, textSection);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = new ListSection();
                        List<String> items = new ArrayList<>();
                        items.add(dis.readUTF());
                        items.add(dis.readUTF());
                        listSection.setItems(items);
                        resume.addSection(sectionType, listSection);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        OrganizationSection exp = new OrganizationSection();
                        List<Organization> organizationList = new ArrayList<>();

                        Organization organization = new Organization();
                        organization.setName(dis.readUTF());
                        URL url = new URL(dis.readUTF());
                        organization.setWebsite(url);
                        List<Organization.Period> periods = new ArrayList<>();
                        Organization.Period period = new Organization.Period();
                        period.setTitle(dis.readUTF());
                        period.setDescription(dis.readUTF());
                        period.setStartDate(LocalDate.parse(dis.readUTF()));
                        period.setEndDate(LocalDate.parse(dis.readUTF()));
                        periods.add(period);
                        organization.setPeriods(periods);
                        organizationList.add(organization);
                        exp.setOrganizationList(organizationList);

                        resume.addSection(sectionType, exp);
                    }
                }
            }
            return resume;
        }
    }
}

// ОСНОВА ТОГО КАК НАДО ДОБАВЛЯТЬ.
//                SectionType sectionType = SectionType.valueOf(dis.readUTF());
//                AbstractSection abstractSection = dis.readUTF();
//                resume.addSection(sectionType, abstractSection);

