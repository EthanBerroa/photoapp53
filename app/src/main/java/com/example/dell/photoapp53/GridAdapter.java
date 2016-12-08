package com.example.dell.photoapp53;

/**
 * Created by Dell on 12/8/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class GridAdapter extends BaseAdapter {
    private Context mContext;

    public GridAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return albumNum;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View comboView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            LayoutInflater li = LayoutInflater.from(mContext);
            comboView = new View(mContext);
            comboView = li.inflate(R.layout.grid, null);
        } else {
            comboView = (View) convertView;
        }
        ImageView imageView = (ImageView)comboView.findViewById(R.id.image);
        imageView.setImageResource(R.mipmap.ic_album_icon);
        TextView textView = (TextView)comboView.findViewById(R.id.name);
        textView.setText(MainActivity.user.getAlbums().get(position).getAlbumName());

        return comboView;

    }
    // references to our images
    private int albumNum = MainActivity.user.getAlbums().size();
}
