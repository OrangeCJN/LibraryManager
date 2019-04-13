package com.example.bookmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.ui.dialog.UserRegisterDialog;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.SPUtils;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;

/**
 * user(student) login activity
 */
public class UserLoginActivity extends BaseActivity implements View.OnClickListener, UserRegisterDialog.AddUserCallback {

    // widgets
    private EditText nick;
    private EditText pwd;
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("User");
        nick = (EditText) findViewById(R.id.nick);
        pwd = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_person;
    }

    /**
     * user register click event listener
     *
     * @param v
     */
    public void register(View v) {
        new UserRegisterDialog(this).setAddUserCallback(this).show();
    }

    /**
     * login click event listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        SPUtils.init(this);
        final String username = nick.getText().toString();
        final String password = pwd.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtil.tost(mActivity, "The username or password cannot be empty");
            return;
        }
        if (v == login) {
            // check if the username matches the password
            mIDataFetcher.fetchUser(new FirebaseQueryResult() {
                @Override
                public void success(DataSnapshot dataSnapshot) {
                    Student student;
                    if ((student = FilterUtil.filterStudent(dataSnapshot, username, password)) != null) {
                        // save user's info and goto user's home
                        SPUtils.putString(Constant.SP_USER_TYPE, Constant.USER_TYPE_USER);
                        SPUtils.putString(Constant.SP_USER_USER_NAME, student.getStudent_code());
                        SPUtils.putString(Constant.SP_USER_NAME, student.getStudent_name());
                        SPUtils.putString(Constant.SP_USER_PASSWORD, student.getPassword());
                        ToastUtil.tost(mActivity, "Login in successfully");
                        startActivity(new Intent(mActivity, UserHomeActivity.class)
                                .putExtra("student", student));
                        finish();
                    } else {
                        ToastUtil.tost(mActivity, "User name or password error");
                    }
                }
            });
        }
    }

    /**
     * callback of user registration result
     *
     * @param success
     * @param msg
     * @param student
     */
    @Override
    public void addUserResult(boolean success, String msg, Student student) {
        if (success && student != null) {
            // save user's info and goto user's home
            SPUtils.putString(Constant.SP_USER_TYPE, Constant.USER_TYPE_USER);
            SPUtils.putString(Constant.SP_USER_USER_NAME, student.getStudent_code());
            SPUtils.putString(Constant.SP_USER_NAME, student.getStudent_name());
            SPUtils.putString(Constant.SP_USER_PASSWORD, student.getPassword());
            startActivity(new Intent(mActivity, UserHomeActivity.class)
                    .putExtra("student", student));
        }
    }
}
