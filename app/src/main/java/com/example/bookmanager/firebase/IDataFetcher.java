package com.example.bookmanager.firebase;

import com.example.bookmanager.callback.FirebaseDeleteResult;
import com.example.bookmanager.callback.FirebaseInsertResult;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.callback.FirebaseUpdateResult;
import com.example.bookmanager.model.ActionInfo;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Comment;
import com.example.bookmanager.model.Student;

/**
 * Firebase data fetcher interface
 */
public interface IDataFetcher {

    // fetch user
    void fetchUser(FirebaseQueryResult firebaseQueryResult);

    // fetch administrator
    void fetchAdmin(FirebaseQueryResult firebaseQueryResult);

    // register user
    void registerUser(Student student, FirebaseInsertResult firebaseInsertResult);

    // get all users(students)
    void getAllStudent(FirebaseQueryResult firebaseQueryResult);

    // update user(student) info
    void updateStudent(Student student, FirebaseUpdateResult firebaseUpdateResult, boolean codeIsChange);

    // delete user
    void deleteUser(Student student, FirebaseDeleteResult firebaseDeleteResult);

    // add book
    void addBook(Book book, FirebaseInsertResult firebaseInsertResult);

    // get all book
    void getAllBook(FirebaseQueryResult firebaseQueryResult);

    // delete book
    void deleteBook(Book book, FirebaseDeleteResult firebaseDeleteResult);

    // update book info
    void updateBook(Book book, FirebaseUpdateResult firebaseUpdateResult);

    // add book
    void addActivity(ActionInfo actionInfo, FirebaseInsertResult firebaseInsertResult);

    // get all ActionInfo
    void getAllActivity(FirebaseQueryResult firebaseQueryResult);

    // delete Activity
    void deleteActivity(ActionInfo actionInfo, FirebaseDeleteResult firebaseDeleteResult);

    // update Activity info
    void updateActivity(ActionInfo actionInfo, FirebaseUpdateResult firebaseUpdateResult);

    // add Comment
    void addComment(Comment comment, FirebaseInsertResult firebaseInsertResult);

    // get all Comment
    void getAllComment(FirebaseQueryResult firebaseQueryResult);

    // delete Comment
    void deleteComment(Comment comment, FirebaseDeleteResult firebaseDeleteResult);

}
