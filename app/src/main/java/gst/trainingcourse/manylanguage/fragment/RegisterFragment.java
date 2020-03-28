package gst.trainingcourse.manylanguage.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.lib_Helper.LocaleHelper;

public class RegisterFragment extends Fragment {

    private Button mButtonRegister, mButtonBack, mDialogButtonOK;
    private EditText mEditTextName, mEditTextPass, mEditTextConfirmPass;
    private String mName, mPass, mConfirmPass;
    private Dialog mDialogRegister;
    private TextView mDialogTxtUser, mDialogTxtPass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setLanguage();
        initView(view);
        initAction();
    }

    private void setLanguage() {
        LoginFragment loginFragment = (LoginFragment) getFragmentManager().findFragmentByTag("loginFragment");
        if (loginFragment != null) {
            if (loginFragment.passLanguage() == "vi") {
                LocaleHelper.setLocale(getContext(), "vi");
            } else if (loginFragment.passLanguage() == "en") {
                LocaleHelper.setLocale(getContext(), "en");
            }
        }
    }

    private void popupDialog() {
        mDialogRegister = new Dialog(getContext());
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
                getActivity().finish();
            }
        });

        mDialogRegister.show();
    }

    private void initAction() {
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName = mEditTextName.getText().toString();
                mPass = mEditTextPass.getText().toString();
                mConfirmPass = mEditTextConfirmPass.getText().toString();

                if (mName.matches("") || mPass.matches("") || mConfirmPass.matches("")) {
                    Toast.makeText(getContext(), getString(R.string.txt_check_null), Toast.LENGTH_SHORT).show();
                } else {
                    if (mPass.equals(mConfirmPass)) {
                        popupDialog();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.txt_wrong_password), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void initView(View view) {
        mEditTextName = view.findViewById(R.id.editTextName);
        mEditTextPass = view.findViewById(R.id.editTextPass);
        mEditTextConfirmPass = view.findViewById(R.id.editTextConfirmPass);
        mButtonRegister = view.findViewById(R.id.btnRegister);
        mButtonBack = view.findViewById(R.id.btnBack);
    }
}
