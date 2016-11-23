package com.tnxy.locshare.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tnxy.locshare.R;
import com.tnxy.locshare.app.App;
import com.tnxy.locshare.app.Constants;
import com.tnxy.locshare.base.BaseActivity;
import com.tnxy.locshare.ui.fragment.AboutFragment;
import com.tnxy.locshare.ui.fragment.HomeFragment;
import com.tnxy.locshare.ui.fragment.MapFragment;
import com.tnxy.locshare.ui.fragment.MessageFragment;
import com.tnxy.locshare.ui.fragment.SecondFragment;
import com.tnxy.locshare.ui.fragment.SettingFragment;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    ActionBarDrawerToggle toggle;

    private int hideFragment = Constants.TYPE_HOME;
    private int showFragment = Constants.TYPE_HOME;
    HomeFragment mHomeFragment;
    SecondFragment mSecondFragment;
    MapFragment mMapFragment;
    MessageFragment mMessageFragment;
    SettingFragment mSettingFragment;
    AboutFragment mAboutFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initBase();
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        showFragment = Constants.TYPE_HOME;
        hideFragment = Constants.TYPE_HOME;
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));


    }

    private void initBase() {
        setToolbar(toolbar, "主页");
        mHomeFragment = new HomeFragment();
        mSecondFragment = new SecondFragment();
        mMapFragment = new MapFragment();
        mMessageFragment = new MessageFragment();
        mSettingFragment = new SettingFragment();
        mAboutFragment = new AboutFragment();

        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        loadMultipleRootFragment(R.id.flMainContent, 0, mHomeFragment, mSecondFragment, mMapFragment, mMessageFragment);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            showExitDialog();
        }
    }

    private SupportFragment getTargetFragment(int item) {
        switch (item) {
            case Constants.TYPE_HOME:
                return mHomeFragment;
            case Constants.TYPE_SECOND:
                return mSecondFragment;
            case Constants.TYPE_MAP:
                return mMapFragment;
            case Constants.TYPE_MESSAGE:
                return mMessageFragment;
            case Constants.TYPE_SETTING:
                return mSettingFragment;
            case Constants.TYPE_ABOUT:
                return mAboutFragment;
            default:
                return mHomeFragment;
        }
    }

    protected void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressedSupport());
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定要退出LocShare吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", (dialogInterface, i) -> {
            App.getInstance().exitApp();
        });
        builder.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                showFragment = Constants.TYPE_HOME;
                break;
            case R.id.nav_second:
                showFragment = Constants.TYPE_SECOND;
                break;
            case R.id.nav_map:
                showFragment = Constants.TYPE_MAP;
                break;
            case R.id.nav_message:
                showFragment = Constants.TYPE_MESSAGE;
                break;
            case R.id.nav_setting:
                showFragment = Constants.TYPE_SETTING;
                break;
            case R.id.nav_about:
                showFragment = Constants.TYPE_ABOUT;
                break;
        }
        item.setCheckable(true);
        toolbar.setTitle(item.getTitle());
        drawer.closeDrawer(GravityCompat.START);
        showHideFragment(getTargetFragment(showFragment), getTargetFragment(hideFragment));
        hideFragment = showFragment;
        return true;
    }
}
