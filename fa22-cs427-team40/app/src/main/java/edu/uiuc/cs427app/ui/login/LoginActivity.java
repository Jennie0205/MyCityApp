package edu.uiuc.cs427app.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import edu.uiuc.cs427app.MainActivity;
import edu.uiuc.cs427app.SettingsActivity;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.UserModel;
import edu.uiuc.cs427app.UserModelList;
import edu.uiuc.cs427app.databinding.ActivityLoginBinding;

/**
 * LoginActivity handles user login authentication using the GoogleSignIn library.
 */
public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";
    private UserModelList userList;

    /**
     * This function creates the GoogleSignInClient and also sets the login button on click listener.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        if(i.getSerializableExtra("UserList") != null) {
            // UserList does not exist already so create it:
            userList = new UserModelList();
        } else {
            userList = (UserModelList) i.getSerializableExtra("UserList");
        }
        
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.setTitle("Team 40");
        final Button loginButton = binding.login;
        loginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Callback for login button on click listener. Calls signIn() when clicked.
             * @param v is the current view.
             */
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.login:
                        signIn();
                        break;
                }
            }

            /**
             * Gets the signInIntent and starts the Google Sign In activity.
             */
            private void signIn() {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    /**
     * Retrieve data from Google data center, and calls handleSignInResult.
     * @param requestCode Code for retrieving data.
     * @param resultCode Code for retrieving status.
     * @param data Intent to store current activity's info.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            //Task<GoogleSignInAccount> task = GoogleSignIn.getLastSignedInAccount(this);
            handleSignInResult(task);
        }
    }

    /**
     * Check signed in user and make call to update the UI.
     * @param completedTask
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {

            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Log.e(TAG,"API Exception");
            updateUI(null);
        }
    }

    /**
     * Jump to the next activity based on status of user. For first-time user, jump to settings.
     * Otherwise, jumps to the main activity.
     * @param account Google user account.
     */
    private void updateUI(GoogleSignInAccount account) {
         // Jump activity to main activity
        // Navigate to settings page if Ui theme does not exist in database
        // Or navigate to main activity if UI theme does exist (pass uitheme in as intent)
        String email = account.getEmail();
        Intent i;
        if (!userList.userExist(email)) {
            UserModel user = new UserModel(account.getEmail());
            userList.addUser(user);
            i = new Intent(getApplicationContext(), SettingsActivity.class);

        } else {
            i = new Intent(getApplicationContext(), MainActivity.class);
        }

        i.putExtra("email", account.getEmail());
        String username = account.getEmail().split("@")[0];
        this.setTitle("Team 40-" + username);
        i.putExtra("username", username);
        i.putExtra("UserList", userList);
        startActivity(i);
    }
}