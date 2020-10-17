package tk.pankajb.CustomWidgets;

import android.app.Activity;
import android.app.AlertDialog;

import tk.pankajb.R;

public class LoadingDialog {

    Activity context;
    AlertDialog dialog;

    public LoadingDialog(Activity context) {
        this.context = context;
    }

    public void startLoading() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(R.layout.cutom_loading);
        builder.setCancelable(false);
        dialog = builder.create();
        dialog.show();
    }

    public void stopLoading() {
        dialog.dismiss();
    }
}
