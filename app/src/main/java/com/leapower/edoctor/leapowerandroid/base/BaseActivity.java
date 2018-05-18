package com.leapower.edoctor.leapowerandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.leapower.edoctor.leapowerandroid.event.HttpRequestResultEvent;
import com.leapower.edoctor.leapowerandroid.event.NetError;
import com.leapower.edoctor.leapowerandroid.utils.ActivityUtils;
import com.leapower.edoctor.leapowerandroid.utils.netutil.LeapowerNet;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        EventBus.getDefault().register(this);
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
    /**
     * 显示长toast
     * @param msg
     */
    public void toastLong(String msg){
        if (null == toast) {
            toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText(msg);
            toast.show();
        } else {
            toast.setText(msg);
            toast.show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onHttpRequestResultEvent(HttpRequestResultEvent httpRequestResultEvent) {

    }
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onNetError(NetError netError) {
        if (netError != null){
            switch (netError.getErrorCode()){
                case -1:
                    toastShort("网络异常，请检查网络连接");
                    break;
                case 401:
                    break;
                case 403:
                    break;
                case 500:
                    toastShort("服务器开小差");
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityUtils.removeActivity(this);

    }
}
