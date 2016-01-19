package fr.enssat.gazouilli.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gilles.myapplication.backend.messaging.Messaging;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.enssat.gazouilli.R;
import fr.enssat.gazouilli.adapter.CommentListAdapter;
import fr.enssat.gazouilli.gcm.GcmSendAsyncTask;
import fr.enssat.gazouilli.model.Comment;
import fr.enssat.gazouilli.model.CommentBDD;

public class GazActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MYAPP", "onCreate GazActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView comListView = (ListView) findViewById(R.id.comList);

        final long tweetid = getIntent().getLongExtra("tweetid",510908133917487104L);

// get data from the table by the ListAdapter
        CommentBDD bdd = new CommentBDD(this);
        bdd.open();


        List<Comment> coms = bdd.getCommentsWithTweetID(""+tweetid);
        Log.d("MYAPP",""+(coms != null));
        bdd.close();
        //coms.add(new Comment("x","toto","Zbra"));
        //coms.add(new Comment("y","tata","Pamplemousse"));
        CommentListAdapter comAdapter = new CommentListAdapter(this, R.layout.comment_row, coms);
        //comAdapter.

        comListView.setAdapter(comAdapter);



        final LinearLayout myLayout
                = (LinearLayout) findViewById(R.id.tweet_layout);



        TweetUtils.loadTweet(tweetid, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                myLayout.addView(new TweetView(GazActivity.this, result.data));
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
                String[] params = new String[3];
                params[0] = ""+tweetid;
                params[1] = editText.getText().toString();
                params[2] = "toto";

                CommentBDD bdd = new CommentBDD(v.getContext());
                bdd.open();
                bdd.insertComment(new Comment(params[0],params[2],params[1]));
                bdd.close();
                GcmSendAsyncTask task = new GcmSendAsyncTask(v.getContext());
                task.execute(params);

            }
        });

    }

}
