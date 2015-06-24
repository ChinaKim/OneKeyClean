package com.imageviewdimen.touchspring.onekeyclean.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * Created by KIM on 2015/6/19 0019.
 */
public class ProgressDialogFragment extends DialogFragment {

    int mIndeterminateDrawable;
    String mMessage;
    static View mContentView;

    public static  ProgressDialogFragment newInstance(int indeterminateDrawable,String mMessage){
        ProgressDialogFragment fd = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putInt("indeterminateDrawable",indeterminateDrawable);
        args.putString("mMessage",mMessage);
        fd.setArguments(args);
        return fd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mIndeterminateDrawable =getArguments().getInt("mIndeterminateDrawable");
        mMessage = getArguments().getString("mMessage");
        ProgressDialog mProgressDialog = new ProgressDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
        if(mIndeterminateDrawable > 0){
            mProgressDialog.setIndeterminateDrawable(getActivity().getResources().getDrawable(mIndeterminateDrawable));
        }
        if(mMessage != null){
            mProgressDialog.setMessage(mMessage);
        }
        return mProgressDialog;
    }

    public void setMessage(String msg){
        if(msg != null)
            setMessage(msg);
    }
}
