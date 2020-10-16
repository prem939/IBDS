package com.example.ibds.Activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ibds.DatabaseHelper;
import com.example.ibds.Do.SignUpDo;
import com.example.ibds.R;

public class SignUpActivity extends BaseActivity {
    private LinearLayout llSignUp;
    private TextView tvLogin;
    private EditText et_Name, et_Password, et_CfPassword, et_Email;
    private Button btnSignUp;
    private String strEmail = "", strPassword = "", emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+", strName = "", strCfPassword = "";
    public static final String TABLE_NAME = "tblUsers";

    DatabaseHelper objSqliteDB = null;

    @Override
    public void initialize() {
        llSignUp = (LinearLayout) inflater.inflate(R.layout.signup, null);
        llBody.addView(llSignUp, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llheader.setVisibility(View.GONE);

        initilization();

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = et_Email.getText().toString();
                strName = et_Name.getText().toString();
                strPassword = et_Password.getText().toString();
                strCfPassword = et_CfPassword.getText().toString();
                if (strEmail.equals("") || strPassword.equals("") || strName.equals("") || strCfPassword.equals("")) {
                    showCustomDialog(SignUpActivity.this, getString(R.string.warning), "Enter all details", getString(R.string.OK), "", "");
                } else if (!strPassword.equalsIgnoreCase(strCfPassword)) {
                    showCustomDialog(SignUpActivity.this, getString(R.string.warning), "Both Password are not matched", getString(R.string.OK), "", "");
                } else if (!strEmail.trim().matches(emailPattern)) {
                    showCustomDialog(SignUpActivity.this, getString(R.string.warning), "Mail address is not a valid one", getString(R.string.OK), "", "");
                } else {
                    SignUpDo signUpDo = new SignUpDo(strName,strEmail,strPassword);
                    objSqliteDB = new DatabaseHelper(getApplicationContext());
                    if (objSqliteDB.InsertUserDetails(signUpDo)) {
                        showCustomDialog(SignUpActivity.this, getString(R.string.warning), "Account is created Successful", getString(R.string.OK), "", "signup");
                    }

                }
            }
        });
    }

    private void initilization() {
        tvLogin = llSignUp.findViewById(R.id.tvlogin);
        et_Name = llSignUp.findViewById(R.id.edit_Name);
        et_Password = llSignUp.findViewById(R.id.edit_password);
        et_CfPassword = llSignUp.findViewById(R.id.edit_CfPassword);
        et_Email = llSignUp.findViewById(R.id.edit_email);
        btnSignUp = llSignUp.findViewById(R.id.btnSignUp);

    }
}
