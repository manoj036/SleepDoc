package com.example.manojkumar.practiceui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SignupActivity extends Activity {

    private EditText inputEmail,inputPassword;
    private Button btnSignIn,btnSignUp,btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            startActivity(new Intent(getApplicationContext(),SleepSummaryActivity.class));
            this.finish();
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                    new AuthUI.IdpConfig.TwitterBuilder().build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setTheme(R.style.FirebaseLoginTheme)
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                startActivity(new Intent(getApplicationContext(),SleepSummaryActivity.class));
                this.finish();
            }  // Sign in failed, check response for error code // ...
        }
    }
}