package com.cleanmanager.customerdialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.cleanmanager.R;


public class ConfirmDialog extends DialogFragment implements View.OnClickListener {

    private static final String TITLE = "title";
    private static final String MESSAGE = "message";
    private static final String YES_BTN_TEXT = "yes_btn_text";
    private static final String CANCEL_BTN_TEXT = "cancel_btn_text";

    Button btnOK, btnCancel;
    TextView tvTitle, tvInfo;
    OnDialogActionListener mDialogListener;

    public static ConfirmDialog newInstance(String title, String message,String yesBtnText, String cancelBtnText ) {
        ConfirmDialog fragment = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString(TITLE,title);
        args.putString(MESSAGE,message);
        args.putString(YES_BTN_TEXT,yesBtnText);
        args.putString(CANCEL_BTN_TEXT,cancelBtnText);
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmDialog() {}

    public interface OnDialogActionListener{
        public void onOKClick();
        public void onCancel();
    }

    public void setOnDialogBtnClickListener(OnDialogActionListener l){
        this.mDialogListener = l;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirm_dialog, container, false);
        btnOK = (Button) v.findViewById(R.id.btn_ok);
        btnCancel = (Button) v.findViewById(R.id.btn_cancel);
        tvTitle = (TextView) v.findViewById(R.id.tv_title);
        tvInfo =(TextView) v.findViewById(R.id.tv_info);

        Bundle args = getArguments();
        if(args != null){
            String title = args.getString(TITLE);
            String message = args.getString(MESSAGE);
            String yesBtnText = args.getString(YES_BTN_TEXT);
            String cancelBtnText = args.getString(CANCEL_BTN_TEXT);
            tvTitle.setText(title);
            tvInfo.setText(message);
            btnOK.setText(yesBtnText);
            btnCancel.setText(cancelBtnText);
        }


        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return d;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if(mDialogListener != null){
                    mDialogListener.onOKClick();
                }
                this.dismiss();
                break;
            case R.id.btn_cancel:
                if(mDialogListener != null){
                    mDialogListener.onCancel();
                }
                this.dismiss();
                break;
        }

    }

}
