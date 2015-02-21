package com.avene.avene.livina.upnp;

import android.widget.Toast;

import com.avene.avene.livina.adapter.ServerListAdapter;
import com.avene.avene.livina.content.ServersContent;
import com.avene.avene.livina.fragment.ServersFragment;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by yamai on 2/11/2015.
 */
public class BrowseRegistryListener extends DefaultRegistryListener {

    private ServersFragment mServersFragment;
    private ServerListAdapter mAdapter;

    public BrowseRegistryListener(ServersFragment serversFragment, ServerListAdapter adapter) {
        mServersFragment = serversFragment;
        mAdapter = adapter;

    }

    // Discovery performance optimization for very slow Android devices!
    @DebugLog
    @Override
    public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
        deviceAdded(device);
    }

    @DebugLog
    @Override
    public void remoteDeviceDiscoveryFailed(Registry registry, final RemoteDevice device,
                                            final Exception ex) {
        mServersFragment.getActivity().runOnUiThread(() -> Toast.makeText(mServersFragment
                        .getActivity(),
                "Discovery failed of '" + device.getDisplayString() + "': "
                        + (ex != null ? ex.toString() : "Couldn't retrieve " +
                        "device/service descriptors"),
                Toast.LENGTH_LONG
        ).show());
        deviceRemoved(device);
    } // End of optimization, you can remove the whole block if your Android handset is fast
    // (>=600 Mhz)

    @DebugLog
    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        deviceAdded(device);
    }

    @DebugLog
    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {
        deviceRemoved(device);
    }

    @DebugLog
    @Override
    public void localDeviceAdded(Registry registry, LocalDevice device) {
        deviceAdded(device);
    }

    @DebugLog
    @Override
    public void localDeviceRemoved(Registry registry, LocalDevice device) {
        deviceRemoved(device);
    }

    public void deviceAdded(final Device device) {
        Observable<Device> observable = Observable.create(subscriber -> subscriber.onNext(device));
        observable.subscribe(mAdapter);
        observable.subscribe(ServersContent::addItem);
    }

    public void deviceRemoved(final Device device) {
        mServersFragment.getActivity().runOnUiThread(() -> ServersContent.removeItem(new
                DeviceDisplay(device)));
    }
}
