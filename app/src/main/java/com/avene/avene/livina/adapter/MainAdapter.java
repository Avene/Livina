package com.avene.avene.livina.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avene.avene.livina.R;
import com.avene.avene.livina.upnp.DeviceDisplay;

import java.util.ArrayList;

/**
 * Created by yamai on 12/13/2014.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<DeviceDisplay> mDataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ListView mListView;
        public ImageView mImageView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.device_name);
            mListView = (ListView) v.findViewById(R.id.services_listView);
            mImageView = (ImageView) v.findViewById(R.id.device_icon_imageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainAdapter(Context context) {
        mContext = context;
        mDataSet = new ArrayList<DeviceDisplay>();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_list,
                parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DeviceDisplay d = mDataSet.get(position);
        holder.mTextView.setText(d.toString());
        holder.mListView.setAdapter(new ArrayAdapter<>(mContext
                , android.R.layout.simple_list_item_1, d.getServiceNames()));
        Drawable icon = d.getIcon();
        if(icon != null) {
            holder.mImageView.setImageDrawable(icon);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void clear() {
        mDataSet.clear();
        notifyDataSetChanged();
    }

    public void remove(DeviceDisplay d) {
        mDataSet.remove(d);
        notifyDataSetChanged();
    }

    public void add(DeviceDisplay d) {
        mDataSet.add(d);
        notifyDataSetChanged();
    }

    public int getPosition(DeviceDisplay d) {
        return mDataSet.indexOf(d);
    }

    public void insert(DeviceDisplay d, int position) {
        mDataSet.add(position, d);
    }
}