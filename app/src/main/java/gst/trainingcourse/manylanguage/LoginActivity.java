package gst.trainingcourse.manylanguage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import gst.trainingcourse.manylanguage.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("loginFragment");
        if (loginFragment != null) {

        } else {
            addFragmentContain();
        }
    }

    private void addFragmentContain() {
        mLoginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayoutContain, mLoginFragment, "loginFragment")
                .addToBackStack("back")
                .commit();
    }

    @Override
    public void onBackPressed() {
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentByTag("loginFragment");
        if (loginFragment != null) {
            getSupportFragmentManager().popBackStack();
        }
        super.onBackPressed();
    }
}
