package com.gmail.thangvnnc.emi.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gmail.thangvnnc.emi.DBSQLite.History.Support.DBComment;
import com.gmail.thangvnnc.emi.DBServer.API.APIService;
import com.gmail.thangvnnc.emi.DBServer.API.ApiUtils;
import com.gmail.thangvnnc.emi.Model.MResponse;
import com.gmail.thangvnnc.emi.Model.MSupport;
import com.gmail.thangvnnc.emi.R;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {
    private Context _context = null;
    private View _view = null;

    private APIService _apiService = null;
    private EditText _edtComment = null;
    private Button _btnSend = null;
    private String _content = "";

    private final static String TAG = "CommentFragment";

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(Context context) {
        CommentFragment fragment = new CommentFragment();
        fragment._context = context;
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _view = view;
        _apiService = ApiUtils.getAPIService();
        initWidget(view);
        initEventButton();
    }

    private void initEventButton() {
        _btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _content = _edtComment.getText().toString();
                if(!TextUtils.isEmpty(_content)) {
                    sendPost(_content);
                }
            }
        });
    }

    public void sendPost(final String comment) {
        _apiService.saveSupport(null, comment, new Date().getTime()).enqueue(new Callback<MResponse>() {
            @Override
            public void onResponse(Call<MResponse> call, Response<MResponse> response) {
                Toast.makeText(_context, "Gửi thành công", Toast.LENGTH_LONG).show();
                _edtComment.setText("");
                Log.e(TAG, "OK");
            }

            @Override
            public void onFailure(Call<MResponse> call, Throwable t) {
                DBComment dbComment = new DBComment(_context);
                MSupport mSupport = new MSupport();
                mSupport.setContent(comment);
                Date date = new Date();
                mSupport.setCreatedAt(date.getTime());
                dbComment.add(mSupport);
                _edtComment.setText("");
                Toast.makeText(_context, "Gửi thành công", Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void showResponse(String response) {
//        if(mResponseTv.getVisibility() == View.GONE) {
//            mResponseTv.setVisibility(View.VISIBLE);
//        }
//        mResponseTv.setText(response);
//    }

    private void initWidget(View view) {
        _btnSend = view.findViewById(R.id.btnSendComment);
        _edtComment = view.findViewById(R.id.edtComment);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_comment, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
