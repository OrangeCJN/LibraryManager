package com.example.bookmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookmanager.R;
import com.example.bookmanager.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * book list adapter
 */
public class ItemBookAdapter extends BaseAdapter {

    public List<Book> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public void setObjects(List<Book> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    public ItemBookAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Book getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_book, parent, false);
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
    private void initializeViews(Book object, ViewHolder holder) {
        holder.author.setText(object.getAuthor());
        holder.content.setText(object.getIntroduce());
        holder.name.setText(object.getName());
        Glide.with(context).load(object.getImageUrl())
                .apply(new RequestOptions().placeholder
                        (R.mipmap.ic).error(R.mipmap.ic)).into(holder.img);

    }

    /**
     * widgets buffer
     */
    protected class ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView author;
        private TextView content;

        public ViewHolder(View view) {
            img = (ImageView) view.findViewById(R.id.img);
            name = (TextView) view.findViewById(R.id.name);
            author = (TextView) view.findViewById(R.id.author);
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
