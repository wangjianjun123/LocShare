package com.tnxy.locshare.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tnxy.locshare.app.App;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:  Actitivity基类
 * User:         Wang Jian jun
 * Date:         2016-11-17  08:48
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);
        mContext = this;
        App.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnBinder.unbind();
        App.getInstance().removeActivity(this);
    }

    protected abstract int getLayout();
}
