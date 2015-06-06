package cn.easydone.photolistdemo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by liang on 15/6/6.
 */
@SuppressWarnings("unused")
public class CustomProgressDialog extends Dialog {
    private Context context;
    private static CustomProgressDialog mDialog = null;

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static CustomProgressDialog createDialog(Context context, String msg) {
        mDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
        mDialog.setContentView(R.layout.custom_progress_dialog);
        mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);

        TextView tvMsg = (TextView) mDialog.findViewById(R.id.id_tv_loadingmsg);
        if (tvMsg != null) {
            tvMsg.setText(msg);
        }
        return mDialog;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (mDialog == null) {
            return;
        }

        ImageView indicatior = (ImageView) mDialog.findViewById(R.id.loadingImageView);
        AnimationDrawable animDrawable = (AnimationDrawable) indicatior.getBackground();
        animDrawable.start();
    }
}
