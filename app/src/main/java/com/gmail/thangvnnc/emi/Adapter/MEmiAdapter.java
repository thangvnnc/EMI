package com.gmail.thangvnnc.emi.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gmail.thangvnnc.emi.Controller.Common;
import com.gmail.thangvnnc.emi.Model.MEmi;
import com.gmail.thangvnnc.emi.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class MEmiAdapter extends BaseAdapter {
    private Context mContext = null;
    private List<MEmi> mEmis = null;

    public MEmiAdapter(Context context, List<MEmi> emis) {
        mContext = context;
        mEmis = emis;
    }

    @Override
    public int getCount() {
        return mEmis.size();
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
            view = layoutInflater.inflate(R.layout.act_report_item, null);
            mEmiHolder = new MEmiHolder();
            mEmiHolder.txtIncrement = view.findViewById(R.id.txtIncrement);
            mEmiHolder.txtMonthlyInterest = view.findViewById(R.id.txtMonthlyInterest);
            mEmiHolder.txtMonthlyPrincipal = view.findViewById(R.id.txtMonthlyPrincipal);
            mEmiHolder.txtPrincipalBalance = view.findViewById(R.id.txtPrincipalBalance);
            view.setTag(mEmiHolder);
        }
        else {
            mEmiHolder = (MEmiHolder) view.getTag();
        }

        mEmiHolder.txtIncrement.setText(String.valueOf(mEmis.get(i).id));
        mEmiHolder.txtMonthlyInterest.setText(Common.formatCurrency(mEmis.get(i).monthlyInterest));
        mEmiHolder.txtMonthlyPrincipal.setText(Common.formatCurrency(mEmis.get(i).monthlyPrincipal));
        mEmiHolder.txtPrincipalBalance.setText(Common.formatCurrency(mEmis.get(i).principalBalance));
        return view;
    }

    public class MEmiHolder {
        public TextView txtIncrement;
        public TextView txtMonthlyInterest;
        public TextView txtMonthlyPrincipal;
        public TextView txtPrincipalBalance;
    }
}
