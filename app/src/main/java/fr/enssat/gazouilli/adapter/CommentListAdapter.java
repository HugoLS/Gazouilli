package fr.enssat.gazouilli.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.enssat.gazouilli.R;
import fr.enssat.gazouilli.model.Comment;

/**
 * Created by Adrien-ENSSAT on 19/01/2016.
 */
public class CommentListAdapter extends ArrayAdapter<Comment> {

    public CommentListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CommentListAdapter(Context context, int resource, List<Comment> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.comment_row, null);
        }

        Comment p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.comment_author);
            TextView tt2 = (TextView) v.findViewById(R.id.comment_text);


            if (tt1 != null) {
                tt1.setText(p.getAuthor());
            }

            if (tt2 != null) {
                tt2.setText(p.getText());
            }
        }

        if (position%2 == 0){
            v.setBackgroundColor(v.getResources().getColor(R.color.colorCommentPairLine));
        }else{
            v.setBackgroundColor(v.getResources().getColor(R.color.colorCommentImpairLine));
        }

        return v;
    }
}
