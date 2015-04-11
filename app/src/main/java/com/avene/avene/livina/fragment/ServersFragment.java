package com.avene.avene.livina.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.avene.avene.livina.R;
import com.avene.avene.livina.adapter.ServerListAdapter;
import com.avene.avene.livina.content.ServersContent;
import com.avene.avene.livina.upnp.BrowseRegistryListener;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.Device;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A fragment representing a list of Items.
 * <p>
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ServersFragment extends LivinaFragment
        implements AbsListView.OnItemClickListener {
    private final String TAG = "com.avene.avene.livina.fragment.ServersFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // The fragment's ListView/GridView.
    private AbsListView mListView;

    @InjectView(R.id.server_list)
    RecyclerView mRecyclerView;

    private ServerListAdapter mAdapter;
    private AndroidUpnpService mUpnpService;
    private BrowseRegistryListener registryListener;
    private ServiceConnection serviceConnection;

    // TODO: Rename and change types of parameters
    public static ServersFragment newInstance(String param1, String param2) {
        ServersFragment fragment = new ServersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ServersFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_servers, container, false);
        ButterKnife.inject(this, view);

        registryListener = new BrowseRegistryListener(this, mAdapter);

        ServersContent.subscribe(registryListener.getDeviceStream());
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new ServerListAdapter(getActivity());
        mAdapter.subscribeContentsStream(ServersContent.getContentStream());
        mRecyclerView.setAdapter(mAdapter);

        serviceConnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName className, IBinder service) {
                mUpnpService = (AndroidUpnpService) service;

                // Clear the list
                ServersContent.clear();

                // Get ready for future device advertisements
                mUpnpService.getRegistry().addListener(registryListener);

                // Now add all devices to the list we already know about
                for (Device device : mUpnpService.getRegistry().getDevices()) {
                    registryListener.deviceAdded(device);
                }

                // Search asynchronously for all devices, they will respond soon
                mUpnpService.getControlPoint().search();
            }

            public void onServiceDisconnected(ComponentName className) {
                mUpnpService = null;
            }
        };

        getActivity().getApplicationContext().bindService(
                new Intent(getActivity(), AndroidUpnpServiceImpl.class),
                serviceConnection, Context.BIND_AUTO_CREATE
        );

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public String getTitle() {
        return "Servers";
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onServerInteraction(ServersContent.ITEMS.get(position).getDeviceMacId());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onServerInteraction(String id);
    }

}
