package com.example.bookmanager.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseQueryResult;
import com.example.bookmanager.callback.FirebaseUpdateResult;
import com.example.bookmanager.model.Book;
import com.example.bookmanager.ui.AgreeBorrowActivity;
import com.example.bookmanager.util.FilterUtil;
import com.example.bookmanager.util.ToastUtil;
import com.google.firebase.database.DataSnapshot;

/**
 * agree or reject book list adapter
 */
public class ItemAgreeAdapter extends BaseAdapter implements View.OnClickListener {

    // 缓存，作用是网络请求失败后进行恢复操作
    private List<String> cache = new ArrayList<String>();
    private List<String> objects = new ArrayList<String>();

    private AgreeBorrowActivity context;
    private LayoutInflater layoutInflater;

    public ItemAgreeAdapter(AgreeBorrowActivity context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public String getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * get widgets
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_agree, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    /**
     * bind data
     *
     * @param object
     * @param holder
     */
    private void initializeViews(String object, ViewHolder holder) {
        holder.yes.setTag(object);
        holder.no.setTag(object);
        holder.yes.setOnClickListener(this);
        holder.no.setOnClickListener(this);
        String[] msg = object.split(":");
        StringBuilder stringBuilder = new StringBuilder(msg[1] + " Borrow " + msg[3]);
        SpannableString spannableString = new SpannableString(stringBuilder.toString());
        spannableString.setSpan(new ForegroundColorSpan(0xffFF4081), 0, msg[1].length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(0xff3F51B5), stringBuilder.indexOf(msg[3]), stringBuilder.indexOf(msg[3]) + msg[3].length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        holder.result.setText(spannableString);
    }

    /**
     * yes or no click event
     *
     * @param v
     */
    @Override
    public void onClick(final View v) {
        final String result = (String) v.getTag();
        final int i = objects.indexOf(result);
        if (i < 0) {
            return;
        }
        // 缓存原数据
        cache.clear();
        cache.addAll(objects);
        if (v.getId() == R.id.yes) {
            // 如果是yes，则把:waiting_agree去除
            objects.set(i, result.replace(":waiting_agree", ""));
        } else {
            // 如果是no，则直接从集合中移除
            objects.remove(i);
        }
        final String bookId = result.split(":")[2];
        // 获取目标图书信息
        context.getIDataFetcher().getAllBook(new FirebaseQueryResult() {
            @Override
            public void success(DataSnapshot dataSnapshot) {
                Book book = FilterUtil.filterBook(dataSnapshot, bookId);
                if (book != null) {
                    // 更新Users集合
                    List<String> users = book.getUsers();
                    int i1 = users.indexOf(result);
                    if(v.getId() == R.id.yes) {
                        users.set(i1, result.replace(":waiting_agree", ""));
                    }else {
                        users.remove(i1);
                    }
                    book.setUsers(users);
                    context.getIDataFetcher().updateBook(book, new FirebaseUpdateResult() {
                        @Override
                        public void updateResult(boolean success, String msg) {
                            if (success) {
                                if(v.getId() == R.id.yes) {
                                    ToastUtil.tost(context, "Accept successfully");
                                }else {
                                    ToastUtil.tost(context, "Reject successfully");
                                }
                                // 如果成功，则移除申请信息
                                // 因为已经处理了，不需要展示了
                                cache.remove(i);
                            }
                            objects.clear();
                            objects.addAll(cache);
                            notifyDataSetChanged();
                        }
                    });
                } else {
                    // 没有找到图书，恢复集合数据
                    ToastUtil.tost(context, "Not found");
                    objects.clear();
                    objects.addAll(cache);
                    notifyDataSetChanged();
                }
            }
        });
    }

    protected class ViewHolder {
        private TextView result;
        private ImageView yes;
        private ImageView no;

        public ViewHolder(View view) {
            result = (TextView) view.findViewById(R.id.result);
            yes = (ImageView) view.findViewById(R.id.yes);
            no = (ImageView) view.findViewById(R.id.no);
        }
    }
}
