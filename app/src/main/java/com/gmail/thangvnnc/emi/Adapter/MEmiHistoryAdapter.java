package com.gmail.thangvnnc.emi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmail.thangvnnc.emi.Controller.Common;
import com.gmail.thangvnnc.emi.Model.MEmiHistory;
import com.gmail.thangvnnc.emi.R;

import java.util.List;

public class MEmiHistoryAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<MEmiHistory> mEmiHistories = null;

    public MEmiHistoryAdapter(Context context, List<MEmiHistory> emis) {
        mContext = context;
        mEmiHistories = emis;
    }

    @Override
    public int getCount() {
        return mEmiHistories.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MEmiHolder mEmiHolder = null;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.dlg_history_item, null);
            mEmiHolder = new MEmiHolder();
            mEmiHolder.txtIncrement = view.findViewById(R.id.txtIncrement);
            mEmiHolder.txtLoanAmount = view.findViewById(R.id.txtLoanAmount);
            mEmiHolder.txtEmi = view.findViewById(R.id.txtEmi);
            mEmiHolder.txtNPayments = view.findViewById(R.id.txtNPayments);
            view.setTag(mEmiHolder);
        }
        else {
            mEmiHolder = (MEmiHolder) view.getTag();
        }

        mEmiHolder.txtIncrement.setText(String.valueOf(mEmiHistories.get(i).id));
        mEmiHolder.txtLoanAmount.setText(Common.formatCurrency(mEmiHistories.get(i).loanAmount));
        mEmiHolder.txtEmi.setText(Common.formatCurrency(mEmiHistories.get(i).emi));
        mEmiHolder.txtNPayments.setText(Common.formatCurrency(mEmiHistories.get(i).nPayment));
        return view;
    }

    public class MEmiHolder {
        public TextView txtIncrement;
        public TextView txtLoanAmount;
        public TextView txtEmi;
        public TextView txtNPayments;
    }
}
