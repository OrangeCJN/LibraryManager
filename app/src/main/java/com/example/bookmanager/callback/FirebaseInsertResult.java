package com.example.bookmanager.callback;

/**
 * callback interface of inserting data
 * e.g. user registration
 */
public interface FirebaseInsertResult {

    /**
     * insert result callback
     * @param success true if success
     * @param msg prompt message
     */
    void insertResult(boolean success, String msg);
}
