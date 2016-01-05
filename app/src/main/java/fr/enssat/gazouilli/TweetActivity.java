package fr.enssat.gazouilli;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.Arrays;
import java.util.List;

public class TweetActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MYAPP", "onCreate TweetActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        final LinearLayout myLayout
                = (LinearLayout) findViewById(R.id.tweet_layout);

        long tweetid = getIntent().getLongExtra("tweetid",510908133917487104L);

        TweetUtils.loadTweet(tweetid, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                myLayout.addView(new TweetView(TweetActivity.this, result.data));
            }

            @Override
            public void failure(TwitterException e) {

            }
        });

        editText = (EditText)findViewById(R.id.comEditText);

        Button button = (Button)findViewById(R.id.comButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(v.getContext(),editText.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
