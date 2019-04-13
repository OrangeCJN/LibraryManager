package com.example.bookmanager.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseInsertResult;
import com.example.bookmanager.callback.FirebaseUpdateResult;
import com.example.bookmanager.model.ActionInfo;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.ToastUtil;

/**
 * 添加活动
 */
public class AddActivityActivity extends BaseActivity {

    private EditText name;
    private EditText image;
    private EditText content;
    private ActionInfo actionInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = (EditText) findViewById(R.id.name);
        image = (EditText) findViewById(R.id.image);
        content = (EditText) findViewById(R.id.content);
        actionInfo = (ActionInfo) getIntent().getSerializableExtra("data");
        setTitle("Add activity");
        if(actionInfo != null){
            setTitle("Edit activity");
            name.setText(actionInfo.getActionName());
            content.setText(actionInfo.getActionContent());
            image.setText(actionInfo.getActionImage());
            name.setSelection(name.getText().length());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected int getContentId() {
        return R.layout.activity_add_activity;
    }

    /**
     * 取消
     * @param view
     */
    public void cancel(View view) {
        finish();
    }

    /**
     * 保存
     * @param view
     */
    public void save(View view) {
        String n = name.getText().toString();
        String i = image.getText().toString();
        String c = content.getText().toString();
        if(TextUtils.isEmpty(n)
                || TextUtils.isEmpty(i)
                || TextUtils.isEmpty(c)){
            Toast.makeText(this, "Please enter full information", Toast.LENGTH_SHORT).show();
            return;
        }
        if(actionInfo == null){
            // 插入
            actionInfo = new ActionInfo();
            actionInfo.setActionContent(c);
            actionInfo.setActionName(n);
            actionInfo.setActionImage(i);
            actionInfo.setActionId(System.currentTimeMillis());
            mIDataFetcher.addActivity(actionInfo, new FirebaseInsertResult() {
                @Override
                public void insertResult(boolean success, String msg) {
                    ToastUtil.tost(mActivity, msg);
                    if(success){
                        finish();
                    }
                }
            });
        }else{
            // 更新
            actionInfo.setActionContent(c);
            actionInfo.setActionName(n);
            actionInfo.setActionImage(i);
            mIDataFetcher.updateActivity(actionInfo, new FirebaseUpdateResult() {
                @Override
                public void updateResult(boolean success, String msg) {
                    ToastUtil.tost(mActivity, msg);
                    if(success){
                        finish();
                    }
                }
            });
        }
    }
}
