package com.example.bookmanager.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookmanager.R;
import com.example.bookmanager.adapter.ItemActivityAdapter;
import com.example.bookmanager.callback.FirebaseDeleteResult;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.model.ActionInfo;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * activities & events manager page
 */
public class ActivitiesActivity extends BaseActivity implements View.OnClickListener {

    private EditText et1;
    private Button btnSearch;
    private Button btnClear;
    private ListView list;

    private List<ActionInfo> all;

    @Override
    protected int getContentId() {
        return R.layout.activity_activities;
    }

    private ItemActivityAdapter actionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(userType == 1 ? "Activity Management" : "Activity");
        et1 = (EditText) findViewById(R.id.et1);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnClear = (Button) findViewById(R.id.btn_clear);
        list = (ListView) findViewById(R.id.list);
        all = new ArrayList<>();
        btnClear.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        list.setAdapter(actionAdapter = new ItemActivityAdapter(this));
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            /**
             * 列表长按事件
             * @param parent
             * @param view
             * @param position
             * @param id
             * @return
             */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (userType == 1) {
                    new AlertDialog.Builder(mActivity)
                            .setTitle("Tip")
                            .setMessage("Are you sure you want to delete this activity? ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mIDataFetcher.deleteActivity(actionAdapter.getItem(position), new FirebaseDeleteResult() {
                                        @Override
                                        public void deleteResult(boolean success, String msg) {
                                            // update list
                                            all.remove(actionAdapter.getItem(position));
                                            actionAdapter.setObjects(all);
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("CANCEL", null)
                            .show();
                }
                return true;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * 列表单击事件
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (userType == 1) {
                    startActivity(new Intent(mActivity, AddActivityActivity.class)
                            .putExtra("data", actionAdapter.getItem(position)));
                } else {
                    // goto detail for normal user
                    startActivity(new Intent(mActivity, ActivityDetailActivity.class)
                            .putExtra("t", actionAdapter.getItem(position).getActionName())
                            .putExtra("c", actionAdapter.getItem(position).getActionContent())
                            .putExtra("i", actionAdapter.getItem(position).getActionImage()));
                }
            }
        });

    }

    /**
     * get all activities info
     */
    private void getDataAll() {
        mIDataFetcher.getAllActivity(new FirebaseQueryResult() {
            @Override
            public void success(DataSnapshot dataSnapshot) {
                List<ActionInfo> actionInfos = FilterUtil.filterDataList(dataSnapshot, ActionInfo.class);
                all.clear();
                all.addAll(actionInfos);
                actionAdapter.setObjects(all);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userType == 1) {
            getMenuInflater().inflate(R.menu.add, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add && userType == 1) {
            startActivity(new Intent(mActivity, AddActivityActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * search click event & clear click event listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnClear) {
            actionAdapter.setObjects(all);
            et1.setText("");
        } else {
            String key = et1.getText().toString();
            if (TextUtils.isEmpty(key)) {
                ToastUtil.tost(mActivity, "Please enter the title keyword");
                return;
            }
            if (all == null || all.size() == 0) {
                ToastUtil.tost(mActivity, "No relevant activity have been found");
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                return;
            }
            List<ActionInfo> temp = new ArrayList<>();
            for (ActionInfo actionInfo : all) {
                if (actionInfo.getActionName().toUpperCase().indexOf(key.toUpperCase()) != -1
                        || actionInfo.getActionContent().toUpperCase().indexOf(key.toUpperCase()) != -1) {
                    temp.add(actionInfo);
                }
            }
            if (temp == null || temp.size() == 0) {
                ToastUtil.tost(mActivity, "No relevant activity have been found");
            } else {
                actionAdapter.setObjects(temp);
            }
        }
    }
}
