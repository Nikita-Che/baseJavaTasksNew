package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.SectionType.*;

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

            TextSection personal = (TextSection) resume.getSection(PERSONAL);
            dos.writeUTF(personal.getContent());
            TextSection objective = (TextSection) resume.getSection(OBJECTIVE);
            dos.writeUTF(objective.getContent());

            ListSection achivement = (ListSection) resume.getSection(ACHIEVEMENT);
            List<String> list = achivement.getItems();
            for (String s : list) {
                dos.writeUTF(s);
            }
            ListSection qualifications = (ListSection) resume.getSection(QUALIFICATIONS);
            List<String> list1 = qualifications.getItems();
            for (String s : list1) {
                dos.writeUTF(s);
            }

            OrganizationSection exp = (OrganizationSection) resume.getSection(EXPERIENCE);
            addListOrganizations(dos, exp);
            OrganizationSection education = (OrganizationSection) resume.getSection(EDUCATION);
            addListOrganizations(dos, education);
        }
    }

    private static void addListOrganizations(DataOutputStream dos, OrganizationSection exp) throws IOException {
        List<Organization> organizationList = exp.getOrganizationList();
        for (Organization organization : organizationList) {
            dos.writeUTF(organization.getName());
            URL url = organization.getWebsite();
            dos.writeUTF(url.toString());
            addPeriods(dos, organization);
        }
    }

    private static void addPeriods(DataOutputStream dos, Organization organization) throws IOException {
        List<Organization.Period> periods = organization.getPeriods();
        for (Organization.Period period : periods) {
            dos.writeUTF(period.getTitle());
            dos.writeUTF(period.getDescription());
            dos.writeUTF(String.valueOf(period.getStartDate()));
            dos.writeUTF(String.valueOf(period.getEndDate()));
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
            TextSection personal = new TextSection();
            personal.setContent(dis.readUTF());
            resume.addSection(PERSONAL, personal);

            TextSection objective = new TextSection();
            objective.setContent(dis.readUTF());
            resume.addSection(OBJECTIVE, objective);

            ListSection achivement = new ListSection();
            List<String> items = new ArrayList<>();
            items.add(dis.readUTF());
            items.add(dis.readUTF());
            achivement.setItems(items);
            resume.addSection(ACHIEVEMENT, achivement);

            ListSection qualifications = new ListSection();
            List<String> items1 = new ArrayList<>();
            items1.add(dis.readUTF());
            items1.add(dis.readUTF());
            qualifications.setItems(items1);
            resume.addSection(QUALIFICATIONS, qualifications);



            // TODO implements sections
            return resume;
        }
    }
}
