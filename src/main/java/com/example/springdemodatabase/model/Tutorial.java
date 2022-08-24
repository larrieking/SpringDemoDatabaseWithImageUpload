package com.example.springdemodatabase.model;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Table(name="Tutorials")
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Column(name="image")
    @Lob
    private byte[] file;

    @Column(name="title")

    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published")
    private boolean published;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public boolean isPublished() {
        return published;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Tutorial() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public Tutorial(String title, String description, boolean published, byte[] img, Status status) {
        this.title = title;
        this.description = description;
        this.published = published;
        this.file =  img;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Tutorial{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", published='" + published + '\'' +
                '}';
    }
}
