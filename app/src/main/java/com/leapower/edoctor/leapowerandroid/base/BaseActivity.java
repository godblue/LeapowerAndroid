package com.leapower.edoctor.leapowerandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.leapower.edoctor.leapowerandroid.utils.ActivityUtils;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by chao on 2018/05/15.
 */
public abstract class BaseActivity extends SupportActivity {
    /***封装toast对象**/
    private static Toast toast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(intiLayout());
        ActivityUtils.addActivity(this);
        //初始化控件
        initView();
        //设置数据
        initData();
    }
    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();

    /**
     * 显示短toast
     * @param msg
     */
    public void toastShort(String msg){
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.removeActivity(this);
    }
}
