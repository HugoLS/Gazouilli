package fr.enssat.gazouilli.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import java.util.List;

import fr.enssat.gazouilli.adapter.CustomTweetTimelineListAdapter;
import fr.enssat.gazouilli.R;
import fr.enssat.gazouilli.gcm.GcmRegistrationAsyncTask;

public class GazListActivity extends ListActivity {

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

        listView = getListView();

        Log.d("MYAPP",listView.toString());


    }

    @Override
    protected void onResume() {
        super.onResume();

        TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
        SearchService service = twitterApiClient.getSearchService();
        service.tweets("enssat", null, null, null, null, null, null, null, null, null, new Callback<Search>() {
            @Override
            public void success(Result<Search> result) {
                final List<Tweet> tweets = result.data.tweets;
                /*for (Tweet tweet : tweets) {
                    Log.d("MYAPP", tweet.text);
                }*/

                final FixedTweetTimeline timeline = new FixedTweetTimeline.Builder()
                        .setTweets(tweets)
                        .build();

                /*final TweetTimelineListAdapter adapter = new CustomTweetTimelineListAdapter.Builder(getApplicationContext())
                        .setTimeline(timeline)
                        .build();*/

                final TweetTimelineListAdapter adapter = new CustomTweetTimelineListAdapter(getApplicationContext(), timeline);

                //adapter.getTweets().addAll(tweets);

                listView.setAdapter(adapter);
                //setListAdapter(adapter);
                //adapter.notifyDataSetChanged();

                Log.d("MYAPP", "all items enabled : " + adapter.areAllItemsEnabled());

                Log.d("MYAPP", "adapter length : " + adapter.getCount());

                Log.d("MYAPP", "zbra : " + listView.getAdapter().getCount());

                // Ne fonctionne pas mais mérite d'être poussé
                /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("MYAPP","click on tweet");
                    }

                });*/


            }

            @Override
            public void failure(TwitterException e) {

            }
        });

        GcmRegistrationAsyncTask tsk = new GcmRegistrationAsyncTask(this);
        tsk.execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //TextView t = (TextView) v.findViewById(R.id.tweetTitle);
        //t.setText("Tweet Clicked");

        Log.d("MYAPP","click on tweet");
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
