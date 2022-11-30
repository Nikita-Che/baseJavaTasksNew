package com.urise.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.net.URL;
import java.util.Objects;

public class Link implements Serializable {
    public Link() {
    }

    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private URL url;

    public Link(String name, URL url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link link = (Link) o;

        if (!name.equals(link.name)) return false;
        return url.equals(link.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url=" + url +
                '}';
    }
}
