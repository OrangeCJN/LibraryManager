package com.example.bookmanager.callback;

/**
 * callback interface of update data
 * e.g. user registration
 */
public interface FirebaseUpdateResult {

    /**
     * update result callback
     * @param success true if success
     * @param msg prompt message
     */
    void updateResult(boolean success, String msg);
}
