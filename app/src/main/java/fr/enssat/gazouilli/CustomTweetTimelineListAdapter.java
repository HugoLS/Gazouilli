package fr.enssat.gazouilli;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

/**
 * Created by Adrien-ENSSAT on 05/01/2016.
 */
class CustomTweetTimelineListAdapter extends TweetTimelineListAdapter {

    public CustomTweetTimelineListAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
        Log.d("MYAPP","CustomTweetTimelineListAdapter constructor");
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        //disable subviews to avoid links are clickable
        if(view instanceof ViewGroup){
            disableViewAndSubViews((ViewGroup) view);
        }

        //enable root view and attach custom listener
        view.setEnabled(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long tweetId = getItemId(position);
                //Toast.makeText(context, tweetId, Toast.LENGTH_SHORT).show();
                Log.d("MYAPP","click on tweet : " + tweetId);

                Intent intent = new Intent( v.getContext(), TweetActivity.class);
                Log.d("MYAPP", "intent created");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("tweetid",tweetId);
                v.getContext().startActivity(intent);
                Log.d("MYAPP", "activity started");
            }
        });
        return view;
    }

    //helper method to disable subviews
    private void disableViewAndSubViews(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disableViewAndSubViews((ViewGroup) child);
            } else {
                child.setEnabled(false);
                child.setClickable(false);
                child.setLongClickable(false);
            }
        }
    }

}
