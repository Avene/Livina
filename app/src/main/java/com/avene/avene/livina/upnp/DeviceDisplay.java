package com.avene.avene.livina.upnp;

import android.graphics.drawable.Drawable;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.Icon;
import org.fourthline.cling.model.meta.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yamai on 12/13/2014.
 */
public class DeviceDisplay {
    Device mDevice;

    public DeviceDisplay(Device device) {
        this.mDevice = device;
    }

    public Device getDevice() {
        return mDevice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDisplay that = (DeviceDisplay) o;
        return mDevice.equals(that.mDevice);
    }

    @Override
    public int hashCode() {
        return mDevice.hashCode();
    }

    @Override
    public String toString() {
        boolean isFriendlyNameAvailable =
                mDevice.getDetails() != null && mDevice.getDetails().getFriendlyName() != null;
        String name = isFriendlyNameAvailable
                ? mDevice.getDetails().getFriendlyName()
                : mDevice.getDisplayString();
//  Display a little star while the mDevice is being loaded (see performance optimization earlier)
        return mDevice.isFullyHydrated() ? name : name + " *";
    }

    public List<String> getServiceNames() {
        ArrayList<String> servicesNames = new ArrayList<>();
        Service[] services = mDevice.getServices();
        for (Service s : services) {
            servicesNames.add(s.getServiceType().toFriendlyString());
        }
        return servicesNames;
    }

    public Drawable getIcon() {
        if(mDevice.hasIcons()) {
            Icon icon = mDevice.getIcons()[0];
            if(null != icon.getData()){
                return Drawable.createFromStream(new ByteArrayInputStream(icon.getData())
                        , toString());
            }else{
                return null;
            }
        }else{
            return null;
        }

    }
}
