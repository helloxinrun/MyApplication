package com.ixinrun.app_base.utils;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * 描述：简单化TextWatcher的功能
 * </p>
 *
 * @author : ixinrun
 * @date : 2018/12/24
 */
public abstract class SimpleTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public abstract void afterTextChanged(Editable s);
}
