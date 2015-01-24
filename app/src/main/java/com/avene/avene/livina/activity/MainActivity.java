package com.avene.avene.livina.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.avene.avene.livina.R;
import com.avene.avene.livina.adapter.MainAdapter;
import com.avene.avene.livina.factory.DrawerFragmentFactory;
import com.avene.avene.livina.fragment.AlbumDetailFragment;
import com.avene.avene.livina.fragment.AlbumsFragment;
import com.avene.avene.livina.fragment.MediaServersFragment;
import com.avene.avene.livina.fragment.NavigationDrawerFragment;
import com.avene.avene.livina.fragment.Player;
import com.avene.avene.livina.upnp.DeviceDisplay;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        AlbumsFragment.OnFragmentInteractionListener,
        MediaServersFragment.OnFragmentInteractionListener,
        Player.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    @InjectView(R.id.main_toolbar)
    Toolbar mToolbar;
    @InjectView((R.id.drawer_layout))
    DrawerLayout mDrawerLayout;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        mTitle = getTitle();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, mDrawerLayout);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, DrawerFragmentFactory.getFragment(position)).commit();
    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen if the drawer is not 
            // showing.
            // Otherwise, let the drawer decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks
        // on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "TODO: create settings view", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link AlbumsFragment.OnFragmentInteractionListener}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onAlbumsInteraction(String id) {
        Intent detailIntent = new Intent(this, AlbumDetailActivity.class);
        detailIntent.putExtra(AlbumDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

    @Override
    public void onMediaServerInteraction(String id) {
        Toast.makeText(this, "server selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayerInteraction(Uri uri) {
        Toast.makeText(this, "player interacted", Toast.LENGTH_SHORT).show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            String title;
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    title = getString(R.string.title_section1);
                    break;
                case 2:
                    title = getString(R.string.title_section2);
                    break;
                case 3:
                    title = getString(R.string.title_section3);
                    break;
                default:
                    title = "";
            }
            ((MainActivity) activity).onSectionAttached(title);
        }
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
                        ).show();
                    }
                });
                deviceRemoved(device);
            } // End of optimization, you can remove the whole block if your Android handset is fast (>=600 Mhz)

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