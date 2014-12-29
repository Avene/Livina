package com.avene.avene.livina.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avene.avene.livina.AlbumsActivity;
import com.avene.avene.livina.R;
import com.avene.avene.livina.adapter.MainAdapter;
import com.avene.avene.livina.upnp.DeviceDisplay;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public static class MainFragment extends Fragment {

        private MainAdapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;
        private AndroidUpnpService mUpnpService;
        private BrowseRegistryListener registryListener = new BrowseRegistryListener();


        private ServiceConnection serviceConnection = new ServiceConnection() {

            public void onServiceConnected(ComponentName className, IBinder service) {
                mUpnpService = (AndroidUpnpService) service;

                // Clear the list
                mAdapter.clear();

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

        public MainFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            mAdapter = new MainAdapter(getActivity());
            recyclerView.setAdapter(mAdapter);

            getActivity().getApplicationContext().bindService(
                    new Intent(getActivity(), AndroidUpnpServiceImpl.class),
                    serviceConnection,
                    Context.BIND_AUTO_CREATE
            );
            rootView.findViewById(R.id.album_list_Button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), AlbumsActivity.class);
                    startActivity(i);
                }
            });
            return rootView;
        }

        protected class BrowseRegistryListener extends DefaultRegistryListener {

// Discovery performance optimization for very slow Android devices!
            @Override
            public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
                deviceAdded(device);
            }

            @Override
            public void remoteDeviceDiscoveryFailed(Registry registry, final RemoteDevice device,
                                                    final Exception ex) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Discovery failed of '" + device.getDisplayString() + "': "
                                        + (ex != null ? ex.toString() : "Couldn't retrieve " +
                                        "device/service descriptors"),
                                Toast.LENGTH_LONG
                        ).show();}});
                deviceRemoved(device);
            }
// End of optimization, you can remove the whole block if your Android handset is fast (>=600 Mhz)

            @Override
            public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
                deviceAdded(device);
            }

            @Override
            public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
                deviceRemoved(device);
            }

            @Override
            public void localDeviceAdded(Registry registry, LocalDevice device) {
                deviceAdded(device);
            }

            @Override
            public void localDeviceRemoved(Registry registry, LocalDevice device) {
                deviceRemoved(device);
            }

            public void deviceAdded(final Device device) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        DeviceDisplay d = new DeviceDisplay(device);
                        int position = mAdapter.getPosition(d);
                        if (position >= 0) {
                            // Device already in the list, re-set new value at same position
                            mAdapter.remove(d);
                            mAdapter.insert(d, position);
                        } else {
                            mAdapter.add(d);
                        }
                    }
                });
            }

            public void deviceRemoved(final Device device) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        mAdapter.remove(new DeviceDisplay(device));
                    }
                });
            }
        }
    }
}