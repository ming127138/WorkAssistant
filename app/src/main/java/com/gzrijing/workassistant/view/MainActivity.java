package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gzrijing.workassistant.R;
import com.gzrijing.workassistant.base.BaseActivity;
import com.igexin.sdk.PushManager;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private ImageView iv_business;
    private ImageView iv_manage;
    private ImageView iv_acceptance;
    private ImageView iv_more;
    private FragmentManager fragmentManager; //用于对Fragment进行管理
    private BusinessFragment businessFragment;
    private ManageFragment manageFragment;
    private AcceptanceFragment acceptanceFragment;
    private MoreFragment moreFragment;
    private long firstTime = 0;
    private String userName;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initViews();
        setListeners();
    }

    private void initData() {
        SharedPreferences app = getSharedPreferences(
                "saveUser", MODE_PRIVATE);
        userName = app.getString("userName", "");

        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("fragId"));
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mToolbar.setTitle(userName + "的业务");
        setSupportActionBar(mToolbar);

        iv_business = (ImageView) findViewById(R.id.main_business_iv);
        iv_manage = (ImageView) findViewById(R.id.main_manage_iv);
        iv_acceptance = (ImageView) findViewById(R.id.main_acceptance_iv);
        iv_more = (ImageView) findViewById(R.id.main_more_iv);

        if (id == 0) {
            fragmentManager = getSupportFragmentManager();
            setTabSelection(0);
        } else if (id == 1) {
            fragmentManager = getSupportFragmentManager();
            setTabSelection(1);
        } else if (id == 2) {
            fragmentManager = getSupportFragmentManager();
            setTabSelection(2);
        } else if (id == 3) {
            fragmentManager = getSupportFragmentManager();
            setTabSelection(3);
        }
    }

    private void setListeners() {
        iv_business.setOnClickListener(this);
        iv_manage.setOnClickListener(this);
        iv_acceptance.setOnClickListener(this);
        iv_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_business_iv:
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                setTabSelection(0);
                break;
            case R.id.main_manage_iv:
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                setTabSelection(1);
                break;
            case R.id.main_acceptance_iv:
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                setTabSelection(2);
                break;
            case R.id.main_more_iv:
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                setTabSelection(3);
                break;
        }
    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     */
    private void setTabSelection(int index) {
        clearSelection();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                mToolbar.setTitle(userName + "的业务");
                iv_business.setImageResource(R.drawable.main_bottom_business_on);
                if (businessFragment == null) {
                    businessFragment = new BusinessFragment();
                    transaction.add(R.id.fragment_content, businessFragment);
                } else {
                    transaction.show(businessFragment);
                }
                break;
            case 1:
                mToolbar.setTitle("管理");
                iv_manage.setImageResource(R.drawable.main_bottom_manage_on);
                if (manageFragment == null) {
                    manageFragment = new ManageFragment();
                    transaction.add(R.id.fragment_content, manageFragment);
                } else {
                    transaction.show(manageFragment);
                }
                break;
            case 2:
                mToolbar.setTitle("验收");
                iv_acceptance.setImageResource(R.drawable.main_bottom_acceptance_on);
                if (acceptanceFragment == null) {
                    acceptanceFragment = new AcceptanceFragment();
                    transaction.add(R.id.fragment_content, acceptanceFragment);
                } else {
                    transaction.show(acceptanceFragment);
                }
                break;
            case 3:
                mToolbar.setTitle("更多");
                iv_more.setImageResource(R.drawable.main_bottom_more_on);
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.fragment_content, moreFragment);
                } else {
                    transaction.show(moreFragment);
                }
                break;

        }
        transaction.commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        iv_business.setImageResource(R.drawable.main_bottom_business_off);
        iv_manage.setImageResource(R.drawable.main_bottom_manage_off);
        iv_acceptance.setImageResource(R.drawable.main_bottom_acceptance_off);
        iv_more.setImageResource(R.drawable.main_bottom_more_off);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (businessFragment != null) {
            transaction.hide(businessFragment);
        }
        if (manageFragment != null) {
            transaction.hide(manageFragment);
        }
        if (acceptanceFragment != null) {
            transaction.hide(acceptanceFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }
    }

    /**
     * 按两次back键退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {    //如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;//更新firstTime
                    return true;
                } else {   //两次按键小于2秒时，退出应用
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

}
