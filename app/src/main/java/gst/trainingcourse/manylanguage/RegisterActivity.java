package gst.trainingcourse.manylanguage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import gst.trainingcourse.manylanguage.fragment.RegisterFragment;

public class RegisterActivity extends AppCompatActivity {

    private RegisterFragment mRegisterFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager().findFragmentByTag("registerFragment");
        if (registerFragment != null) {

        } else {
            addFragmentContain();
        }
    }

    private void addFragmentContain() {
        mRegisterFragment = new RegisterFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutContain, mRegisterFragment, "registerFragment")
                .addToBackStack("back")
                .commit();
    }

    @Override
    public void onBackPressed() {
        RegisterFragment registerFragment = (RegisterFragment) getSupportFragmentManager().findFragmentByTag("registerFragment");
        if (registerFragment != null) {
            getSupportFragmentManager().popBackStack();
        }
        super.onBackPressed();
    }
}
