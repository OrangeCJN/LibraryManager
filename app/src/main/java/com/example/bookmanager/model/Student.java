package com.example.bookmanager.model;

import java.io.Serializable;
import java.util.List;

/**
 * student entity class
 */
public class Student extends BaseBean implements Serializable {

    private String student_name;
    private String student_code;
    private String password;
    private List<Long> books;

    public Student() {
    }

    public Student(String student_name, String student_code, String password, List<Long> books) {
        this.student_name = student_name;
        this.student_code = student_code;
        this.password = password;
        this.books = books;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getBooks() {
        return books;
    }

    public void setBooks(List<Long> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return student_code != null ? student_code.equals(student.student_code) : student.student_code == null;
    }

    @Override
    public int hashCode() {
        return student_code != null ? student_code.hashCode() : 0;
    }
}
