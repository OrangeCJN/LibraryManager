package com.example.bookmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bookmanager.R;
import com.example.bookmanager.ui.base.BaseActivity;

/**
 * main activity
 */
public class MainActivity extends BaseActivity {

    // widgets
    private TextView normal;
    private TextView admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        normal = (TextView) findViewById(R.id.normal);
        admin = (TextView) findViewById(R.id.admin);

        // set click event listener for normal(student) user
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, UserLoginActivity.class));
            }
        });

        // set click event listener for administrator
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, AdminLoginActivity.class));
            }
        });

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }
}
