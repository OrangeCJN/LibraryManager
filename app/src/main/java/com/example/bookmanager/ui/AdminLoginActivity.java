package com.example.bookmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.SPUtils;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;

/**
 * administrator login activity
 */
public class AdminLoginActivity extends BaseActivity implements View.OnClickListener {

    // widgets
    private EditText nick;
    private EditText pwd;
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Administrator");
        nick = (EditText) findViewById(R.id.nick);
        pwd = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_admin;
    }

    /**
     * login click event listener
     * @param v
     */
    @Override
    public void onClick(View v) {
        SPUtils.init(this);
        // check if the inputs are complete
        final String username = nick.getText().toString();
        final String password = pwd.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtil.tost(mActivity, "The username or password cannot be empty");
            return;
        }
        // login event
        if (v == login) {
            // check if the username matches the password
            mIDataFetcher.fetchAdmin(new FirebaseQueryResult() {
                @Override
                public void success(DataSnapshot dataSnapshot) {
                    if (FilterUtil.filterAdmin(dataSnapshot, username, password) != null) {
                        SPUtils.putString(Constant.SP_USER_TYPE, Constant.USER_TYPE_ADMIN);
                        SPUtils.putString(Constant.SP_USER_USER_NAME, username);
                        ToastUtil.tost(mActivity, "Login in successfully");
                        startActivity(new Intent(mActivity, AdminHomeActivity.class));
                        finish();
                    } else {
                        ToastUtil.tost(mActivity, "User name or password error");
                    }
                }
            });
        }
    }

}
