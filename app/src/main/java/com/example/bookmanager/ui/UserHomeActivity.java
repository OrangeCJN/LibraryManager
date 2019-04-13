package com.example.bookmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.ui.dialog.PushCommentDialog;
import com.example.bookmanager.ui.dialog.UserPasswordDialog;
import com.example.bookmanager.util.SPUtils;

/**
 * common user home
 */
public class UserHomeActivity extends BaseActivity {

    private Student mStudent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Welcome, " + SPUtils.getString(Constant.SP_USER_USER_NAME));
        mStudent = (Student) getIntent().getSerializableExtra("student");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.exit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            moveTaskToBack(true);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_user_home;
    }

    /**
     * change password dialog
     */
    public void changePassword(View view) {
        // show change password dialog
        new UserPasswordDialog(this, mStudent).show();
    }

    /**
     * jump to books page
     */
    public void borrowBooks(View view) {
        // goto BooksActivity (Distinguish user from admin)
        startActivity(new Intent(this, BooksActivity.class));
    }

    /**
     * jump to activity & event page
     */
    public void lookActivities(View view) {
        // goto ActivitiesActivity (Distinguish user from admin)
        startActivity(new Intent(this, ActivitiesActivity.class));
    }

    /**
     * post comment message dialog
     */
    public void pushMessage(View view) {
        startActivity(new Intent(this, CommentsActivity.class));
    }

    /**
     * go back event listener: move program to background
     */
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void finish() {
        moveTaskToBack(true);
    }
}
