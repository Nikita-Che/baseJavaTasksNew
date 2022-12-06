package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializerStrategie {
//    public static void main(String[] args) throws IOException {
//        Resume resume = ResumeTestData.createResume("12", "Pidor");
//        DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("c://java/java.txt")));
//        doWrite(resume, dos);
//}

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
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationList = ((OrganizationSection) sections).getOrganizationList();
                        dos.writeInt(organizationList.size());
                        for (Organization s : organizationList) {
                            dos.writeUTF(String.valueOf(s));
                        }
                    }
                }
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

            EnumMap<SectionType, AbstractSection> sectionMap = new EnumMap<>(SectionType.class);
            int size1 = dis.readInt();
            for (int i = 0; i < size1; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        TextSection textSection = new TextSection();
                        textSection.setContent(dis.readUTF());
                        sectionMap.put(sectionType, textSection);
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection listSection = new ListSection();
                        List<String> stringList = new ArrayList<>();
                        int size2 = dis.readInt();
                        for (int i1 = 0; i1 < size2; i1++) {
                            stringList.add(dis.readUTF());
                        }
                        listSection.setItems(stringList);
                        sectionMap.put(sectionType, listSection);
                    }

                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection organizationSection = new OrganizationSection();

                        sectionMap.put(sectionType, organizationSection);
                    }
                }
            }
            resume.addSections(sectionMap);
            return resume;
        }
    }
}

