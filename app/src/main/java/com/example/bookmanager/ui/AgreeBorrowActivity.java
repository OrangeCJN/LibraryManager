package com.example.bookmanager.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ListView;

import com.example.bookmanager.R;
import com.example.bookmanager.adapter.ItemAgreeAdapter;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.model.BaseBean;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理同意或者拒绝借书申请
 */
public class AgreeBorrowActivity extends BaseActivity {

    private ListView list;
    private ItemAgreeAdapter mItemAgreeAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_agree_borrow;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Borrow books artifact");
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(mItemAgreeAdapter = new ItemAgreeAdapter(this));
        // 获取所有申请借书的信息
        mIDataFetcher.getAllBook(new FirebaseQueryResult() {
            @Override
            public void success(DataSnapshot dataSnapshot) {
                List<Book> books = FilterUtil.filterDataList(dataSnapshot, Book.class);
                if (books != null && books.size() > 0) {
                    List<String> borrowInfos = new ArrayList<>();
                    for (Book b : books) {
                        if (b != null && b.getUsers() != null && b.getUsers().size() > 0) {
                            for (String s : b.getUsers()) {
                                // 包含waiting字符串的表示需要审核
                                if (!TextUtils.isEmpty(s) && s.indexOf(":waiting_agree") != -1 && !borrowInfos.contains(s)) {
                                    borrowInfos.add(s);
                                }
                            }
                        }
                    }
                    mItemAgreeAdapter.setObjects(borrowInfos);
                }
            }
        });
    }
}
