package com.learn.kdnn.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.learn.kdnn.R;

public class ApplicationUiUtils {

    public static void showSoftInput(Activity activity, TextInputEditText et) {
        Object inputService = activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodManager imm = (InputMethodManager) inputService;
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideSoftInput(Activity activity) {

        Object inputService = activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        InputMethodManager imm = (InputMethodManager) inputService;
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }

    }

    public static void showCustomToast(Context context, int duration, String message, LayoutInflater inflater){
        Toast toast = new Toast(context);
        View view = inflater.inflate(R.layout.custom_toast, null, false);
        TextView toastMessage = view.findViewById(R.id.toast_message);
        toastMessage.setText(message);
        toast.setView(view);
        toast.setDuration(duration);
        toast.show();
    };
}
