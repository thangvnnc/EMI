package com.gmail.thangvnnc.emi.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmail.thangvnnc.emi.Controller.Common;
import com.gmail.thangvnnc.emi.Controller.CurrencyEditText;
import com.gmail.thangvnnc.emi.Controller.EMI;
import com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryEmi;
import com.gmail.thangvnnc.emi.Dialog.DialogHistory;
import com.gmail.thangvnnc.emi.Model.MEmiHistory;
import com.gmail.thangvnnc.emi.R;

public class CalcEMIFragment extends Fragment {
    private Context _context = null;
    private View _view = null;

    private CurrencyEditText _edtLoanAmount = null;
    private EditText _edtNPayments = null;
    private EditText _edtInterest = null;

    private TextView _txtEmi = null;
    private TextView _txtPercent = null;

    private Button _btnCalc = null;
    private Button _btnDetails = null;
    private LinearLayout _lnlControl = null;
    private Button _btnReset = null;
    private Button _btnResetAll = null;

    public final static String INTENT_LOANAMOUNT = "LOANAMOUNT";
    public final static String INTENT_INTEREST = "INTEREST";
    public final static String INTENT_PERCENT = "PERCENT";
    public final static String INTENT_NPAYMENTS = "NPAYMENTS";
    public final static String INTENT_TOTALALL = "TOTALALL";
    public final static String INTENT_EMID = "EMID";

    private double loanAmountD = 0;
    private int nPaymentsI = 0;
    private double emiD = 0;
    private double interestD = 0;
    private double percentD = 0;
    private double totalAllD = 0;

    public CalcEMIFragment() {
    }

    public static CalcEMIFragment newInstance(Context context) {
        CalcEMIFragment fragment = new CalcEMIFragment();
        fragment._context = context;
        return fragment;
    }

    private void initWidget(View view) {
        _edtLoanAmount = view.findViewById(R.id.edtLoanAmount);
        _edtNPayments = view.findViewById(R.id.edtNPayments);
        _edtInterest = view.findViewById(R.id.edtInterest);

        _txtEmi = view.findViewById(R.id.txtEmi);
        _txtPercent = view.findViewById(R.id.txtPercent);

        _btnCalc = view.findViewById(R.id.btnCalc);
        _btnDetails = view.findViewById(R.id.btnDetails);
        _btnReset = view.findViewById(R.id.btnReset);
        _btnResetAll = view.findViewById(R.id.btnResetAll);

        _lnlControl = view.findViewById(R.id.lnlControl);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _view = view;

        initWidget(view);
        initEventButton();
    }

    private boolean isValid() {
        String loanAmount = _edtLoanAmount.getText().toString();
        String nPayments = _edtNPayments.getText().toString();
        String edtInterest = _edtInterest.getText().toString();

        if ("".equals(loanAmount) || "".equals(nPayments) || "".equals(edtInterest)) {
            return false;
        }
        loanAmountD = _edtLoanAmount.getDoubleValue();
        nPaymentsI = Integer.parseInt(nPayments);
        interestD = Double.parseDouble(edtInterest);

        if (loanAmountD <= 0 || nPaymentsI <= 0 || interestD <= 0) {
            return false;
        }

        if (interestD >= 100) {
            return false;
        }

        return true;
    }

    private void initEventButton() {
        _btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isValid()) {
                    Common.showErrorInputData(_view);
                    return;
                }
                calc();
            }
        });

        _btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableEdittext(true);
            }
        });

        _btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, ReportDetail.class);
                intent.putExtra(INTENT_LOANAMOUNT, loanAmountD);
                intent.putExtra(INTENT_INTEREST, interestD);
                intent.putExtra(INTENT_NPAYMENTS, nPaymentsI);
                intent.putExtra(INTENT_TOTALALL, totalAllD);
                intent.putExtra(INTENT_EMID, emiD);
                intent.putExtra(INTENT_PERCENT, percentD);
                startActivity(intent);
            }
        });

        _btnResetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEnableEdittext(true);
                _edtLoanAmount.setText("");
                _edtNPayments.setText("");
                _edtInterest.setText("");
            }
        });
    }

    private void calc() {
        setEnableEdittext(false);
        emiD = EMI.calEmi(loanAmountD, interestD, nPaymentsI);
        double totalInterestRatePayment = EMI.calTotalInterestRatePayment(loanAmountD, interestD, nPaymentsI);
//        String totalInterestRatePaymentString = Common.formatCurrency(totalInterestRatePayment);
//        _txtTotalInterestRatePayment.setText(totalInterestRatePaymentString);
//
//        totalAllD = EMI.calTotalInterestAll(loanAmountD, interestD, nPaymentsI);
//        String totalAllString = Common.formatCurrency(totalAllD);
//        _txtTotalAll.setText(totalAllString);

        percentD = EMI.calculatePercent(loanAmountD, totalInterestRatePayment, nPaymentsI);
        _txtPercent.setText(String.valueOf(percentD));

        _txtEmi.setText(Common.formatCurrency(emiD));
    }

    public void setEnableEdittext(boolean enableEdittext) {
        _edtLoanAmount.setEnabled(enableEdittext);
        _edtNPayments.setEnabled(enableEdittext);
        _edtInterest.setEnabled(enableEdittext);

        _btnReset.setVisibility(View.VISIBLE);
        _lnlControl.setVisibility(View.VISIBLE);
        _btnCalc.setVisibility(View.GONE);

        if (enableEdittext) {
            _btnReset.setVisibility(View.GONE);
            _lnlControl.setVisibility(View.GONE);
            _btnCalc.setVisibility(View.VISIBLE);

            _txtPercent.setText("0");
            _txtEmi.setText("0");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_calc_emi, container, false);
    }
}
