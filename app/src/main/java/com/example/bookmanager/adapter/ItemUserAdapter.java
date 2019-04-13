package com.example.bookmanager.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bookmanager.R;
import com.example.bookmanager.callback.FirebaseDeleteResult;
import com.example.bookmanager.model.Student;
import com.example.bookmanager.ui.UserManagerActivity;
import com.example.bookmanager.ui.base.BaseActivity;
import com.example.bookmanager.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * user list adapter
 */
public class ItemUserAdapter extends BaseAdapter implements View.OnClickListener {

    private List<Student> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public void setObjects(List<Student> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    public ItemUserAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Student getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_user, parent, false);
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
    private void initializeViews(Student object, ViewHolder holder) {
        holder.result.setText(object.getStudent_name() + " (" + object.getStudent_code() + ") ");
        holder.del.setTag(object);
        holder.del.setOnClickListener(this);
    }

    /**
     * delete click event listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        final Student student = (Student) v.getTag();
        new AlertDialog.Builder(context)
                .setTitle("Tip")
                .setMessage("Confirm delete user? ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final UserManagerActivity activity = (UserManagerActivity) context;
                        activity.getIDataFetcher().deleteUser(student, new FirebaseDeleteResult() {
                            @Override
                            public void deleteResult(boolean success, String msg) {
                                ToastUtil.tost(context, msg);
                                if (success) {
                                    activity.getData();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    /**
     * widgets buffer
     */
    protected class ViewHolder {
        private TextView result;
        private ImageView del;

        public ViewHolder(View view) {
            result = (TextView) view.findViewById(R.id.result);
            del = (ImageView) view.findViewById(R.id.del);
        }
    }
}
