package fr.enssat.gazouilli;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class ConnectionActivity extends AppCompatActivity {

    TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TwitterAuthConfig authConfig =  new TwitterAuthConfig(getString(R.string.twitterKey), getString(R.string.twitterSecret));
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_connection);

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.d("MYAPP", "ZBRA TWITTER SUCCESS");
                Intent intent = new Intent( getApplicationContext(), GazListActivity.class);
                startActivity(intent);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure

                Log.d("MYAPP", exception.getMessage());
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
