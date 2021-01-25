package com.gmail.thangvnnc.emi.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gmail.thangvnnc.emi.Controller.Common;
import com.gmail.thangvnnc.emi.Model.MEmi;
import com.gmail.thangvnnc.emi.R;

import java.util.List;

public class DialogInterestDetail extends Dialog {
    private Context _context = null;
    private TextView _txtTitle = null;
    private TextView _txtTotalInterestRatePayment = null;
    private TextView _txtTotalAmount = null;
    private TextView _txtTotalAll = null;
    private List<MEmi> emis = null;
    private int position = -1;

    public DialogInterestDetail(@NonNull Context context) {
        super(context);
        _context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_interest_detail);
        initWidget();
        initEvent();
        loadData();
    }

    private void initEvent() {

    }

    private void loadData() {
        double totalInterestRatePayment = 0;
        double totalAmount = 0;
        double totalAll = 0;
        for (int idx = 0; idx <= this.position; idx++) {
            totalInterestRatePayment += emis.get(idx).monthlyInterest;
            totalAmount += emis.get(idx).monthlyPrincipal;
        }
        totalAll = totalInterestRatePayment + totalAmount;
        _txtTitle.setText(_txtTitle.getText().toString() + (position + 1));
        _txtTotalInterestRatePayment.setText(Common.formatCurrency(totalInterestRatePayment));
        _txtTotalAmount.setText(Common.formatCurrency(totalAmount));
        _txtTotalAll.setText(Common.formatCurrency(totalAll));
    }

    private void initWidget() {
        _txtTitle = findViewById(R.id.txtTitle);
        _txtTotalInterestRatePayment = findViewById(R.id.txtTotalInterestRatePayment);
        _txtTotalAmount = findViewById(R.id.txtTotalAmount);
        _txtTotalAll = findViewById(R.id.txtTotalAll);
    }

    public void setData(List<MEmi> emis, int position) {
        this.emis = emis;
        this.position = position;
    }
}
