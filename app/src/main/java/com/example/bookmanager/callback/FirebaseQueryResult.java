package com.example.bookmanager.callback;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * callback interface of data query
 * e.g. searching user
 */
public interface FirebaseQueryResult {

    /**
     * data query callback
     * @param dataSnapshot
     */
    void success(DataSnapshot dataSnapshot);
}
