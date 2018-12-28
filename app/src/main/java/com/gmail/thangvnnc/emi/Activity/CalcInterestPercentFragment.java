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

public class CalcInterestPercentFragment extends Fragment {
    private Context _context = null;
    private View _view = null;

    private CurrencyEditText _edtLoanAmount = null;
    private EditText _edtNPayments = null;
    private CurrencyEditText _edtEMI = null;

    private TextView _txtInterestRate = null;
    private TextView _txtPercent = null;
    private TextView _txtTotalInterestRatePayment = null;
    private TextView _txtTotalAll = null;

    private Button _btnCalc = null;
    private Button _btnDetails = null;
    private LinearLayout _lnlControl = null;
    private Button _btnReset = null;
    private Button _btnResetAll = null;

    public static Handler handler = null;
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

    public CalcInterestPercentFragment() {
    }

    public static CalcInterestPercentFragment newInstance(Context context) {
        CalcInterestPercentFragment fragment = new CalcInterestPercentFragment();
        fragment._context = context;
        return fragment;
    }

    private void initWidget(View view) {
        _edtLoanAmount = view.findViewById(R.id.edtLoanAmount);
        _edtNPayments = view.findViewById(R.id.edtNPayments);
        _edtEMI = view.findViewById(R.id.edtEMI);

        _txtInterestRate = view.findViewById(R.id.txtInterestRate);
        _txtPercent = view.findViewById(R.id.txtPercent);
        _txtTotalInterestRatePayment = view.findViewById(R.id.txtTotalInterestRatePayment);
        _txtTotalAll = view.findViewById(R.id.txtTotalAll);

        _btnCalc = view.findViewById(R.id.btnCalc);
        _btnDetails = view.findViewById(R.id.btnDetails);
        _btnReset = view.findViewById(R.id.btnReset);
        _btnResetAll = view.findViewById(R.id.btnResetAll);

        _lnlControl = view.findViewById(R.id.lnlControl);
        handler = mHandler;
    }

    @SuppressLint("HandlerLeak") public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (DialogHistory.DLG_HISTORY_RESULT == msg.what) {
                MEmiHistory mEmiHistory = (MEmiHistory) msg.obj;
                setValueFragment(mEmiHistory);
            }
        }
    };

    private void setValueFragment(MEmiHistory mEmiHistory) {
        loanAmountD = mEmiHistory.loanAmount;
        emiD =  mEmiHistory.emi;
        nPaymentsI = mEmiHistory.nPayment;
        _edtLoanAmount.setText(CurrencyEditText.formatDec(loanAmountD+""));
        _edtEMI.setText(CurrencyEditText.formatDec(emiD+""));
        _edtNPayments.setText(nPaymentsI+"");
        calc();
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
        String edtEMI = _edtEMI.getText().toString();

        if ("".equals(loanAmount) || "".equals(nPayments) || "".equals(edtEMI)) {
            return false;
        }
        loanAmountD = _edtLoanAmount.getDoubleValue();
        nPaymentsI = Integer.parseInt(nPayments);
        emiD = _edtEMI.getDoubleValue();

        if (loanAmountD <= 0 || nPaymentsI <= 0 || emiD <= 0) {
            return false;
        }

        if (loanAmountD < emiD) {
            return false;
        }
        return true;
    }

    private void saveHistory(){
        DBHistoryEmi dbHistoryEmi = new DBHistoryEmi(_context);
        MEmiHistory mEmiHistory = new MEmiHistory(loanAmountD, emiD, nPaymentsI);
        dbHistoryEmi.add(mEmiHistory);
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
                saveHistory();
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
                _edtEMI.setText("");
            }
        });
    }

    private void calc() {
        setEnableEdittext(false);

        interestD = EMI.calInterest(loanAmountD, emiD, nPaymentsI);
        _txtInterestRate.setText(String.valueOf(interestD));

        double totalInterestRatePayment = EMI.calTotalInterestRatePayment(loanAmountD, interestD, nPaymentsI);
        String totalInterestRatePaymentString = Common.formatCurrency(totalInterestRatePayment);
        _txtTotalInterestRatePayment.setText(totalInterestRatePaymentString);

        totalAllD = EMI.calTotalInterestAll(loanAmountD, interestD, nPaymentsI);
        String totalAllString = Common.formatCurrency(totalAllD);
        _txtTotalAll.setText(totalAllString);

        percentD = EMI.calculatePercent(loanAmountD, totalInterestRatePayment, nPaymentsI);
        _txtPercent.setText(String.valueOf(percentD));
    }

    public void setEnableEdittext(boolean enableEdittext) {
        _edtLoanAmount.setEnabled(enableEdittext);
        _edtNPayments.setEnabled(enableEdittext);
        _edtEMI.setEnabled(enableEdittext);

        _btnReset.setVisibility(View.VISIBLE);
        _lnlControl.setVisibility(View.VISIBLE);
        _btnCalc.setVisibility(View.GONE);

        if (enableEdittext) {
            _btnReset.setVisibility(View.GONE);
            _lnlControl.setVisibility(View.GONE);
            _btnCalc.setVisibility(View.VISIBLE);

            _txtInterestRate.setText("0");
            _txtPercent.setText("0");
            _txtTotalAll.setText("0");
            _txtTotalInterestRatePayment.setText("0");
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
        return inflater.inflate(R.layout.frag_calc_interest_percent, container, false);
    }
}
