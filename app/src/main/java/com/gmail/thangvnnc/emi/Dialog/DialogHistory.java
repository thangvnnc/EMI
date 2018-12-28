package com.gmail.thangvnnc.emi.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gmail.thangvnnc.emi.Activity.CalcInterestPercentFragment;
import com.gmail.thangvnnc.emi.Adapter.MEmiHistoryAdapter;
import com.gmail.thangvnnc.emi.DBSQLite.History.History.DBHistoryEmi;
import com.gmail.thangvnnc.emi.Model.MEmiHistory;
import com.gmail.thangvnnc.emi.R;

import java.util.ArrayList;
import java.util.List;

public class DialogHistory extends Dialog {
    private Context _context = null;

    private ListView _lvHistory = null;
    private List<MEmiHistory> _mEmiHistories = null;
    private MEmiHistoryAdapter _mEmiHistoryAdapter = null;

    public static final int DLG_HISTORY_RESULT = 1;

    public DialogHistory(@NonNull Context context) {
        super(context);
        _context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dlg_history);
        initWidget();
        initEvent();
        loadData();
    }

    private void initEvent() {
        _lvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message message = new Message();
                message.obj = _mEmiHistories.get(i);
                message.what = DLG_HISTORY_RESULT;
                if (CalcInterestPercentFragment.handler != null) {
                    CalcInterestPercentFragment.handler.sendMessage(message);
                }
                hide();
            }
        });
    }

    private void loadData() {
        DBHistoryEmi dbHistoryEmi = new DBHistoryEmi(_context);
        List<MEmiHistory> listLoad = dbHistoryEmi.getAll();
        reloadDataReportList(listLoad);
    }

    private void initWidget() {
        _lvHistory = findViewById(R.id.lvHistory);
        _mEmiHistories = new ArrayList<>();
        _mEmiHistoryAdapter = new MEmiHistoryAdapter(_context, _mEmiHistories);
        _lvHistory.setAdapter(_mEmiHistoryAdapter);
    }

    private void reloadDataReportList(List<MEmiHistory> emiList) {
        _mEmiHistories.clear();
        _mEmiHistories.addAll(emiList);
        _mEmiHistoryAdapter.notifyDataSetChanged();
    }
}
