package com.avene.avene.livina.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.avene.avene.livina.R;
import com.avene.avene.livina.content.ServersContent;
import com.avene.avene.livina.upnp.DeviceDisplay;

import org.fourthline.cling.model.meta.Device;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observer;

/**
 * Created by yamai on 12/13/2014.
 */
public class ServerListAdapter
        extends RecyclerView.Adapter<ServerListAdapter.ViewHolder>
        implements Observer<Device> {
    private Context mContext;

    @Override
    public void onCompleted() {
        notifyDataSetChanged();
    }

    @Override
    public void onError(Throwable e) {
        notifyDataSetChanged();
    }

    @Override
    public void onNext(Device device) {
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.server_switch)
        public Switch mSwitch;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.inject(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ServerListAdapter(Context context) {
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ServerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server,
                parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DeviceDisplay d = ServersContent.ITEMS.get(position);
        holder.mSwitch.setText(d.toString());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ServersContent.size();
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