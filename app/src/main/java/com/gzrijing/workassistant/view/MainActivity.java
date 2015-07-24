package com.gzrijing.workassistant.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gzrijing.workassistant.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ImageView iv_business;
    private ImageView iv_manage;
    private ImageView iv_acceptance;
    private ImageView iv_more;
    private FragmentManager fragmentManager; //用于对Fragment进行管理
    private BusinessFragment businessFragment;
    private ManageFragment manageFragment;
    private AcceptanceFragment acceptanceFragment;
    private MoreFragment moreFragment;
    private TextView tv_actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        int titleId = Resources.getSystem().getIdentifier(
                "action_bar_title", "id", "android");
        tv_actionBar = (TextView) findViewById(titleId);


        Intent intent = getIntent();
        int id = Integer.parseInt(intent.getStringExtra("fragId"));
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

    private void initViews() {
        iv_business = (ImageView) findViewById(R.id.main_business_iv);
        iv_manage = (ImageView) findViewById(R.id.main_manage_iv);
        iv_acceptance = (ImageView) findViewById(R.id.main_acceptance_iv);
        iv_more = (ImageView) findViewById(R.id.main_more_iv);
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
                tv_actionBar.setText("业务");
                iv_business.setImageResource(R.drawable.main_bottom_business_on);
                if (businessFragment == null) {
                    businessFragment = new BusinessFragment();
                    transaction.add(R.id.fragment_content, businessFragment);
                } else {
                    transaction.show(businessFragment);
                }
                break;
            case 1:
                tv_actionBar.setText("管理");
                iv_manage.setImageResource(R.drawable.main_bottom_manage_on);
                if (manageFragment == null) {
                    manageFragment = new ManageFragment();
                    transaction.add(R.id.fragment_content, manageFragment);
                } else {
                    transaction.show(manageFragment);
                }
                break;
            case 2:
                tv_actionBar.setText("验收");
                iv_acceptance.setImageResource(R.drawable.main_bottom_acceptance_on);
                if (acceptanceFragment == null) {
                    acceptanceFragment = new AcceptanceFragment();
                    transaction.add(R.id.fragment_content, acceptanceFragment);
                } else {
                    transaction.show(acceptanceFragment);
                }
                break;
            case 3:
                tv_actionBar.setText("更多");
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

}
