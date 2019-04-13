package com.example.bookmanager.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.bookmanager.R;
import com.example.bookmanager.ui.base.BaseActivity;

/**
 * administrator home
 */
public class AdminHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Welcome, admin");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            moveTaskToBack(true);
            return true;
        }
        if (item.getItemId() == R.id.statistical) {
            final String[] items = {"Books Statistical", "Messages Statistical"};
            AlertDialog.Builder listDialog =
                    new AlertDialog.Builder(mActivity);
            listDialog.setTitle("Choose");
            listDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        startActivity(new Intent(mActivity, BooksStatisticalActivity.class));
                    } else {
                        startActivity(new Intent(mActivity, MessagesStatisticalActivity.class));
                    }
                }
            });
            listDialog.show();
            return true;
        }
        if (item.getItemId() == R.id.artifact) {
            startActivity(new Intent(this, AgreeBorrowActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_admin_home;
    }

    /**
     * jump to user accounts manager page
     *
     * @param view
     */
    public void userManager(View view) {
        startActivity(new Intent(this, UserManagerActivity.class));
    }

    /**
     * jump to books manager page
     *
     * @param view
     */
    public void bookManager(View view) {
        startActivity(new Intent(this, BooksActivity.class));
    }

    /**
     * jump to activity & event manager page
     *
     * @param view
     */
    public void activityManager(View view) {
        startActivity(new Intent(this, ActivitiesActivity.class));
    }

    /**
     * jump to comment message manager page
     *
     * @param view
     */
    public void messageManager(View view) {
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
