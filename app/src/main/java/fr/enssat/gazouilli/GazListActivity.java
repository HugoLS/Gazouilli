package fr.enssat.gazouilli;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.GuestCallback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetViewAdapter;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class GazListActivity extends Activity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //final TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.twitterKey), getString(R.string.twitterSecret));

        //Fabric.with(this, new Twitter(authConfig));


        TwitterSession session = Twitter.getSessionManager().getActiveSession();
        TwitterAuthToken authToken = session.getAuthToken();
        String token = authToken.token;
        String secret = authToken.secret;

        Log.d("TESTAUTH", token);
        Log.d("TESTAUTH", secret);

        setContentView(R.layout.activity_gaz_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_gaz_list);

        listView = (ListView) findViewById(R.id.mylist);

        Log.d("MYAPP",listView.toString());


    }

    @Override
    protected void onResume() {
        super.onResume();

        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
// Can also use Twitter directly: Twitter.getApiClient()
        StatusesService statusesService = twitterApiClient.getStatusesService();
        statusesService.userTimeline(null, "enssat", null, 20L, null, null, true, null, null, new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> listResult) {
                final List<Tweet> tweets = listResult.data;
                for (Tweet tweet : tweets) {
                    Log.d("MYAPP", tweet.text);
                }

                final FixedTweetTimeline timeline = new FixedTweetTimeline.Builder()
                        .setTweets(tweets)
                        .build();

                final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getApplicationContext())
                        .setTimeline(timeline)
                        .build();
                //adapter.getTweets().addAll(tweets);
                //
                listView.setAdapter(adapter);
                //setListAdapter(adapter);
                //adapter.notifyDataSetChanged();
                Log.d("MYAPP", "all items enabled : " + adapter.areAllItemsEnabled());


                Log.d("MYAPP", "adapter length : " + adapter.getCount());

                Log.d("MYAPP", "zbra : " + listView.getAdapter().getCount());
            }

            @Override
            public void failure(TwitterException e) {
                Log.e("Twitter", e.toString() + ", " + e.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gaz_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
