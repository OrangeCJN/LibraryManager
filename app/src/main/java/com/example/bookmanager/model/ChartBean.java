package com.example.bookmanager.model;

// work 3.2
public class ChartBean {

    private int have;
    private int borrow;
    private String bookName;

    public ChartBean() {
    }

    public ChartBean(int have, int borrow, String bookName) {
        this.have = have;
        this.borrow = borrow;
        this.bookName = bookName;
    }

    public int getHave() {
        return have;
    }

    public void setHave(int have) {
        this.have = have;
    }

    public int getBorrow() {
        return borrow;
    }

    public void setBorrow(int borrow) {
        this.borrow = borrow;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
