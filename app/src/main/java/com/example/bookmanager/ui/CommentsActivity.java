package com.example.bookmanager.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.adapter.ItemCommentAdapter;
import com.example.bookmanager.callback.FirebaseDeleteResult;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.model.Comment;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.ui.dialog.PushCommentDialog;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.SPUtils;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * comment messages manager page
 */
public class CommentsActivity extends BaseActivity {

    private ListView list;
    private List<Comment> mComments;
    private List<Comment> mReveseComments;
    private ItemCommentAdapter mItemCommentAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_comments;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(userType == 1 ? "Message Management" : "Message");
        mComments = new ArrayList<>();
        mReveseComments = new ArrayList<>();
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(mItemCommentAdapter = new ItemCommentAdapter(mActivity));
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (userType == 1) {
                    new AlertDialog.Builder(mActivity)
                            .setTitle("Tip")
                            .setMessage("Are you sure you want to delete this comment? ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mIDataFetcher.deleteComment(mItemCommentAdapter.getItem(position), new FirebaseDeleteResult() {
                                        @Override
                                        public void deleteResult(boolean success, String msg) {
                                            // update list
                                            ToastUtil.tost(mActivity, msg);
                                            mComments.remove(mItemCommentAdapter.getItem(position));
                                            mItemCommentAdapter.setObjects(mComments);
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("CANCEL", null)
                            .show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataAll();
    }

    /**
     * get all comments info
     */
    private void getDataAll() {
        mIDataFetcher.getAllComment(new FirebaseQueryResult() {
            @Override
            public void success(DataSnapshot dataSnapshot) {
                List<Comment> comments = null;
                if (userType == 1) {
                    comments = FilterUtil.filterDataList(dataSnapshot, Comment.class);
                } else {
                    comments = FilterUtil.filterYourComment(FilterUtil.
                            <Comment>filterDataList(dataSnapshot, Comment.class), SPUtils.getString(Constant.SP_USER_USER_NAME));
                }
                if (comments == null || comments.size() == 0) {
                    ToastUtil.tost(mActivity, "No data");
                } else {
                    mItemCommentAdapter.setObjects(mComments = comments);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        if (userType == 1) {
            menu.findItem(R.id.add).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.sort) {
            // 选择排序方式，默认是正序
            final String items[] = {"Positive sequence", "Reverse order"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    if (which == 0) {
                        mItemCommentAdapter.setObjects(mComments);
                    } else {
                        mReveseComments.clear();
                        mReveseComments.addAll(mComments);
                        Collections.reverse(mReveseComments);
                        mItemCommentAdapter.setObjects(mReveseComments);
                    }
                }
            });
            builder.show();
        } else if (item.getItemId() == R.id.add) {
            // 普通用户有发表留言的功能
            PushCommentDialog pushCommentDialog = new PushCommentDialog(mActivity);
            pushCommentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    getDataAll();
                }
            });
            pushCommentDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
