package com.example.bookmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookmanager.R;
import com.example.bookmanager.model.ActionInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * activity list adapter
 */
public class ItemActivityAdapter extends BaseAdapter {

    public List<ActionInfo> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public void setObjects(List<ActionInfo> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    public ItemActivityAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ActionInfo getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_action, parent, false);
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
    private void initializeViews(ActionInfo object, ViewHolder holder) {
        holder.name.setText(object.getActionName());
        holder.content.setText(object.getActionContent());
        Glide.with(context).load(object.getActionImage()).into(holder.img);
    }

    protected class ViewHolder {
        private ImageView img;
        private TextView name;
        private TextView content;

        public ViewHolder(View view) {
            img = (ImageView) view.findViewById(R.id.img);
            name = (TextView) view.findViewById(R.id.name);
            content = (TextView) view.findViewById(R.id.content);
        }
    }
}
