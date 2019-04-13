package com.example.bookmanager.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseInsertResult;
import com.example.bookmanager.callback.FirebaseUpdateResult;
import com.example.bookmanager.firebase.DataFetcherImpl;
import com.example.bookmanager.model.Comment;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.util.MD5Util;
import com.example.bookmanager.util.SPUtils;
import com.example.bookmanager.util.ToastUtil;

import java.util.ArrayList;

import static com.example.bookmanager.util.TimeUtil.getNowDate;

/**
 * 发表留言弹框
 */
public class PushCommentDialog extends Dialog implements View.OnClickListener {

    private EditText content;
    private CheckBox anonymous;
    private TextView cancel;
    private TextView save;

    private Context context;

    public PushCommentDialog(Activity context) {
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
        SPUtils.init(context);
    }

    /**
     * dialog initialization
     */
    private void init() {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setBackgroundColor(0x00000000);
        setContentView(R.layout.user_push_comment);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        content = (EditText) findViewById(R.id.content);
        anonymous = (CheckBox) findViewById(R.id.anonymous);
        cancel = (TextView) findViewById(R.id.cancel);
        save = (TextView) findViewById(R.id.save);

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
                String contents = content.getText().toString();
                if (TextUtils.isEmpty(contents)) {
                    Toast.makeText(context, "Please enter message", Toast.LENGTH_SHORT).show();
                    return;
                }
                Comment comment = null;
                if (anonymous.isChecked()) {
                    comment = new Comment(getNowDate(), contents, Constant.ANONYMOUS_TAG, SPUtils.getString(Constant.SP_USER_USER_NAME));
                } else {
                    comment = new Comment(getNowDate(),
                            contents, SPUtils.getString(Constant.SP_USER_NAME), SPUtils.getString(Constant.SP_USER_USER_NAME));
                }
                DataFetcherImpl.getDataFetcher(context).addComment(comment, new FirebaseInsertResult() {
                    @Override
                    public void insertResult(boolean success, String msg) {
                        ToastUtil.tost(context, msg);
                    }
                });
                break;
        }
        dismiss();
    }

}
