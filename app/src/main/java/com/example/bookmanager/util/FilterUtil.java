package com.example.bookmanager.util;

import com.example.bookmanager.model.Admin;
import com.example.bookmanager.model.BaseBean;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Comment;
import com.example.bookmanager.model.Student;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * data filter utility class
 */
public class FilterUtil {

    /**
     * check from DataSnapshot if the specified administrator account exist
     *
     * @param dataSnapshot
     * @param n            user name
     * @param p            password
     * @return
     */
    public static Admin filterAdmin(DataSnapshot dataSnapshot, String n, String p) {
        if (dataSnapshot.hasChildren()) {
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
                DataSnapshot next = iterator.next();
                Admin model = next.getValue(Admin.class);
                if (n.equals(model.getUsername()) && p.equals(model.getPassword())) {
                    return (Admin) model.setKey(next.getKey());
                }
            }
        }
        return null;
    }

    /**
     * check from DataSnapshot if the specified book info exists
     *
     * @param dataSnapshot
     * @param id           user name
     * @return
     */
    public static Book filterBook(DataSnapshot dataSnapshot, String id) {
        if (dataSnapshot.hasChildren()) {
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
                DataSnapshot next = iterator.next();
                Book model = next.getValue(Book.class);
                if (id.equals(model.getId() + "")) {
                    return (Book) model.setKey(next.getKey());
                }
            }
        }
        return null;
    }

    /**
     * check from DataSnapshot if the specified student account exists
     *
     * @param dataSnapshot
     * @param n            user name
     * @return
     */
    public static Student filterStudent(DataSnapshot dataSnapshot, String n) {
        if (dataSnapshot.hasChildren()) {
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
                DataSnapshot next = iterator.next();
                Student model = next.getValue(Student.class);
                if (n.equals(model.getStudent_code())) {
                    return (Student) model.setKey(next.getKey());
                }
            }
        }
        return null;
    }

    /**
     * check from DataSnapshot if the specified administrator account exists
     *
     * @param dataSnapshot
     * @param n            user name
     * @param p            password
     * @return
     */
    public static Student filterStudent(DataSnapshot dataSnapshot, String n, String p) {
        if (dataSnapshot.hasChildren()) {
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
                DataSnapshot next = iterator.next();
                Student model = next.getValue(Student.class);
                if (n.equals(model.getStudent_code()) && MD5Util.MD5Encode(p).equals(model.getPassword())) {
                    return (Student) model.setKey(next.getKey());
                }
            }
        }
        return null;
    }

    /**
     * convert DataSnapshot into set
     *
     * @param dataSnapshot
     * @return
     */
    public static <T extends BaseBean> List<T> filterDataList(DataSnapshot dataSnapshot, Class clazz) {
        List<T> data = new ArrayList<>();
        if (dataSnapshot.hasChildren()) {
            Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
            while (iterator.hasNext()) {
                DataSnapshot next = iterator.next();
                data.add((T) ((T) (next.getValue(clazz))).setKey(next.getKey()));
            }
        }
        return data;
    }

    /**
     * select your comment
     * @param source
     * @param username
     * @return
     */
    public static List<Comment> filterYourComment(List<Comment> source, String username) {
        if (source == null || source.size() == 0) {
            return source;
        }
        List<Comment> data = new ArrayList<>();
        for (Comment c : source) {
            if (c != null && c.getUsername().equals(username)) {
                data.add(c);
            }
        }
        return data;
    }

}
