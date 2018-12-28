package com.gmail.thangvnnc.emi.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.thangvnnc.emi.R;

public class CommentFragment extends Fragment {
    private Context _context = null;
    private View _view = null;


    private EditText _edtComment = null;
    private Button _btnSend = null;


    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(Context context) {
        CommentFragment fragment = new CommentFragment();
        fragment._context = context;
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _view = view;
        initWidget(view);
        initEventButton();
    }

    private void initEventButton() {
        _btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String message = _edtComment.getText().toString();
                AlertDialog.Builder builder1 = new AlertDialog.Builder(_context);
                builder1.setTitle("Góp ý của bạn");
                builder1.setMessage(message);
                builder1.setCancelable(true);
                builder1.setNegativeButton(
                        "Nhắn tin",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String message = _context.getString(R.string.phone_support);
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + message));
                                intent.putExtra("sms_body", message);
                                startActivity(intent);
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

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
