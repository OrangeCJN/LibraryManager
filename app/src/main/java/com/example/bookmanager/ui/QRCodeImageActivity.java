package com.example.bookmanager.ui;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.baozi.Zxing.utils.ZXingUtils;
import com.example.bookmanager.R;
import com.example.bookmanager.ui.base.BaseActivity;

/**
 * 展示二维码图片的页面
 */
public class QRCodeImageActivity extends BaseActivity {

    public static final String CODE_SPLIT = ",;,";
    public static final String CODE_PRE = "kxrpJMIscfrvmo9u";
    public static final String CODE_NEXT = "kOXVmBZ5SZUCLahl";

    private ImageView image;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Book QRCode");
        image = findViewById(R.id.image);
        image.setImageBitmap(ZXingUtils.createQRCodeWithLogo(CODE_PRE + CODE_SPLIT + getIntent()
                .getLongExtra("bookId", 0L) + CODE_SPLIT + CODE_NEXT, ((BitmapDrawable) getDrawable(R.mipmap.ic)).getBitmap()));
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_qr_code;
    }
}
