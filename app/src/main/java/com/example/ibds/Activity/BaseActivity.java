package com.example.ibds.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.ibds.AppConstants;
import com.example.ibds.BitmapUtilsLatLang;
import com.example.ibds.CustomDialog;
import com.example.ibds.DatabaseHelper;
import com.example.ibds.FileUtils;
import com.example.ibds.Preference;
import com.example.ibds.R;
import com.example.ibds.Do.TaskDo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class BaseActivity extends FragmentActivity {
    public LayoutInflater inflater;
    public LinearLayout llBody, llheader, llheader2;
    public CustomDialog customDialog;
    public Preference preference;
    DatabaseHelper databaseHelper;
    public ImageView imgAdd;
    public TextView txtCancel, txtSave;
    private String camera_imagepath = "";
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public static final String ALLOW_KEY = "ALLOWED";
    public static final String CAMERA_PREF = "camera_pref";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);
        preference = new Preference(getApplicationContext());
        inflater = this.getLayoutInflater();
        databaseHelper = new DatabaseHelper(getApplicationContext());
        initilization();
        initialize();

    }

    private void initilization() {
        llBody = findViewById(R.id.llBody);
        llheader = findViewById(R.id.llheader);
        imgAdd = findViewById(R.id.imgAdd);
        llheader2 = findViewById(R.id.llheader2);
        txtCancel = findViewById(R.id.txtCancel);
        txtSave = findViewById(R.id.txtSave);

    }

    public abstract void initialize();

    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from) {
        runOnUiThread(new RunshowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, true));
    }

    public void showCustomDialog(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, TaskDo task) {
        runOnUiThread(new RunshowCustomDialogs(context, strTitle, strMessage, firstBtnName, secondBtnName, from, true, task));
    }

    class RunshowCustomDialogs implements Runnable {
        private String strTitle;// Title of the dialog
        private String strMessage;// Message to be shown in dialog
        private String firstBtnName;
        private String secondBtnName;
        private String from;
        private String params;
        private Object paramateres;
        private boolean isCancelable = false;
        private TaskDo task;
        private Context mcontext;

        public RunshowCustomDialogs(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable) {
            this.strTitle = strTitle;
            this.strMessage = strMessage;
            this.firstBtnName = firstBtnName;
            this.secondBtnName = secondBtnName;
            this.isCancelable = isCancelable;
            if (from != null)
                this.from = from;
            else
                this.from = "";
        }

        public RunshowCustomDialogs(Context context, String strTitle, String strMessage, String firstBtnName, String secondBtnName, String from, boolean isCancelable, TaskDo task) {
            this.strTitle = strTitle;
            this.strMessage = strMessage;
            this.firstBtnName = firstBtnName;
            this.secondBtnName = secondBtnName;
            this.isCancelable = isCancelable;
            this.task = task;
            this.mcontext = context;
            if (from != null)
                this.from = from;
            else
                this.from = "";
        }

        @Override
        public void run() {
            if (customDialog != null && customDialog.isShowing())
                customDialog.dismiss();
            View view = inflater.inflate(R.layout.custom_common_popup, null);

            customDialog = new CustomDialog(BaseActivity.this, view, preference
                    .getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 60,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            customDialog.setCancelable(isCancelable);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitlePopup);
            TextView tvMessage = (TextView) view.findViewById(R.id.tvMessagePopup);
            Button btnYes = (Button) view.findViewById(R.id.btnYesPopup);
            Button btnNo = (Button) view.findViewById(R.id.btnNoPopup);


            tvTitle.setText("" + strTitle);
            tvMessage.setText("" + strMessage);
            btnYes.setText("" + firstBtnName);
            if (secondBtnName.equalsIgnoreCase("")) {
                btnNo.setVisibility(View.GONE);
            } else {
                btnNo.setText("" + secondBtnName);
            }
            btnYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                    if (from.equalsIgnoreCase("Completion")) {
                        databaseHelper.UpdateTask(task);
                        showCustomeToast(getResources().getString(R.string.congratulations_for_completed_task));
                    }
                    if (from.equalsIgnoreCase("signup")) {
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (from.equalsIgnoreCase("logout")) {
                        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            });

            btnNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialog.dismiss();
                }
            });

            try {
                if (!customDialog.isShowing())
                    customDialog.showCustomDialog();
            } catch (Exception e) {
            }
        }
    }

    public void showCustomeToast(String msg) {
        View layout = inflater.inflate(R.layout.custom_toast, null);
        TextView txtToastMsg = layout.findViewById(R.id.txtToastMsg);
        ImageView imgCancel = layout.findViewById(R.id.imgCancel);
        txtToastMsg.setText(msg);

        final Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.setGravity(Gravity.TOP, 0, 250);
        toast.show();
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toast.cancel();
            }
        });
    }


    public boolean isDeviceSupportCamera() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
            return true;
        else
            return false;
    }

    public void CamaraSetting() {
        if (preference.getbooleanFromPreference(ALLOW_KEY, false)) {

            showSettingsAlert();

        } else if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                showAlert();
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ActivityCompat.requestPermissions(BaseActivity.this,
                                new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);

                    }
                });
        alertDialog.show();
    }

    private void showSettingsAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("App needs to access the Camera.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "DONT ALLOW",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //finish();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "SETTINGS",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startInstalledAppDetailsActivity(BaseActivity.this);

                    }
                });
        alertDialog.show();
    }

    public static void startInstalledAppDetailsActivity(final Activity context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                for (int i = 0, len = permissions.length; i < len; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        boolean showRationale =
                                ActivityCompat.shouldShowRequestPermissionRationale
                                        (this, permission);
                        if (showRationale) {
                            showAlert();
                        } else if (!showRationale) {
                            preference.saveBooleanInPreference(ALLOW_KEY, true);
                        }
                    }
                }
            }
        }
    }
}
