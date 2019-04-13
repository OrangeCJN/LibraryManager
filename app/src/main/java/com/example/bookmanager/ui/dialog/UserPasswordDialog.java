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
import com.example.bookmanager.callback.FirebaseUpdateResult;
import com.example.bookmanager.firebase.DataFetcherImpl;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.util.MD5Util;
import com.example.bookmanager.util.SPUtils;
import com.example.bookmanager.util.ToastUtil;

/**
 * 普通用户修改姓名与密码的弹框
 */
public class UserPasswordDialog extends Dialog implements View.OnClickListener {

    private EditText sname;
    private EditText oldPass;
    private EditText newPass;
    private EditText confirm;
    private TextView cancel;
    private TextView save;

    private Student mStudent;

    private Context context;

    public UserPasswordDialog(Activity context, Student student) {
        super(context);
        this.context = context;
        this.mStudent = student;
        init();
    }

    /**
     * display user registration dialog
     */
    @Override
    public void show() {
        super.show();
        SPUtils.init(context);
    }

    /**
     * dialog initialization
     */
    private void init() {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setBackgroundColor(0x00000000);
        setContentView(R.layout.user_cp);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        sname = (EditText) findViewById(R.id.sname);
        oldPass = (EditText) findViewById(R.id.old_pass);
        newPass = (EditText) findViewById(R.id.new_pass);
        confirm = (EditText) findViewById(R.id.confirm);
        cancel = (TextView) findViewById(R.id.cancel);
        save = (TextView) findViewById(R.id.save);

        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.save).setOnClickListener(this);

        sname.setText(mStudent.getStudent_name());
        sname.setSelection(sname.getText().length());

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
                String snames = sname.getText().toString();
                String oldPasss = oldPass.getText().toString();
                String newPasss = newPass.getText().toString();
                String confirms = confirm.getText().toString();
                if (TextUtils.isEmpty(snames)
                        || (TextUtils.isEmpty(newPasss)
                        && !TextUtils.isEmpty(confirms))
                        || (!TextUtils.isEmpty(newPasss)
                        && TextUtils.isEmpty(confirms))) {
                    ToastUtil.tost(context, "Please enter full information");
                    return;
                }
                if (TextUtils.isEmpty(newPasss) && TextUtils.isEmpty(confirms) && TextUtils.isEmpty(oldPasss)) {
                    noChangePwd = true;
                    oldPasss = newPasss = confirms = mStudent.getPassword();
                } else if(TextUtils.isEmpty(newPasss) || TextUtils.isEmpty(confirms) || TextUtils.isEmpty(oldPasss)){
                    ToastUtil.tost(context, "Please enter full information");
                    return;
                }
                if (!noChangePwd && !MD5Util.MD5Encode(oldPasss).equals(mStudent.getPassword())) {
                    Toast.makeText(context, "The original password does not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPasss.equals(confirms)) {
                    Toast.makeText(context, "The passwords entered do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!TextUtils.isEmpty(newPasss) && newPasss.length() < 6) {
                    ToastUtil.tost(context, "Password should be less than 6");
                    return;
                }
                mStudent.setStudent_name(snames);
                if (noChangePwd) {
                    mStudent.setPassword(oldPasss);
                } else {
                    mStudent.setPassword(MD5Util.MD5Encode(newPasss));
                }
                DataFetcherImpl.getDataFetcher(context).updateStudent(mStudent, new FirebaseUpdateResult() {
                    @Override
                    public void updateResult(boolean success, String msg) {
                        ToastUtil.tost(context, msg);
                    }
                }, false);
                break;
        }
        dismiss();
    }

}
