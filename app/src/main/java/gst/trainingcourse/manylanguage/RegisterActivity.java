package gst.trainingcourse.manylanguage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import gst.trainingcourse.manylanguage.database.MyDatabase;
import gst.trainingcourse.manylanguage.fragment.LoginFragment;
import gst.trainingcourse.manylanguage.lib_Helper.LocaleHelper;
import gst.trainingcourse.manylanguage.model.Account;

public class RegisterActivity extends AppCompatActivity {

    private Button mButtonRegister, mButtonBack, mDialogButtonOK;
    private EditText mEditTextName, mEditTextPass, mEditTextConfirmPass;
    private String mName, mPass, mConfirmPass;
    private Dialog mDialogRegister;
    private TextView mDialogTxtUser, mDialogTxtPass;
    private Account mAccount;
    private List<Account> mAccountList;
    private MyDatabase mMyDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setLanguage();
        initView();
        initAction();
    }

    private void setLanguage() {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("loginFragment");
        if (loginFragment != null) {
            if (loginFragment.passLanguage() == "img_vi") {
                LocaleHelper.setLocale(getApplicationContext(), "img_vi");
            } else if (loginFragment.passLanguage() == "img_en") {
                LocaleHelper.setLocale(getApplicationContext(), "img_en");
            }
        }
    }

    private void popupDialog() {
        mDialogRegister = new Dialog(this);
        mDialogRegister.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialogRegister.setContentView(R.layout.dialog_register);
        mDialogRegister.setCanceledOnTouchOutside(false);

        mDialogTxtUser = mDialogRegister.findViewById(R.id.txtUsername);
        mDialogTxtPass = mDialogRegister.findViewById(R.id.txtPassword);
        mDialogButtonOK = mDialogRegister.findViewById(R.id.btnOK);

        mDialogTxtUser.setText(mName);
        mDialogTxtPass.setText(mPass);

        mDialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("username", mName);
                intent.putExtra("password", mPass);
                setResult(Activity.RESULT_OK, intent);
                mDialogRegister.cancel();
                onBackPressed();
            }
        });

        mDialogRegister.show();
    }

    private void initAction() {
        mMyDatabase = new MyDatabase(RegisterActivity.this);
        mAccountList = mMyDatabase.getAllAccount();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName = mEditTextName.getText().toString();
                mPass = mEditTextPass.getText().toString();
                mConfirmPass = mEditTextConfirmPass.getText().toString();

                if (mName.matches("") || mPass.matches("") || mConfirmPass.matches("")) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_check_null), Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = mMyDatabase.checkAccountRegister(mName);
                    if (check) {
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_account_already_exists), Toast.LENGTH_SHORT).show();
                    } else {
                        if (mPass.equals(mConfirmPass)) {
                            mAccount = new Account(mName, mPass);
                            mMyDatabase.addAccount(mAccount);

                            popupDialog();
                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.toast_wrong_password), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mMyDatabase.deleteDatabase();
                onBackPressed();
            }
        });
    }

    private void initView() {
        mEditTextName = findViewById(R.id.editTextName);
        mEditTextPass = findViewById(R.id.editTextPass);
        mEditTextConfirmPass = findViewById(R.id.editTextConfirmPass);
        mButtonRegister = findViewById(R.id.btnRegister);
        mButtonBack = findViewById(R.id.btnBack);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
