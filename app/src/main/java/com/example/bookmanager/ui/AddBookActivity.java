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
import com.example.bookmanager.model.Book;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * add or update book info
 */
public class AddBookActivity extends BaseActivity {

    private EditText mName;
    private EditText mImage;
    private EditText mPress;
    private EditText mDescribe;
    private EditText mNumber;
    private EditText mAuthor;

    private Book book;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mName = (EditText) findViewById(R.id.name);
        mImage = (EditText) findViewById(R.id.image);
        mPress = (EditText) findViewById(R.id.cbs);
        mDescribe = (EditText) findViewById(R.id.js);
        mNumber = (EditText) findViewById(R.id.c);
        mAuthor = (EditText) findViewById(R.id.a);
        book = (Book) getIntent().getSerializableExtra("data");
        if (book != null) {
            setTitle("Edit Book");
            mName.setText(book.getName());
            mImage.setText(book.getImageUrl());
            mPress.setText(book.getPressName());
            mDescribe.setText(book.getIntroduce());
            mNumber.setText(book.getCount() + "");
            mAuthor.setText(book.getAuthor() + "");
            mAuthor.setSelection(mAuthor.getText().length());
            mNumber.setSelection(mNumber.getText().length());
            mDescribe.setSelection(mDescribe.getText().length());
            mPress.setSelection(mPress.getText().length());
            mImage.setSelection(mImage.getText().length());
            mName.setSelection(mName.getText().length());
        } else {
            setTitle("Add Book");
        }
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_add_book;
    }

    /**
     * return to last page
     *
     * @param view
     */
    public void cancel(View view) {
        finish();
    }

    /**
     * save info
     *
     * @param view
     */
    public void save(View view) {
        String nameString = mName.getText().toString();
        String imageString = mImage.getText().toString();
        String pressString = mPress.getText().toString();
        String describeString = mDescribe.getText().toString();
        String numberString = mNumber.getText().toString();
        String authorString = mAuthor.getText().toString();
        if (TextUtils.isEmpty(nameString)
                || TextUtils.isEmpty(imageString)
                || TextUtils.isEmpty(pressString)
                || TextUtils.isEmpty(describeString)
                || TextUtils.isEmpty(numberString)
                || TextUtils.isEmpty(authorString)) {
            ToastUtil.tost(mActivity, "Please enter full information");
            return;
        }
        if (book == null) {
            book = new Book();
            book.setPressName(pressString);
            book.setIntroduce(describeString);
            book.setName(nameString);
            book.setImageUrl(imageString);
            book.setId(System.currentTimeMillis());
            book.setUsers(new ArrayList<String>());
            book.setCount(Integer.parseInt(numberString));
            book.setAuthor(authorString);
            mIDataFetcher.addBook(book, new FirebaseInsertResult() {
                @Override
                public void insertResult(boolean success, String msg) {
                    ToastUtil.tost(mActivity, msg);
                    if(success){
                        finish();
                    }
                }
            });
        } else {
            int n = Integer.parseInt(numberString);
            List<String> users = book.getUsers();
            if (users != null && users.size() > n) {
                Toast.makeText(mActivity, "The number of books must not be less than the number lent out and waiting agree", Toast.LENGTH_SHORT).show();
                return;
            }
            book.setPressName(pressString);
            book.setIntroduce(describeString);
            book.setName(nameString);
            book.setImageUrl(imageString);
            book.setCount(Integer.parseInt(numberString));
            book.setAuthor(authorString);
            mIDataFetcher.updateBook(book, new FirebaseUpdateResult() {
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
