package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    public Organization() {
    }

    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private URL website;
    private List<Period> periods;

    public Organization(String name, URL website, List<Period> periods) {
        this.name = name;
        this.website = website;
        this.periods = periods;
    }

    public String getName() {
        return name;
    }

    public URL getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(name, that.name) && Objects.equals(website, that.website) && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website, periods);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "name='" + name + '\'' +
                ", website=" + website +
                ", periods=" + periods +
                '}';
    }

    public static class Period implements Serializable {
        public Period() {
        }

        @Serial
        private static final long serialVersionUID = 1L;

        private String title;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;

//        public Period(int startYear, Month startMonth, String title, String description) {
//            this(DateUtil.of(startYear, startMonth), NOW, title, description);
//        }
//
//        public Period(int startYear, Month startMonth, int endYear, Month endMonth, String title, String description) {
//            this(of(startYear, startMonth), of(endYear, endMonth), title, description);
//        }

        public Period(String title, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(title, "title  must not be null");
            Objects.requireNonNull(description, "description  must not be null");
            Objects.requireNonNull(startDate, "startDate  must not be null");
            Objects.requireNonNull(endDate, "endDate  must not be null");
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Period period = (Period) o;

            if (!Objects.equals(title, period.title)) return false;
            if (!Objects.equals(description, period.description)) return false;
            if (!Objects.equals(startDate, period.startDate)) return false;
            return Objects.equals(endDate, period.endDate);
        }

        @Override
        public int hashCode() {
            int result = title.hashCode();
            result = 31 * result + description.hashCode();
            result = 31 * result + startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    '}' + "\n";
        }
    }
}
