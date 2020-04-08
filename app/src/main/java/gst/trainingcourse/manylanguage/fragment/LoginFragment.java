package gst.trainingcourse.manylanguage.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import gst.trainingcourse.manylanguage.ControlAccount;
import gst.trainingcourse.manylanguage.MainActivity;
import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.RegisterActivity;
import gst.trainingcourse.manylanguage.database.MyDatabase;
import gst.trainingcourse.manylanguage.lib_Helper.LocaleHelper;
import gst.trainingcourse.manylanguage.model.Account;

public class LoginFragment extends Fragment {

    private Button mButtonLogin;
    private CheckBox mCheckBox;
    private EditText mEditTextName, mEditTextPass, mEditTextPhone;
    private String mName, mPass, mPhone;
    private RelativeLayout mRlChooseLanguage;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private TextView mTxtRegister, mTxtAppName;
    private String mSetLanguage = "img_vi";
    private int REQUEST_USER_PASS = 999;
    private Account mAccount;
    private MyDatabase mMyDatabase;
//    private List<Account> mAccountList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSharedPreferences = getActivity().getSharedPreferences("saveInfo", getContext().MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        initView(view);
        initAction();
    }

    private void setLanguage() {
        PopupMenu popupMenu = new PopupMenu(getContext(), mRlChooseLanguage);
        popupMenu.getMenuInflater().inflate(R.menu.menu_test, popupMenu.getMenu());

        //Trick show icon item menu
        Object menuHelper;
        Class[] argTypes;
        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popupMenu);
            argTypes = new Class[]{boolean.class};
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception ignored) {
        }
        ///////////////////////////

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.itemMenuVi:
                        changeLanguage("img_vi");
                        mSetLanguage = "img_vi";
                        break;
                    case R.id.itemMenuEn:
                        changeLanguage("img_en");
                        mSetLanguage = "img_en";
                        break;
                }

                return false;
            }
        });
        popupMenu.show();
    }

    public String passLanguage() {
        return mSetLanguage;
    }

    private void initAction() {
        mMyDatabase = new MyDatabase(getContext());
//        mAccountList = mMyDatabase.getAllAccount();

        mRlChooseLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLanguage();
            }
        });

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName = mEditTextName.getText().toString().trim();
                mPass = mEditTextPass.getText().toString().trim();
                if (mCheckBox.isChecked()) {
                    mEditor.putString("username", mName);
                    mEditor.putString("password", mPass);
                    mEditor.commit();
                }
                if (mName.matches("") || mPass.matches("")) {
                    Toast.makeText(getContext(), getString(R.string.toast_not_enough_information), Toast.LENGTH_SHORT).show();
                } else {
                    if (mName.matches("admin") && mPass.matches("123")) {
                        Intent intent = new Intent(getActivity(), ControlAccount.class);
                        startActivity(intent);
                    } else {
                        boolean check = mMyDatabase.checkAccountLogin(mName, mPass);
                        if (check) {
                            int id = mMyDatabase.getId(mName);
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("idAccount", id);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), getString(R.string.toast_wrong_account_infomation), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
                if (check) {
                    mEditor.putString("username", mName);
                    mEditor.putString("password", mPass);
                } else {
                    mEditor.clear();
                }
                mEditor.putBoolean("checkBoxInfo", check);
                mEditor.commit();
            }
        });

        mTxtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair[] pairs = new Pair[4];
                pairs[0] = new Pair(mTxtAppName, getResources().getString(R.string.app_name));
                pairs[1] = new Pair(mEditTextName, getResources().getString(R.string.txt_username));
                pairs[2] = new Pair(mEditTextPass, getResources().getString(R.string.txt_password));
                pairs[3] = new Pair(mEditTextPhone, getResources().getString(R.string.txt_confirm_password));

                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pairs);
                startActivityForResult(intent, REQUEST_USER_PASS, activityOptions.toBundle());
            }
        });
    }

    private void changeLanguage(String language) {
        LoginFragment loginFragment = (LoginFragment) getFragmentManager().findFragmentByTag("loginFragment");
        if (loginFragment != null) {
            if (language == "img_vi") {
                LocaleHelper.setLocale(getContext(), "img_vi");
            } else if (language == "img_en") {
                LocaleHelper.setLocale(getContext(), "img_en");
            }
            getFragmentManager().beginTransaction()
                    .detach(loginFragment)
                    .attach(loginFragment)
                    .commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_USER_PASS && resultCode == getActivity().RESULT_OK && data != null) {
            mName = data.getStringExtra("username");
            mPass = data.getStringExtra("password");
            mEditTextName.setText(mName);
            mEditTextPass.setText(mPass);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", mName);
        outState.putString("pass", mPass);
        outState.putString("phone", mPhone);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mName = savedInstanceState.getString("name");
            mPass = savedInstanceState.getString("pass");
            mPhone = savedInstanceState.getString("phone");
        }
    }

    private void initView(View view) {
        mEditTextName = view.findViewById(R.id.editTextName);
        mEditTextPass = view.findViewById(R.id.editTextPass);
        mEditTextPhone = view.findViewById(R.id.editTextPhone);
        mButtonLogin = view.findViewById(R.id.btnLogin);
        mCheckBox = view.findViewById(R.id.checkBoxInfo);
        mRlChooseLanguage = view.findViewById(R.id.rlChooseLanguage);
        mTxtRegister = view.findViewById(R.id.txtRegister);
        mTxtAppName = view.findViewById(R.id.txtAppName);

        mCheckBox.setChecked(mSharedPreferences.getBoolean("checkBoxInfo", false));

        mEditTextName.setText(mSharedPreferences.getString("username", null));
        mEditTextPass.setText(mSharedPreferences.getString("password", null));
    }
}
