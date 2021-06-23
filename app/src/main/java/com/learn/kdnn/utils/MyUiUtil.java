package com.learn.kdnn.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.textfield.TextInputEditText;

public class MyUiUtil {

    public static void showSoftInput(Activity activity, TextInputEditText et) {
        Object inputService = activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodManager imm = (InputMethodManager) inputService;
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftInput(Activity activity) {

        Object inputService = activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodManager imm = (InputMethodManager) inputService;
        View currentFocus = activity.getCurrentFocus();
        imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);

    }
}
