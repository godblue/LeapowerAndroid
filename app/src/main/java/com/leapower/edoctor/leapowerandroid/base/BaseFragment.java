package com.leapower.edoctor.leapowerandroid.base;

import android.content.Context;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by chao on 2018/05/15.
 */
public class BaseFragment extends SupportFragment {
    protected InputMethodManager inputMethodManager;
    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        _mActivity.finish();
        return true;
    }
    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
