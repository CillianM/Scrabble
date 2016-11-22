package dcu.ie.scrabble.adapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import dcu.ie.scrabble.R;

public class PlayingAdapter extends BaseAdapter {
    private Context mContext;

    // Constructor
    public PlayingAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return squareIDs.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(squareIDs[position]);
        return imageView;
    }

    // Keep all Images in array
    public Integer[] squareIDs = {
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing,

            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing,
            R.drawable.nothing, R.drawable.nothing,R.drawable.nothing, R.drawable.nothing, R.drawable.nothing, R.drawable.nothing,R.drawable.nothing
    };
}
