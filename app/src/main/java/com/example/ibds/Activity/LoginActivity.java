package com.example.ibds.Activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ibds.DatabaseHelper;
import com.example.ibds.Do.SignUpDo;
import com.example.ibds.Preference;
import com.example.ibds.R;

public class LoginActivity extends BaseActivity {
    private EditText et_email, et_password;
    private LinearLayout llLogin;
    private Button btnLogin;
    private CheckBox pwdCheckbox;
    private String strEmail = "", strPassword = "", emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private TextView tvSignUp;
    DatabaseHelper objSqliteDB = null;
    @Override
    public void initialize() {
        llLogin = (LinearLayout) inflater.inflate(R.layout.login, null);
        llBody.addView(llLogin, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        llheader.setVisibility(View.GONE);

        initialization();
        objSqliteDB = new DatabaseHelper(getApplicationContext());

        pwdCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail = et_email.getText().toString();
                strPassword = et_password.getText().toString();

                if (strEmail.equals("") || strPassword.equals("") || !strEmail.trim().matches(emailPattern)) {
                    validateLogin();
                } else {
                    if(objSqliteDB.isUserExist(strEmail,strPassword) && objSqliteDB.getUserDetails() != null) {

                        SignUpDo signUpDo = new SignUpDo();
                        signUpDo = objSqliteDB.getUserDetails();
                        preference.saveStringInPreference(Preference.USERNAME, signUpDo.getName());
                        preference.saveStringInPreference(Preference.EMAIL, signUpDo.getEmail());
                        preference.saveStringInPreference(Preference.PASSWORD, signUpDo.getPassword());
                        preference.commitPreference();

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        showCustomDialog(LoginActivity.this, getString(R.string.warning), "Entered Email & Password are wrong", getString(R.string.OK), "", "");
                    }
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initialization() {
        et_email = llLogin.findViewById(R.id.edit_email);
        et_password = llLogin.findViewById(R.id.edit_password);
        pwdCheckbox = llLogin.findViewById(R.id.pwdCheckbox);
        btnLogin = llLogin.findViewById(R.id.btnLogin);
        tvSignUp = llLogin.findViewById(R.id.tvSignUp);
    }

    private void validateLogin() {
        if (strEmail.equals("") && strPassword.equals("")) {
            showCustomDialog(LoginActivity.this, getString(R.string.warning), getString(R.string.enter_username_password), getString(R.string.OK), "", "");
            et_email.requestFocus();
        } else if (strEmail.equals("")) {
            showCustomDialog(LoginActivity.this, getString(R.string.warning), getString(R.string.enter_username), getString(R.string.OK), "", "");
            et_email.requestFocus();
        } else if (strPassword.equals("")) {
            showCustomDialog(LoginActivity.this, getString(R.string.warning), getString(R.string.enter_password), getString(R.string.OK), "", "");
            et_password.requestFocus();
        } else if (!strEmail.trim().matches(emailPattern)) {
            showCustomDialog(LoginActivity.this, getString(R.string.warning), "Mail address is not a valid one", getString(R.string.OK), "", "");
        }
    }

}
