package gst.trainingcourse.manylanguage.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import gst.trainingcourse.manylanguage.LoginActivity;
import gst.trainingcourse.manylanguage.MainActivity;
import gst.trainingcourse.manylanguage.R;
import gst.trainingcourse.manylanguage.database.MyDatabase;
import gst.trainingcourse.manylanguage.model.Account;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private TextView mTxtUser, mTxtChangePass, mTxtLogout, mTxtBack;
    private EditText mEditTextEmail, mEditTextAddress, mEditTextPhone, mEditTextOldPass, mEditTextNewPass, mEditTextCofimNewPass;
    private LinearLayout mLLProfile, mLLChangePass;
    private Button mBtnUpload;
    private CircleImageView mImgProfle;
    private FloatingActionButton mBtnEdit, mBtnOk;
    private MyDatabase mMyDatabase;
    private Account mAccount;
    private String mPass, mEmail, mAddress, mPhone, mNewPass, mCofirmNewPass;
    private int id;
    private static final int REQUEST_CODE_UPLOAD = 111;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        disableEditTextLLProfile();
        initData();
        initAction();
    }

    private void initAction() {
        mTxtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        mTxtChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLLProfile.setVisibility(View.INVISIBLE);
                mLLChangePass.setVisibility(View.VISIBLE);
                mBtnEdit.setVisibility(View.INVISIBLE);
                mBtnOk.setVisibility(View.VISIBLE);
                mTxtChangePass.setVisibility(View.INVISIBLE);
                mTxtBack.setVisibility(View.VISIBLE);
            }
        });

        mTxtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTxtChangePass.setVisibility(View.VISIBLE);
                mTxtBack.setVisibility(View.INVISIBLE);
                mBtnEdit.setVisibility(View.VISIBLE);
                mBtnOk.setVisibility(View.INVISIBLE);
                mLLProfile.setVisibility(View.VISIBLE);
                mLLChangePass.setVisibility(View.INVISIBLE);
                disableEditTextLLProfile();
            }
        });

        mBtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableEditTextLLProfile();
                mBtnOk.setVisibility(View.VISIBLE);
            }
        });

        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mLLProfile.getVisibility() == View.VISIBLE) {
                    mEmail = mEditTextEmail.getText().toString();
                    mAddress = mEditTextAddress.getText().toString();
                    mPhone = mEditTextPhone.getText().toString();

                    if (mEmail.matches("") || mAddress.matches("") || mPhone.matches("")) {
                        Toast.makeText(getContext(), "Bạn chưa cập nhật hết thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    }

                    mAccount = new Account();
                    mAccount.setId(id);
                    mAccount.setEmail(mEmail);
                    mAccount.setAddress(mAddress);
                    mAccount.setTelephone(mPhone);
                    mMyDatabase.updateAccountInfo(mAccount);

                    mBtnOk.setVisibility(View.INVISIBLE);
                    disableEditTextLLProfile();
                }

                if (mLLChangePass.getVisibility() == View.VISIBLE) {
                    mPass = mEditTextOldPass.getText().toString();
                    mNewPass = mEditTextNewPass.getText().toString();
                    mCofirmNewPass = mEditTextCofimNewPass.getText().toString();

                    if (mPass.matches("") || mNewPass.matches("") || mCofirmNewPass.matches("")) {
                        Toast.makeText(getContext(), "Bạn chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean check = mMyDatabase.checkAccountLogin(mTxtUser.getText().toString(), mPass);
                        if (check) {
                            if (mNewPass.equals(mCofirmNewPass)) {
                                mLLProfile.setVisibility(View.VISIBLE);
                                mLLChangePass.setVisibility(View.INVISIBLE);

                                mAccount = new Account();
                                mAccount.setId(id);
                                mAccount.setPassword(mNewPass);
                                mMyDatabase.updateAccountPass(mAccount);

                                mBtnOk.setVisibility(View.INVISIBLE);
                                mBtnEdit.setVisibility(View.VISIBLE);
                                mTxtBack.setVisibility(View.INVISIBLE);
                                mTxtChangePass.setVisibility(View.VISIBLE);
                                disableEditTextLLProfile();

                                Toast.makeText(getContext(), "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Nhập sai mật khẩu mới", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Nhập sai mật khẩu cũ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                //loại là hình ảnh
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_UPLOAD);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_UPLOAD && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                mImgProfle.setImageBitmap(bitmap);

                //chuyển bitmap -> byte[]
                BitmapDrawable bitmapDrawable = (BitmapDrawable) mImgProfle.getDrawable();
                Bitmap newBitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                //ưu tiên PNG -> ảnh đẹp
                //chất lượng 100 (mặc định của ảnh ban đầu), nếu giảm thì sẽ nét hơn
                newBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();
                /////////////////////////
                mAccount = new Account();
                mAccount.setId(id);
                mAccount.setImage(image);
                mMyDatabase.updateAccountImage(mAccount);

                Toast.makeText(getContext(), "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initData() {
        Intent intent = getActivity().getIntent();
        id = intent.getIntExtra("idAccount", 1);

        mMyDatabase = new MyDatabase(getContext());
        mAccount = mMyDatabase.getAccount(id);

        mTxtUser.setText(mAccount.getUsername());
        mEditTextEmail.setText(mAccount.getEmail());
        mEditTextAddress.setText(mAccount.getAddress());
        mEditTextPhone.setText(mAccount.getTelephone());

        byte[] hinhanh = mAccount.getImage();
        //chuyển byte[] -> image
        if (hinhanh != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(hinhanh, 0, hinhanh.length);
            mImgProfle.setImageBitmap(bitmap);
        }
    }

    private void initView(View view) {
        mTxtUser = view.findViewById(R.id.txtUsername);
        mTxtChangePass = view.findViewById(R.id.txtChangePassword);
        mTxtLogout = view.findViewById(R.id.txtLogout);
        mTxtBack = view.findViewById(R.id.txtBack);

        mEditTextEmail = view.findViewById(R.id.editTextEmail);
        mEditTextAddress = view.findViewById(R.id.editTextAdress);
        mEditTextPhone = view.findViewById(R.id.editTextPhone);
        mEditTextOldPass = view.findViewById(R.id.editTextOldPass);
        mEditTextNewPass = view.findViewById(R.id.editTextNewPass);
        mEditTextCofimNewPass = view.findViewById(R.id.editTextCofimNewPass);

        mBtnEdit = view.findViewById(R.id.btnEdit);
        mBtnOk = view.findViewById(R.id.btnOK);
        mBtnUpload = view.findViewById(R.id.btnUpLoadImg);

        mImgProfle = view.findViewById(R.id.imgUser);

        mLLProfile = view.findViewById(R.id.llProfile);
        mLLChangePass = view.findViewById(R.id.llChangePass);
    }

    private void enableEditTextLLProfile() {
        mEditTextEmail.setEnabled(true);
        mEditTextAddress.setEnabled(true);
        mEditTextPhone.setEnabled(true);
    }

    private void disableEditTextLLProfile() {
        mEditTextEmail.setEnabled(false);
        mEditTextAddress.setEnabled(false);
        mEditTextPhone.setEnabled(false);
    }
}
