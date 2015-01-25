package com.avene.avene.livina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avene.avene.livina.R;
import com.avene.avene.livina.content.AlbumsContent;
import com.avene.avene.livina.upnp.DeviceDisplay;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by yamai on 12/13/2014.
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.ViewHolder> {
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.album_title)
        public TextView mAlbumTitle;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlbumListAdapter(Context context) {
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlbumListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album_grid,
                parent, false);
        // set the view's size, margins, paddings and layout parameters
        
        return new ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        AlbumsContent.AlbumItem d = AlbumsContent.ITEMS.get(position);
        holder.mAlbumTitle.setText(d.toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return AlbumsContent.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

//    public void clear() {
//        mDataSet.clear();
//        notifyDataSetChanged();
//    }
//
//    public void remove(DeviceDisplay d) {
//        mDataSet.remove(d);
//        notifyDataSetChanged();
//    }
//
//    public void add(DeviceDisplay d) {
//        mDataSet.add(d);
//        notifyDataSetChanged();
//    }
//
//    public int getPosition(DeviceDisplay d) {
//        return mDataSet.indexOf(d);
//    }
//
//    public void insert(DeviceDisplay d, int position) {
//        mDataSet.add(position, d);
//    }
}