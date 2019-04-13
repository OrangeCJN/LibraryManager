package com.example.bookmanager.ui;

import android.content.DialogInterface;
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
import com.example.bookmanager.adapter.ItemUserAdapter;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.ui.dialog.UserRegisterDialog;
import com.example.bookmanager.ui.dialog.UserUpdateDialog;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * user manager page
 */
public class UserManagerActivity extends BaseActivity implements AdapterView.OnItemClickListener, UserRegisterDialog.AddUserCallback, View.OnClickListener {

    private List<Student> data;

    private ListView list;
    private Button btnSearch;
    private Button btnClear;
    private EditText et1;
    private ItemUserAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = (ListView) findViewById(R.id.list);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnClear = (Button) findViewById(R.id.btn_clear);
        et1 = (EditText) findViewById(R.id.et1);
        btnClear.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        list.setAdapter(adapter = new ItemUserAdapter(this));
        list.setOnItemClickListener(this);
        setTitle("User Management");
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            new UserRegisterDialog(this).setAddUserCallback(this).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * get all users(students) info
     */
    public void getData() {
        mIDataFetcher.getAllStudent(new FirebaseQueryResult() {
            @Override
            public void success(DataSnapshot dataSnapshot) {
                adapter.setObjects(data = FilterUtil.filterDataList(dataSnapshot, Student.class));
                if (data == null || data.size() == 0) {
                    ToastUtil.tost(mActivity, "No data");
                }
            }
        });
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_user_manager;
    }

    /**
     * list click event listener
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Student student = adapter.getItem(position);
        UserUpdateDialog userUpdateDialog = new UserUpdateDialog(this, student);
        userUpdateDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                adapter.notifyDataSetChanged();
            }
        });
        userUpdateDialog.show();
    }

    /**
     * callback of adding user
     *
     * @param success
     * @param msg
     * @param student
     */
    @Override
    public void addUserResult(boolean success, String msg, Student student) {
        if (success) {
            getData(); // update list data if success
        }
    }

    /**
     * search click event & clear click event listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnClear) {
            adapter.setObjects(data);
            et1.setText("");
        } else {
            String key = et1.getText().toString();
            if (TextUtils.isEmpty(key)) {
                ToastUtil.tost(mActivity, "Please enter the title keyword");
                return;
            }
            if (data == null || data.size() == 0) {
                ToastUtil.tost(mActivity, "No relevant users have been found");
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                return;
            }
            List<Student> temp = new ArrayList<>();
            for (Student student : data) {
                if (student.getStudent_name().toUpperCase().indexOf(key.toUpperCase()) != -1
                        || student.getStudent_code().toUpperCase().indexOf(key.toUpperCase()) != -1) {
                    temp.add(student);
                }
            }
            if (temp == null || temp.size() == 0) {
                ToastUtil.tost(mActivity, "No relevant users have been found");
            } else {
                adapter.setObjects(temp);
            }
        }
    }
}
