package com.example.ibds.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.ibds.AppConstants;
import com.example.ibds.BitmapUtilsLatLang;
import com.example.ibds.CalendarUtils;
import com.example.ibds.CustomDialog;
import com.example.ibds.DatabaseHelper;
import com.example.ibds.Do.CommentDo;
import com.example.ibds.Do.LeadDo;
import com.example.ibds.FileUtils;
import com.example.ibds.Preference;
import com.example.ibds.R;

import java.io.File;

public class LeadCreaterActivity extends BaseActivity {
    LinearLayout llLeadCreater;
    private String titles[] = {"Mr.", "Mrs.", "Miss.", "Dr."};
    private ArrayAdapter<String> titleAdaptor;
    private Spinner spinTitle;
    private EditText edtFname, edtMname, edtLname, edtSuffix, edtEmail, edtPhone, edtCompany, edtComment, edtTitle;
    private String strFname = "", strMname = "", strLnames = "", strSuffix = "", strEmail = "", strPhone = "", strCompany = "", strComment = "", strSpinTitle = "", strTitle = "";
    DatabaseHelper databaseHelper;
    private ImageView imgCapture;
    private Button btnCapture;
    private String camera_imagepath = "";
    public CustomDialog customDialog;

    @Override
    public void initialize() {
        llLeadCreater = (LinearLayout) inflater.inflate(R.layout.lead_creater, null);
        llBody.addView(llLeadCreater, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        llheader.setVisibility(View.GONE);
        llheader2.setVisibility(View.VISIBLE);

        spinTitle = llLeadCreater.findViewById(R.id.spinTitle);
        edtFname = llLeadCreater.findViewById(R.id.edtFname);
        edtMname = llLeadCreater.findViewById(R.id.edtMname);
        edtLname = llLeadCreater.findViewById(R.id.edtLname);
        edtSuffix = llLeadCreater.findViewById(R.id.edtSuffix);
        edtEmail = llLeadCreater.findViewById(R.id.edtEmail);
        edtPhone = llLeadCreater.findViewById(R.id.edtPhone);
        edtCompany = llLeadCreater.findViewById(R.id.edtCompany);
        edtComment = llLeadCreater.findViewById(R.id.edtComment);
        edtTitle = llLeadCreater.findViewById(R.id.edtTitle);
        imgCapture = llLeadCreater.findViewById(R.id.imgCapture);
        btnCapture = llLeadCreater.findViewById(R.id.btnCapture);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        titleAdaptor = new ArrayAdapter<>(LeadCreaterActivity.this, android.R.layout.simple_spinner_dropdown_item, titles);
        titleAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinTitle.setAdapter(titleAdaptor);

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LeadCreaterActivity.this, MainActivity.class);
                intent.putExtra("viewpager_position", 2);
                startActivity(intent);
                finish();
            }
        });
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });
        imgCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(new RunshowCustomImageDialogs(((BitmapDrawable)imgCapture.getDrawable()).getBitmap()));
            }
        });
        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strFname = edtFname.getText().toString();
                strMname = edtMname.getText().toString();
                strLnames = edtLname.getText().toString();
                strSuffix = edtSuffix.getText().toString();
                strEmail = edtEmail.getText().toString();
                strPhone = edtPhone.getText().toString();
                strCompany = edtCompany.getText().toString();
                strComment = edtComment.getText().toString();
                strTitle = edtTitle.getText().toString();
                strSpinTitle = spinTitle.getSelectedItem().toString();

                if (strFname.equalsIgnoreCase("") || strLnames.equalsIgnoreCase("") || strMname.equalsIgnoreCase("") || strSuffix.equalsIgnoreCase("")) {
                    showCustomDialog(LeadCreaterActivity.this, getString(R.string.warning), "Name is empty", getString(R.string.OK), "", "");
                } else if (strCompany.equalsIgnoreCase("")) {
                    showCustomDialog(LeadCreaterActivity.this, getString(R.string.warning), "Company is empty", getString(R.string.OK), "", "");
                } else {
                    LeadDo lead = new LeadDo(strSpinTitle + " " + strFname + " " + strMname + " " + strLnames + " " + strSuffix, strEmail, strCompany, strPhone, strTitle, strComment, CalendarUtils.getCurrentDateTime());
                    if (databaseHelper.insertLead(lead)) {
                        if (!strComment.equalsIgnoreCase("")) {
                            CommentDo comment = new CommentDo(strSpinTitle + " " + strFname + " " + strMname + " " + strLnames + " " + strSuffix, strComment);
                            databaseHelper.insertComment(comment);
                        }
                        Intent intent = new Intent(LeadCreaterActivity.this, MainActivity.class);
                        intent.putExtra("viewpager_position", 4);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    public void captureImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            CamaraSetting();
        }else {
            if (isDeviceSupportCamera()) {
                File file = FileUtils.getOutputImageFile("Lead");
                if (file != null) {
                    camera_imagepath = file.getAbsolutePath();
//                    preference.saveStringInPreference(preference.CAPTURE_IMAGE_PATH,camera_imagepath);
//                    preference.commitPreference();
                    Uri fileUri = Uri.fromFile(file);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    intent.putExtra("fileName", file.getName());
                    intent.putExtra("filePath", file.getAbsolutePath());
                    startActivityForResult(intent, AppConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                }
            } else {
                showCustomeToast("Sorry Device not supported to camera.");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstants.CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_CANCELED:
                    Log.i("Camera", "User cancelled");
                    break;

                case RESULT_OK:
//                    camera_imagepath = preference.getStringFromPreference(Preference.CAPTURE_IMAGE_PATH, "");
                    if (!TextUtils.isEmpty(camera_imagepath)) {
                        File f = new File(camera_imagepath);
                        Bitmap bmp = BitmapUtilsLatLang.decodeSampledBitmapFromResource(f, 720, 1280);
                        imgCapture.setImageBitmap(bmp);
                    }
                    break;
            }
        }
    }

    class RunshowCustomImageDialogs implements Runnable {
        private boolean isCancelable = false;
        private Bitmap bitmap;

        public RunshowCustomImageDialogs(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            if (customDialog != null && customDialog.isShowing())
                customDialog.dismiss();
            View view = inflater.inflate(R.layout.custom_image_popup, null);

            customDialog = new CustomDialog(LeadCreaterActivity.this, view, preference
                    .getIntFromPreference(Preference.DEVICE_DISPLAY_WIDTH, 320) - 60,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
            customDialog.setCancelable(isCancelable);

            ImageView imgCapture = view.findViewById(R.id.imgCaptureImage);
            Button bntCross = view.findViewById(R.id.btnCross);
            imgCapture.setImageBitmap(bitmap);
            bntCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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
}
