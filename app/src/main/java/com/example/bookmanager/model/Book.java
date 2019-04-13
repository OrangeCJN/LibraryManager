package com.example.bookmanager.model;

import java.io.Serializable;
import java.util.List;

/**
 * book entity class
 */
public class Book extends BaseBean implements Serializable {

    private long id;
    private String name;
    private String author;
    private String introduce;
    private String imageUrl;
    private String pressName;
    private List<String> users; // borrower List<String>
    private int count; // total book count

    public Book() {
    }

    public Book(long id) {
        this.id = id;
    }

    public Book(long id, String name, String author, String introduce, String imageUrl, String pressName, List<String> users, int count) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.introduce = introduce;
        this.imageUrl = imageUrl;
        this.pressName = pressName;
        this.users = users;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPressName() {
        return pressName;
    }

    public void setPressName(String pressName) {
        this.pressName = pressName;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id == book.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
