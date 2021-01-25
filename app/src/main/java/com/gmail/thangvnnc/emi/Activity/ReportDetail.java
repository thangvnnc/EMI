package com.gmail.thangvnnc.emi.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gmail.thangvnnc.emi.Adapter.MEmiAdapter;
import com.gmail.thangvnnc.emi.Controller.Common;
import com.gmail.thangvnnc.emi.Dialog.DialogInterestDetail;
import com.gmail.thangvnnc.emi.Model.MEmi;
import com.gmail.thangvnnc.emi.R;

import java.util.ArrayList;
import java.util.List;

public class ReportDetail extends AppCompatActivity {
    private Context _context = null;

    private TextView _txtLoanAmount = null;
    private TextView _txtTotalAll = null;
    private TextView _txtInterestRate = null;
    private TextView _txtNPayments = null;
    private TextView _txtEmi = null;
    private TextView _txtPercent = null;
    private ListView _lvReport = null;

    private double loanAmountD = 0;
    private int nPaymentsD = 0;
    private double interestD = 0;
    private double totalAllD = 0;
    private double emiD = 0;
    private double percentD = 0;

    private List<MEmi> emis = new ArrayList<>();
    private MEmiAdapter _mEmiAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_report_detail);
        setTitle("Bản chi tiết đóng tiền");
        _context = this;

        getData();
        initWidget();
        setValueUI();
        calcData();
    }

    private void getData() {
        loanAmountD = getIntent().getDoubleExtra(CalcInterestPercentFragment.INTENT_LOANAMOUNT, 0);
        nPaymentsD = getIntent().getIntExtra(CalcInterestPercentFragment.INTENT_NPAYMENTS, 0);
        interestD = getIntent().getDoubleExtra(CalcInterestPercentFragment.INTENT_INTEREST, 0);
        percentD = getIntent().getDoubleExtra(CalcInterestPercentFragment.INTENT_PERCENT, 0);
        totalAllD = getIntent().getDoubleExtra(CalcInterestPercentFragment.INTENT_TOTALALL, 0);
        emiD = getIntent().getDoubleExtra(CalcInterestPercentFragment.INTENT_EMID, 0);
    }

    private void setValueUI() {
        try {
            String loanAmount = Common.formatCurrency(loanAmountD);
            String totalAll = Common.formatCurrency(totalAllD);
            String emi = Common.formatCurrency(emiD);

            _txtNPayments.setText(String.valueOf(nPaymentsD));
            _txtLoanAmount.setText(loanAmount);
            _txtInterestRate.setText(String.valueOf(interestD));
            _txtPercent.setText(String.valueOf(percentD));
            _txtTotalAll.setText(totalAll);
            _txtEmi.setText(emi);

            _mEmiAdapter = new MEmiAdapter(_context, emis);
            _lvReport.setAdapter(_mEmiAdapter);
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(_context, "Lỗi exception", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initWidget() {
        _txtLoanAmount = findViewById(R.id.txtLoanAmount);
        _txtTotalAll = findViewById(R.id.txtTotalAll);
        _txtInterestRate = findViewById(R.id.txtInterestRate);
        _txtNPayments = findViewById(R.id.txtNPayments);
        _txtPercent = findViewById(R.id.txtPercent);
        _txtEmi = findViewById(R.id.txtEMI);
        _lvReport = findViewById(R.id.lvReport);
        
        _lvReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final List<MEmi> emiList = emis;
                DialogInterestDetail dialogInterestDetail = new DialogInterestDetail(_context);
                dialogInterestDetail.setData(emiList, i);
                dialogInterestDetail.show();
            }
        });
    }

    private void calcData() {
        List<MEmi> dataCalc = calcEmi(loanAmountD, interestD, nPaymentsD);
        reloadDataReportList(dataCalc);
    }

    private List<MEmi> calcEmi(double loanAmountD, double interestD, int nPaymentsD) {
        List<MEmi> dataCalc = new ArrayList<>();

        double loanAmount = loanAmountD;
        double interest = interestD;

        for(int n = 0; n < nPaymentsD; n++) {
            double monthlyInterest = loanAmount * (interest / 100);
            double monthlyPrincipal = emiD - monthlyInterest;
            double principalBalance = loanAmount - monthlyPrincipal;
            loanAmount = principalBalance;
            if(n == nPaymentsD - 1) {
                principalBalance = 0;
            }
            MEmi mEmi = new MEmi((n+1), monthlyInterest, monthlyPrincipal, principalBalance);
            dataCalc.add(mEmi);
        }

        return dataCalc;
    }

    private void reloadDataReportList(List<MEmi> emiList) {
        emis.clear();
        emis.addAll(emiList);
        _mEmiAdapter.notifyDataSetChanged();
    }
}
