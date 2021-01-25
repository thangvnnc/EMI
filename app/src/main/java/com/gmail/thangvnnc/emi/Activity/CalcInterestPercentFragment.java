package com.gmail.thangvnnc.emi.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
    private TextView _txtClickContact = null;

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
        _txtClickContact = view.findViewById(R.id.txtClickContact);

        handler = mHandler;

        _txtClickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSupporter();
            }
        });
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

    @SuppressLint("SetTextI18n")
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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

        if (loanAmountD > nPaymentsI*emiD) {
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

    public void showDialogSupporter() {
        final String message = _context.getString(R.string.phone_support);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(_context);
        builder1.setTitle("Hổ trợ tư vấn lãi suất");
        builder1.setMessage("Bạn có nhu cầu vay vốn hãy liên hệ đến tôi qua số điện thoại " + message+ "\nGặp Cẩm Tiên");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Gọi ngay",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel: " + message));
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Nhắn tin",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String message = _context.getString(R.string.phone_support);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + message));
                        intent.putExtra("sms_body", "Hãy tư vấn vay giúp tôi. Tôi muốn vay ");
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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
