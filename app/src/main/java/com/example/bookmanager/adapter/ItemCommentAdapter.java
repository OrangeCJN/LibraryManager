package com.example.bookmanager.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bookmanager.R;
import com.example.bookmanager.model.Comment;

/**
 * book list adapter
 */
public class ItemCommentAdapter extends BaseAdapter {

    private List<Comment> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemCommentAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setObjects(List<Comment> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Comment getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_comment, parent, false);
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
    private void initializeViews(Comment object, ViewHolder holder) {
        holder.name.setText(object.getName());
        holder.date.setText(object.getDate());
        holder.content.setText(object.getContent());
    }

    /**
     * widgets buffer
     */
    protected class ViewHolder {
        private TextView name;
        private TextView date;
        private TextView content;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            date = (TextView) view.findViewById(R.id.date);
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
