package com.urise.webapp.storage.serializer;


import com.urise.webapp.model.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class DataStreamSerializer implements SerializerStrategie {
//    public static void main(String[] args) throws IOException {
//        Resume resume = ResumeTestData.createResume("12", "Pidor");
//        DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("c://java/java.txt")));
//        doWrite(resume, dos);
//        DataInputStream dis = new DataInputStream(new FileInputStream(new File("c://java/java.txt")));
//        doRead(dis);
//    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeWithException(contacts.entrySet(), dos, contactTypeStringEntry -> {
                dos.writeUTF(contactTypeStringEntry.getKey().name());
                dos.writeUTF(contactTypeStringEntry.getValue());
            });

            writeWithException(resume.getSections().entrySet(), dos, sectionTypeAbstractSectionEntry -> {
                SectionType sectionType = sectionTypeAbstractSectionEntry.getKey();
                dos.writeUTF(sectionType.name());
                AbstractSection sections = sectionTypeAbstractSectionEntry.getValue();
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> dos.writeUTF(((TextSection) sections).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> list = ((ListSection) sections).getItems();
                        writeWithException(list, dos, dos::writeUTF);
                    }
                    case EXPERIENCE, EDUCATION -> {
                        List<Organization> organizationList = ((OrganizationSection) sections).getOrganizationList();
                        writeWithException(organizationList, dos, organization -> {
                            dos.writeUTF(organization.getName());
                            dos.writeUTF(String.valueOf(organization.getWebsite()));
                            List<Organization.Period> periods = organization.getPeriods();
                            writeWithException(periods, dos, period -> {
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(String.valueOf(period.getStartDate()));
                                dos.writeUTF(String.valueOf(period.getEndDate()));
                            });
                        });
                    }
                }
            });
        }
    }

    public interface CustomConsumer<T> {
        void accept(T t) throws IOException;
    }

    public <T> void writeWithException(Collection<T> collection, DataOutputStream dos, CustomConsumer<? super T> consumer) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            consumer.accept(item);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            EnumMap<SectionType, AbstractSection> sectionMap = new EnumMap<>(SectionType.class);
            readWithException(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> sectionMap.put(sectionType, new TextSection(dis.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        List<String> stringList = new ArrayList<>();
                        readWithException(dis, () -> stringList.add(dis.readUTF()));
                        sectionMap.put(sectionType, new ListSection(stringList));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection organizationSection = new OrganizationSection();
                        List<Organization> organizationList = new ArrayList<>();
                        readWithException(dis, () -> {
                            Organization organization = new Organization();
                            organization.setName(dis.readUTF());
                            URL url = new URL(dis.readUTF());
                            organization.setWebsite(url);

                            List<Organization.Period> periods = new ArrayList<>();
                            readWithException(dis, () -> {
                                Organization.Period period = new Organization.Period();
                                period.setTitle(dis.readUTF());
                                period.setDescription(dis.readUTF());
                                period.setStartDate(LocalDate.parse(dis.readUTF()));
                                period.setEndDate(LocalDate.parse(dis.readUTF()));
                                periods.add(period);
                            });
                            organization.setPeriods(periods);
                            organizationList.add(organization);
                            organizationSection.setOrganizationList(organizationList);
                        });
                        sectionMap.put(sectionType, organizationSection);
                    }
                }
            });
            resume.addSections(sectionMap);
            return resume;
        }
    }

    public interface CustomInterface {
        void method() throws IOException;
    }

    public void readWithException(DataInputStream dis, CustomInterface customInterface) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            customInterface.method();
        }
    }
}

