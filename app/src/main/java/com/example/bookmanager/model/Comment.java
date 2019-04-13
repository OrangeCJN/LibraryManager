package com.example.bookmanager.model;

import java.io.Serializable;

/**
 * comment entity class
 */
public class Comment extends BaseBean implements Serializable {

    private String date;
    private String content;
    private String name;
    private String username;

    public Comment() {
    }

    public Comment(String date, String content, String name, String username) {
        this.date = date;
        this.content = content;
        this.name = name;
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (date != null ? !date.equals(comment.date) : comment.date != null) return false;
        if (content != null ? !content.equals(comment.content) : comment.content != null)
            return false;
        if (name != null ? !name.equals(comment.name) : comment.name != null) return false;
        return username != null ? username.equals(comment.username) : comment.username == null;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
