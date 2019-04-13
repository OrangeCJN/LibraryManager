package com.example.bookmanager.callback;

/**
 * callback interface of delete data
 * e.g. user registration
 */
public interface FirebaseDeleteResult {

    /**
     * delete result callback
     * @param success true if success
     * @param msg prompt message
     */
    void deleteResult(boolean success, String msg);
}
