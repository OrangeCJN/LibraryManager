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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.baozi.Zxing.CaptureActivity;
import com.baozi.Zxing.ZXingConstants;
import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.adapter.ItemBookAdapter;
import com.example.bookmanager.callback.FirebaseDeleteResult;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.callback.FirebaseUpdateResult;
import com.example.bookmanager.model.BaseBean;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.SPUtils;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * books manager page
 */
public class BooksActivity extends BaseActivity implements View.OnClickListener {

    private Button btnSearch;
    private Button btnClear;
    private ListView list;
    private EditText et1;
    private RadioGroup rg;

    private ItemBookAdapter adapter;
    private List<Book> all;
    private int type = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set title by user type
        setTitle(userType == 1 ? "Books Management" : "Books");
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnClear = (Button) findViewById(R.id.btn_clear);
        et1 = (EditText) findViewById(R.id.et1);
        list = (ListView) findViewById(R.id.list);
        rg = (RadioGroup) findViewById(R.id.rg);
        if (userType == 1) {
            rg.setVisibility(View.GONE);
        } else {
            rg.setVisibility(View.VISIBLE);
        }
        btnClear.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        all = new ArrayList<>();
        list.setAdapter(adapter = new ItemBookAdapter(this));
        // long click listener：show info detail, choice of delete
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Book item = adapter.getItem(position);
                final List<String> users = (item.getUsers() == null ? new ArrayList<String>() : item.getUsers());
                String usersBorrowed = "";
                if (users != null && users.size() > 0) {
                    usersBorrowed = getUsers(users);
                }
                StringBuilder sb = new StringBuilder();
                sb.append("\nName：" + item.getName() + "\n\n");
                sb.append("Author：" + item.getAuthor() + "\n\n");
                sb.append("Describe：" + item.getIntroduce() + "\n\n");
                sb.append("Number：" + (item.getCount()) + "\n\n");
                sb.append("Borrowed：" + (users.size()) + usersBorrowed + "\n\n");
                sb.append("Remaining：" + (item.getCount() - users.size()) + "\n");
                if (userType == 1) {
                    // alert to confirm deleting the book
                    new AlertDialog.Builder(mActivity)
                            .setTitle("Book Detail")
                            .setMessage(sb.toString())
                            .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int usersCount = getUsersCount(item.getUsers());
                                    if (usersCount > 0) {
                                        new AlertDialog.Builder(mActivity)
                                                .setTitle("Tip")
                                                .setMessage("There are still users who have not returned the book, please return it and delete it.")
                                                .setPositiveButton("OK", null)
                                                .show();
                                    } else {
                                        // delete the specified book
                                        mIDataFetcher.deleteBook(item, new FirebaseDeleteResult() {
                                            @Override
                                            public void deleteResult(boolean success, String msg) {
                                                ToastUtil.tost(mActivity, msg);
                                                if (success) {
                                                    // update list
                                                    all.remove(item);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }
                                        });
                                    }
                                }
                            })
                            .setNegativeButton("CANCEL", null)
                            .setNeutralButton("QRCode", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(mActivity, QRCodeImageActivity.class)
                                            .putExtra("bookId", adapter.getItem(position).getId()));
                                }
                            })
                            .show();
                } else {

                }
                return true;
            }
        });
        // click event listener: update book info
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (userType == 1) {
                    startActivity(new Intent(mActivity, AddBookActivity.class)
                            .putExtra("data", adapter.getItem(position)));
                } else {
                    final Book item = adapter.getItem(position);
                    final List<String> users = (item.getUsers() == null ? new ArrayList<String>() : item.getUsers());
                    String usersBorrowed = "";
                    if (users != null && users.size() > 0) {
                        usersBorrowed = getUsers(users);
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("\nName：" + item.getName() + "\n\n");
                    sb.append("Author：" + item.getAuthor() + "\n\n");
                    sb.append("Describe：" + item.getIntroduce() + "\n\n");
                    sb.append("Number：" + (item.getCount()) + "\n\n");
                    sb.append("Borrowed：" + (getUsersCount(users)) + usersBorrowed + "\n\n");
                    sb.append("Remaining：" + (item.getCount() - users.size()) + "\n");
                    // Book实体中的Users由之前存储用户名集合变为存储特殊字符串的集合
                    // 特殊字符串含义 username:name:bookId:booName or username:name:bookId:booName:waiting_agree
                    // 前者表示用户已经借得此图书，后者表示已经发起申请并等待管理员同意或者拒绝
                    final boolean isBorrow = users != null && users.size() > 0
                            && item.getUsers().contains(SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + item.getId() + ":" + item.getName());
                    // 判断是否正在等待管理员审核
                    final boolean isWaitAgree = judgeIsWaitAgree(users, SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + item.getId() + ":" + item.getName());
                    AlertDialog.Builder book_detail = new AlertDialog.Builder(mActivity)
                            .setTitle("Book Detail")
                            .setMessage(sb.toString());
                    if (isBorrow) {
                        book_detail.setPositiveButton("OK", null).show();
                    } else {
                        book_detail.setPositiveButton(isWaitAgree ? "Waiting for the agreed" : "Borrow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int enable = item.getCount() - users.size();
                                if (!isBorrow && !isWaitAgree) {
                                    if (enable > 0) {
                                        // 申请借书
                                        users.add(SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + item.getId() + ":" + item.getName() + ":waiting_agree");
                                        // 更新数据
                                        item.setUsers(users);
                                        mIDataFetcher.updateBook(item, new FirebaseUpdateResult() {
                                            @Override
                                            public void updateResult(boolean success, String msg) {
                                                ToastUtil.tost(mActivity, msg);
                                            }
                                        });
                                    } else {
                                        Toast.makeText(mActivity, "The number of books remaining is insufficient", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (isBorrow) {
                                    /*// 还书
                                    users.remove(SPUtils.getString(Constant.SP_USER_USER_NAME));
                                    item.setUsers(users);
                                    mIDataFetcher.updateBook(item, new FirebaseUpdateResult() {
                                        @Override
                                        public void updateResult(boolean success, String msg) {
                                            ToastUtil.tost(mActivity, msg);
                                        }
                                    });*/
                                }
                            }
                        }).setNegativeButton("CANCEL", null).show();
                    }
                }
            }
        });
        // 过滤数据选择事件
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                filterData(checkedId);
            }
        });
    }

    private boolean judgeIsWaitAgree(List<String> users, String name) {
        if (users == null || users.size() == 0 || TextUtils.isEmpty(name)) {
            return false;
        }
        for (String single : users) {
            if (!TextUtils.isEmpty(single) && single.indexOf(name) >= 0 && !single.equals(name)) {
                return true;
            }
        }
        return false;
    }

    // get the borrower's student code of the specified book
    private int getUsersCount(List<String> ss) {
        if (ss == null || ss.size() == 0) {
            return 0;
        }
        int i = 0;
        StringBuilder sb = new StringBuilder("（");
        for (String s : ss) {
            if (!TextUtils.isEmpty(s)) {
                String[] split = s.split(":");
                if (split != null && split.length > 0) {
                    i++;
                }
            }
        }
        return i;
    }

    // get the borrower's student code of the specified book
    private String getUsers(List<String> ss) {
        StringBuilder sb = new StringBuilder("（");
        for (String s : ss) {
            if (!TextUtils.isEmpty(s)) {
                String[] split = s.split(":");
                if (split != null && split.length > 0) {
                    sb.append(split[1] + ", ");
                }
            }
        }
        return sb.substring(0, sb.length() - 2) + "）";
    }

    /**
     * 过滤数据
     *
     * @param checkedId
     */
    private void filterData(int checkedId) {
        List<Book> ccc = null;
        List<Book> temp = new ArrayList<Book>();
        if (type == 0) {
            ccc = all;
        } else {
            ccc = adapter.objects;
        }
        // Book实体中的Users由之前存储用户名集合变为存储特殊字符串的集合
        // 特殊字符串含义 username:name:bookId:booName or username:name:bookId:booName:waiting_agree
        // 前者表示用户已经借得此图书，后者表示已经发起申请并等待管理员同意或者拒绝
        switch (checkedId) {
            case R.id.rb_1:
                // all
                adapter.setObjects(all);
                break;
            case R.id.rb_2:
                // can borrow
                if (ccc == null || ccc.size() == 0) {
                    return;
                }
                for (Book b : ccc) {
                    if (b.getUsers() == null
                            || (!b.getUsers().contains(SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + b.getId() + ":" + b.getName())
                            && !b.getUsers().contains(SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + b.getId() + ":" + b.getName() + ":waiting_agree")
                            && b.getCount() - getUsersCount(b.getUsers()) > 0)) {
                        temp.add(b);
                    }
                }
                adapter.setObjects(temp);
                break;
            case R.id.rb_3:
                // borrowed
                if (ccc == null || ccc.size() == 0) {
                    return;
                }
                for (Book b : ccc) {
                    if (b.getUsers() != null && b.getUsers().contains(SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + b.getId() + ":" + b.getName())) {
                        temp.add(b);
                    }
                }
                adapter.setObjects(temp);
                break;
            case R.id.rb_5:
                // waiting agree
                if (ccc == null || ccc.size() == 0) {
                    return;
                }
                for (Book b : ccc) {
                    if (b.getUsers() != null && b.getUsers().contains(SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + b.getId() + ":" + b.getName() + ":waiting_agree")) {
                        temp.add(b);
                    }
                }
                adapter.setObjects(temp);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnClear.performClick();
        getDataAll();
    }

    /**
     * get all books info
     */
    private void getDataAll() {
        mIDataFetcher.getAllBook(new FirebaseQueryResult() {
            @Override
            public void success(DataSnapshot dataSnapshot) {
                List<Book> books = FilterUtil.filterDataList(dataSnapshot, Book.class);
                all.clear();
                all.addAll(books);
                adapter.setObjects(all);
                rg.check(R.id.rb_1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (userType == 1) {
            getMenuInflater().inflate(R.menu.add, menu);
        } else {
            getMenuInflater().inflate(R.menu.scan, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add && userType == 1) {
            startActivity(new Intent(this, AddBookActivity.class));
            return true;
        } else if (item.getItemId() == R.id.scan && userType == 0) {
            scan();
        } else if (item.getItemId() == R.id.refresh) {
            btnClear.performClick();
            getDataAll();
        }
        return super.onOptionsItemSelected(item);
    }

    public void scan() {
        Intent intent = new Intent(this,
                CaptureActivity.class);
        intent.putExtra(ZXingConstants.ScanIsShowHistory, true);
        startActivityForResult(intent, ZXingConstants.ScanRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ZXingConstants.ScanRequestCode:
                if (data == null) {
                    return;
                }
                if (resultCode == ZXingConstants.ScanRequestCode) {
                    /**
                     * 拿到解析完成的字符串
                     */
                    String result = data.getStringExtra(ZXingConstants.ScanResult);
                    if (TextUtils.isEmpty(result)) {
                        new AlertDialog.Builder(this)
                                .setTitle("Tip")
                                .setMessage("Invalid QR code")
                                .setPositiveButton("OK", null)
                                .create().show();
                        return;
                    }
                    String[] split = result.split(QRCodeImageActivity.CODE_SPLIT);
                    if (split == null || split.length != 3) {
                        new AlertDialog.Builder(this)
                                .setTitle("Tip")
                                .setMessage("Invalid QR code")
                                .setPositiveButton("OK", null)
                                .create().show();
                        return;
                    }
                    final long bookId = Long.parseLong(split[1]);
                    mIDataFetcher.getAllBook(new FirebaseQueryResult() {
                        @Override
                        public void success(DataSnapshot dataSnapshot) {
                            List<Book> baseBeans = FilterUtil.filterDataList(dataSnapshot, Book.class);
                            if (baseBeans == null || baseBeans.size() == 0 || !baseBeans.contains(new Book(bookId))) {//if (baseBeans == null || baseBeans.size() == 0) {
                                new AlertDialog.Builder(mActivity)
                                        .setTitle("Tip")
                                        .setMessage("The operation failed. The book doesn't exist.")
                                        .setPositiveButton("OK", null)
                                        .create().show();
                            } else {
                                for (Book b : baseBeans) {
                                    if (b != null && b.getId() == bookId) {
                                        // Book实体中的Users由之前存储用户名集合变为存储特殊字符串的集合
                                        // 特殊字符串含义 username:name:bookId:booName or username:name:bookId:booName:waiting_agree
                                        // 前者表示用户已经借得此图书，后者表示已经发起申请并等待管理员同意或者拒绝
                                        List<String> users = b.getUsers() == null ? new ArrayList<String>() : b.getUsers();
                                        if (users.contains(SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + b.getId() + ":" + b.getName())) {
                                            users.remove(SPUtils.getString(Constant.SP_USER_USER_NAME) + ":" + SPUtils.getString(Constant.SP_USER_NAME) + ":" + b.getId() + ":" + b.getName());
                                            b.setUsers(users);
                                            mIDataFetcher.updateBook(b, new FirebaseUpdateResult() {
                                                @Override
                                                public void updateResult(boolean success, String msg) {
                                                    if (success) {
                                                        new AlertDialog.Builder(mActivity)
                                                                .setTitle("Tip")
                                                                .setMessage("Scan the code successfully and return the book successfully")
                                                                .setPositiveButton("OK", null)
                                                                .create().show();
                                                    } else {
                                                        ToastUtil.tost(mActivity, msg);
                                                    }
                                                }
                                            });
                                        } else {
                                            new AlertDialog.Builder(mActivity)
                                                    .setTitle("Tip")
                                                    .setMessage("The operation failed. You didn't borrow the book")
                                                    .setPositiveButton("OK", null)
                                                    .create().show();
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_book_manager;
    }

    /**
     * search click event & clear click event listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v == btnClear) {
            adapter.setObjects(all);
            et1.setText("");
            type = 0;
        } else {
            String key = et1.getText().toString();
            if (TextUtils.isEmpty(key)) {
                ToastUtil.tost(mActivity, "Please enter the title keyword");
                return;
            }
            if (all == null || all.size() == 0) {
                ToastUtil.tost(mActivity, "No relevant books have been found");
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                return;
            }
            List<Book> temp = new ArrayList<>();
            for (Book book : all) {
                if (book.getName().toUpperCase().indexOf(key.toUpperCase()) != -1
                        || book.getAuthor().toUpperCase().indexOf(key.toUpperCase()) != -1
                        || book.getIntroduce().toUpperCase().indexOf(key.toUpperCase()) != -1) {
                    temp.add(book);
                }
            }
            if (temp == null || temp.size() == 0) {
                ToastUtil.tost(mActivity, "No relevant books have been found");
            } else {
                adapter.setObjects(temp);
                type = 1;
            }
        }
    }
}
