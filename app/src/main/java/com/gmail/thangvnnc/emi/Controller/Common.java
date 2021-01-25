package com.gmail.thangvnnc.emi.Controller;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by SICVN0016 on 2018/12/19.
 */

public class Common {

    private static DecimalFormat formatter = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.US));

    public static String formatCurrency(Number number) {
        return formatter.format(number);
    }

    public static void showErrorInputData(View view) {
        showMessage(view, "Vui lòng nhập đúng dữ liệu");
    }

    public static void showMessage(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

}
