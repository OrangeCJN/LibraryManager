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

import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseInsertResult;
import com.example.bookmanager.callback.FirebaseUpdateResult;
import com.example.bookmanager.firebase.DataFetcherImpl;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.util.MD5Util;
import com.example.bookmanager.util.ToastUtil;

import java.util.ArrayList;

/**
 * User update dialog
 */
public class UserUpdateDialog extends Dialog implements View.OnClickListener {

    // widgets
    private EditText name;
    private EditText password;
    private EditText confirm;
    private TextView cancel;
    private TextView save;
    private Context context;
    private Student mStudent;
    private TextView tip;

    // constructor
    public UserUpdateDialog(Activity context, Student student) {
        super(context);
        this.context = context;
        this.mStudent = student;
        init();
    }

    /**
     * display user update dialog
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
        setContentView(R.layout.user_update);
        // set to not cancelable
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        // get widgets
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.pass);
        confirm = (EditText) findViewById(R.id.confirm);
        cancel = (TextView) findViewById(R.id.cancel);
        save = (TextView) findViewById(R.id.save);
        tip = (TextView) findViewById(R.id.tip);

        tip.setText("Name");

        save.setText("Save");
        name.setText(mStudent.getStudent_name());
        name.setSelection(name.getText().length());

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
                boolean noChangePwd = false;
                // check if information entered is complete and legit
                String nameString = name.getText().toString();
                String passwordString = password.getText().toString();
                String confirmString = confirm.getText().toString();
                if (TextUtils.isEmpty(nameString)
                        || (TextUtils.isEmpty(passwordString)
                        && !TextUtils.isEmpty(confirmString))
                        || (!TextUtils.isEmpty(passwordString)
                        && TextUtils.isEmpty(confirmString))) {
                    ToastUtil.tost(context, "Please enter full information");
                    return;
                }
                if(TextUtils.isEmpty(passwordString)){
                    noChangePwd = true;
                    passwordString = mStudent.getPassword();
                    confirmString = mStudent.getPassword();
                }
                if (!noChangePwd && !passwordString.equals(confirmString)) {
                    Toast.makeText(context, "The passwords entered do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordString) && passwordString.length() < 6) {
                    ToastUtil.tost(context, "Neither user name nor password should be less than 6");
                    return;
                }
                final String oldPassword = mStudent.getPassword();
                final String oldCode = mStudent.getStudent_code();
                final String oldName = mStudent.getStudent_name();
                if(noChangePwd){
                    mStudent.setPassword(passwordString);
                }else {
                    mStudent.setPassword(MD5Util.MD5Encode(passwordString));
                }
                mStudent.setStudent_name(nameString);
                // try inserting data into Firebase
                DataFetcherImpl.getDataFetcher(context).updateStudent(mStudent, new FirebaseUpdateResult() {
                    @Override
                    public void updateResult(boolean success, String msg) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        if(!success){
                            mStudent.setPassword(oldPassword);
                            mStudent.setStudent_name(oldName);
                            mStudent.setStudent_code(oldCode);
                        }
                    }
                }, false);
                break;
        }
        dismiss();
    }

}
