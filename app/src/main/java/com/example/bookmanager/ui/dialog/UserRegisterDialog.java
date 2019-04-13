package com.example.bookmanager.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseInsertResult;
import com.example.bookmanager.firebase.DataFetcherImpl;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.util.MD5Util;
import com.example.bookmanager.util.SPUtils;
import com.example.bookmanager.util.ToastUtil;

import java.util.ArrayList;

/**
 * User registration dialog
 */
public class UserRegisterDialog extends Dialog implements View.OnClickListener {

    // widgets
    private EditText name;
    private EditText username;
    private EditText password;
    private EditText confirm;
    private TextView cancel;
    private TextView save;
    private TextView tip;
    private Context context;

    private Student mStudent;
    private AddUserCallback mAddUserCallback;

    public UserRegisterDialog setAddUserCallback(AddUserCallback addUserCallback) {
        mAddUserCallback = addUserCallback;
        return this;
    }

    // constructor
    public UserRegisterDialog(Activity context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * display user registration dialog
     */
    @Override
    public void show() {
        super.show();
    }

    /**
     * dialog initialization
     */
    private void init() {
        // set to no title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // set background to transparent
        getWindow().getDecorView().setBackgroundColor(0x00000000);
        // set layout
        setContentView(R.layout.user_register);
        // set to not cancelable
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        // get widgets
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.pass);
        confirm = (EditText) findViewById(R.id.confirm);
        cancel = (TextView) findViewById(R.id.cancel);
        save = (TextView) findViewById(R.id.save);
        tip = (TextView) findViewById(R.id.tip);

        if (SPUtils.getString(Constant.SP_USER_TYPE).equals(Constant.USER_TYPE_ADMIN)) {
            tip.setText("Name");
        }

        // set click event
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);

    }

    /**
     * click event listener
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save:
                // check if information entered is complete and legit
                final String nameString = name.getText().toString();
                final String usernameString = username.getText().toString();
                final String passwordString = password.getText().toString();
                final String confirmString = confirm.getText().toString();
                if (TextUtils.isEmpty(nameString)
                        || TextUtils.isEmpty(usernameString)
                        || TextUtils.isEmpty(passwordString)
                        || TextUtils.isEmpty(confirmString)) {
                    ToastUtil.tost(context, "Please enter full information");
                    return;
                }
                if (!passwordString.equals(confirmString)) {
                    Toast.makeText(context, "The passwords entered do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (usernameString.length() < 6 || passwordString.length() < 6) {
                    ToastUtil.tost(context, "Neither user name nor password should be less than 6");
                    return;
                }
                // try inserting data into Firebase
                DataFetcherImpl.getDataFetcher(context).registerUser(mStudent = new Student(nameString,
                        usernameString, MD5Util.MD5Encode(passwordString), new ArrayList<Long>()), new FirebaseInsertResult() {
                    @Override
                    public void insertResult(boolean success, String msg) {
                        ToastUtil.tost(context, msg);
                        if (mAddUserCallback != null) {
                            mAddUserCallback.addUserResult(success, msg, mStudent);
                        }
                    }
                });
                break;
        }
        dismiss();
    }

    public interface AddUserCallback {
        void addUserResult(boolean success, String msg, Student student);
    }

}
