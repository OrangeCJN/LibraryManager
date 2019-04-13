package com.example.bookmanager.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.bookmanager.Constant;
import com.example.bookmanager.R;
import com.example.bookmanager.firebase.DataFetcherImpl;
import com.example.bookmanager.firebase.IDataFetcher;
import com.example.bookmanager.ui.MainActivity;
import com.example.bookmanager.util.SPUtils;

/**
 * base Activity for all Activity pages
 */
public abstract class BaseActivity extends AppCompatActivity {

    // current logged-in user type
    protected String mCurrentUserType;
    // current logged-in user username
    protected String mCurrentUserName;
    // current logged-in user student name (if user type is student)
    protected String mCurrentStudentName;
    // current logged-in user password (if user type is student)
    protected String mCurrentStudentPassword;
    // data fetcher singleton object
    protected IDataFetcher mIDataFetcher;
    // current activity reference
    protected BaseActivity mActivity;
    // current logged-in user type (int)
    protected int userType = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set layout
        setContentView(getContentId());
        // activity reference
        mActivity = this;
        // initialize SharedPreference utility class
        SPUtils.init(this);
        // initialize IDataFetcher object
        mIDataFetcher = DataFetcherImpl.getDataFetcher(this);
        // get user's recorded info
        mCurrentUserType = SPUtils.getString(Constant.SP_USER_TYPE);
        mCurrentUserName = SPUtils.getString(Constant.SP_USER_USER_NAME);
        mCurrentStudentName = SPUtils.getString(Constant.SP_USER_NAME);
        mCurrentStudentPassword = SPUtils.getString(Constant.SP_USER_PASSWORD);
        if (SPUtils.getString(Constant.SP_USER_TYPE).equals(Constant.USER_TYPE_ADMIN)) {
            userType = 1;
        }
        // set display back button
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * set title
     *
     * @param title
     */
    protected void setTitle(String title) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
        }
    }

    /**
     * set subtitle
     *
     * @param subTitle
     */
    protected void setSubTitle(String subTitle) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setSubtitle(subTitle);
        }
    }

    /**
     * menu click event listener
     * click back button to goto up/home in all activities
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // finish event
            return true;
        }
        if (item.getItemId() == R.id.exit) {
            SPUtils.init(this);
            SPUtils.putString("type", "");
            SPUtils.putString("username", "");
            SPUtils.putString("password", "");
            startActivity(new Intent(this, MainActivity.class));
            super.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * need implementation in subclasses
     *
     * @return layout id
     */
    protected abstract int getContentId();

    public String getCurrentUserType() {
        return mCurrentUserType;
    }

    public String getCurrentUserName() {
        return mCurrentUserName;
    }

    public String getCurrentStudentName() {
        return mCurrentStudentName;
    }

    public String getCurrentStudentPassword() {
        return mCurrentStudentPassword;
    }

    public IDataFetcher getIDataFetcher() {
        return mIDataFetcher;
    }

    public BaseActivity getActivity() {
        return mActivity;
    }
}
