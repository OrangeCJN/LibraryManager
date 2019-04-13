package com.example.bookmanager.firebase;

import android.content.Context;

import com.example.bookmanager.Constant;
import com.example.bookmanager.callback.FirebaseDeleteResult;
import com.example.bookmanager.callback.FirebaseInsertResult;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.callback.FirebaseUpdateResult;
import com.example.bookmanager.model.ActionInfo;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Comment;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.LogUtil;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Firebase data fetcher interface implementation, singleton
 */
public class DataFetcherImpl implements IDataFetcher {

    // Context object
    private Context mContext;
    // Firebase database reference
    private DatabaseReference mDatabaseReference;
    // singleton object
    private static IDataFetcher sDataFetcher = null;

    private DataFetcherImpl(Context context) {
        mContext = context;
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    /**
     * get singleton object of IDataFetcher
     *
     * @param context
     * @return
     */
    public static IDataFetcher getDataFetcher(Context context) {
        // only create new instance if it's null
        if (sDataFetcher == null) {
            synchronized (String.class) {   // thread safe
                if (sDataFetcher == null) {
                    sDataFetcher = new DataFetcherImpl(context);
                }
            }
        }
        return sDataFetcher;
    }

    /**
     * fetch administrator
     *
     * @param firebaseQueryResult
     */
    @Override
    public void fetchAdmin(final FirebaseQueryResult firebaseQueryResult) {
        getDatabaseReference(Constant.FIREBASE_DATA_ADMINS).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                handlerSuccess(dataSnapshot, firebaseQueryResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                handlerError(databaseError);
            }
        });
    }

    /**
     * register user
     *
     * @param student
     * @param firebaseInsertResult
     */
    @Override
    public void registerUser(final Student student, final FirebaseInsertResult firebaseInsertResult) {
        getDatabaseReference(Constant.FIREBASE_DATA_STUDENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                handlerSuccess(dataSnapshot, new FirebaseQueryResult() {
                    @Override
                    public void success(DataSnapshot dataSnapshot) {
                        if (FilterUtil.filterStudent(dataSnapshot, student.getStudent_code()) == null) {
                            mDatabaseReference.child(Constant.FIREBASE_DATA_STUDENTS).child(getDataId()).setValue(student, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                    if (databaseError == null && databaseReference != null) {
                                        firebaseInsertResult.insertResult(true, "Operation is successful");
                                    } else {
                                        firebaseInsertResult.insertResult(false, databaseError.getMessage());
                                    }
                                }
                            });
                        } else {
                            ToastUtil.tost(mContext, "The username has been registered");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                handlerError(databaseError);
            }
        });
    }

    /**
     * get all users(students)
     *
     * @param firebaseQueryResult
     */
    @Override
    public void getAllStudent(final FirebaseQueryResult firebaseQueryResult) {
        getDatabaseReference(Constant.FIREBASE_DATA_STUDENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handlerSuccess(dataSnapshot, firebaseQueryResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                handlerError(databaseError);
            }
        });
    }

    /**
     * update user(student) info
     *
     * @param student
     * @param firebaseUpdateResult
     */
    @Override
    public void updateStudent(final Student student, final FirebaseUpdateResult firebaseUpdateResult, boolean codeIsChange) {
        if (codeIsChange) {
            getDatabaseReference(Constant.FIREBASE_DATA_STUDENTS).addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    handlerSuccess(dataSnapshot, new FirebaseQueryResult() {
                        @Override
                        public void success(DataSnapshot dataSnapshot) {
                            if (FilterUtil.filterStudent(dataSnapshot, student.getStudent_code()) == null) {
                                mDatabaseReference.child(Constant.FIREBASE_DATA_STUDENTS).child(student.getKey()).setValue(student, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                        if (databaseError == null && databaseReference != null) {
                                            firebaseUpdateResult.updateResult(true, "Updated successfully");
                                        } else {
                                            firebaseUpdateResult.updateResult(false, databaseError.getMessage());
                                        }
                                    }
                                });
                            } else {
                                ToastUtil.tost(mContext, "The username has been registered");
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    handlerError(databaseError);
                }
            });
        } else {
            mDatabaseReference.child(Constant.FIREBASE_DATA_STUDENTS).child(student.getKey()).setValue(student, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    if (databaseError == null && databaseReference != null) {
                        firebaseUpdateResult.updateResult(true, "Updated successfully");
                    } else {
                        firebaseUpdateResult.updateResult(false, databaseError.getMessage());
                    }
                }
            });
        }
    }

    /**
     * delete user
     *
     * @param firebaseDeleteResult
     */
    @Override
    public void deleteUser(Student student, final FirebaseDeleteResult firebaseDeleteResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_STUDENTS).child(student.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseDeleteResult.deleteResult(true, "Deleted successfully");
                } else {
                    firebaseDeleteResult.deleteResult(false, databaseError.getMessage());
                }
            }
        });
    }

    /**
     * add book
     *
     * @param book
     * @param firebaseInsertResult
     */
    @Override
    public void addBook(Book book, final FirebaseInsertResult firebaseInsertResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_BOOKS).child(getDataId()).setValue(book, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseInsertResult.insertResult(true, "Operation is successful");
                } else {
                    firebaseInsertResult.insertResult(false, databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void getAllBook(final FirebaseQueryResult firebaseQueryResult) {
        getDatabaseReference(Constant.FIREBASE_DATA_BOOKS).addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handlerSuccess(dataSnapshot, firebaseQueryResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                handlerError(databaseError);
            }
        });
    }

    @Override
    public void deleteBook(Book book, final FirebaseDeleteResult firebaseDeleteResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_BOOKS).child(book.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseDeleteResult.deleteResult(true, "Deleted successfully");
                } else {
                    firebaseDeleteResult.deleteResult(false, databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void updateBook(Book book, final FirebaseUpdateResult firebaseUpdateResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_BOOKS).child(book.getKey()).setValue(book, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseUpdateResult.updateResult(true, "Updated successfully");
                } else {
                    firebaseUpdateResult.updateResult(false, databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void addActivity(ActionInfo actionInfo, final FirebaseInsertResult firebaseInsertResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_ACTIONS).child(getDataId()).setValue(actionInfo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseInsertResult.insertResult(true, "Operation is successful");
                } else {
                    firebaseInsertResult.insertResult(false, databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void getAllActivity(final FirebaseQueryResult firebaseQueryResult) {
        getDatabaseReference(Constant.FIREBASE_DATA_ACTIONS).addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handlerSuccess(dataSnapshot, firebaseQueryResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                handlerError(databaseError);
            }
        });
    }

    @Override
    public void deleteActivity(ActionInfo actionInfo, final FirebaseDeleteResult firebaseDeleteResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_ACTIONS).child(actionInfo.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseDeleteResult.deleteResult(true, "Deleted successfully");
                } else {
                    firebaseDeleteResult.deleteResult(false, databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void updateActivity(ActionInfo actionInfo, final FirebaseUpdateResult firebaseUpdateResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_ACTIONS).child(actionInfo.getKey()).setValue(actionInfo, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseUpdateResult.updateResult(true, "Updated successfully");
                } else {
                    firebaseUpdateResult.updateResult(false, databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void addComment(Comment comment, final FirebaseInsertResult firebaseInsertResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_COMMENTS).child(getDataId()).setValue(comment, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseInsertResult.insertResult(true, "Operation is successful");
                } else {
                    firebaseInsertResult.insertResult(false, databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public void getAllComment(final FirebaseQueryResult firebaseQueryResult) {
        getDatabaseReference(Constant.FIREBASE_DATA_COMMENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handlerSuccess(dataSnapshot, firebaseQueryResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                handlerError(databaseError);
            }
        });
    }

    @Override
    public void deleteComment(Comment comment, final FirebaseDeleteResult firebaseDeleteResult) {
        mDatabaseReference.child(Constant.FIREBASE_DATA_COMMENTS).child(comment.getKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null && databaseReference != null) {
                    firebaseDeleteResult.deleteResult(true, "Deleted successfully");
                } else {
                    firebaseDeleteResult.deleteResult(false, databaseError.getMessage());
                }
            }
        });
    }

    /**
     * fetch user
     *
     * @param firebaseQueryResult
     */
    @Override
    public void fetchUser(final FirebaseQueryResult firebaseQueryResult) {
        getDatabaseReference(Constant.FIREBASE_DATA_STUDENTS).addListenerForSingleValueEvent(new ValueEventListener() {
            //@Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                handlerSuccess(dataSnapshot, firebaseQueryResult);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                handlerError(databaseError);
            }
        });
    }

    /**
     * handle successful data fetch
     *
     * @param dataSnapshot
     * @param firebaseQueryResult
     */
    private void handlerSuccess(DataSnapshot dataSnapshot, FirebaseQueryResult firebaseQueryResult) {
        if (firebaseQueryResult != null) {
            firebaseQueryResult.success(dataSnapshot);
        }
    }

    /**
     * handle request error, check network warning
     *
     * @param databaseError
     */
    private void handlerError(DatabaseError databaseError) {
        if (mContext instanceof BaseActivity) {
            ToastUtil.tost(mContext, "Network error, please check the network connection");
        }
        if (databaseError != null) {
            LogUtil.e(databaseError.getMessage());
        }
    }

    /**
     * get current time in millisecond as the unique data id
     *
     * @return
     */
    private String getDataId() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * get database reference according to the path name
     *
     * @param name
     * @return
     */
    private DatabaseReference getDatabaseReference(String name) {
        if (mDatabaseReference != null) {
            return mDatabaseReference.child(name);
        }
        return null;
    }
}
