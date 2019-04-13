package com.example.bookmanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bookmanager.R;
import com.example.bookmanager.ui.base.BaseActivity;

/**
 * 活动详情页
 */
public class ActivityDetailActivity extends BaseActivity {

    private TextView t;
    private ImageView img;
    private TextView c;

    private String ts,cs;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.share){
            shareText("BookManager",ts,cs);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_detail;
    }

    /**
     * 分享活动
     * @param dlgTitle
     * @param subject
     * @param content
     */
    private void shareText(String dlgTitle, String subject, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t = (TextView) findViewById(R.id.t);
        img = (ImageView) findViewById(R.id.img);
        c = (TextView) findViewById(R.id.c);
        setTitle("Activity details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t.setText(ts = getIntent().getStringExtra("t"));
        c.setText(cs = getIntent().getStringExtra("c"));
        Glide.with(this).load(getIntent().getStringExtra("i")).into(img);
    }

}
