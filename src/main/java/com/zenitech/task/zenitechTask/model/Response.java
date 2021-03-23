package com.zenitech.task.zenitechTask.model;

import javax.persistence.*;

@Entity
@Table(name = "responses")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "is_Posted")
    private boolean posted;

    public Response() {
    }

    public Response(String title, String description, boolean posted) {
        this.title = title;
        this.description = description;
        this.posted = posted;
    }

    public long getId() {
        return id;
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

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean isPosted) {
        this.posted = isPosted;
    }

    @Override
    public String toString() {
        return "Response [id=" + id + ", title=" + title + ", desc=" + description + ", posted=" + posted + "]";
    }
}
