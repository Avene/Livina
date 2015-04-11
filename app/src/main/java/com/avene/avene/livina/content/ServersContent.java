package com.avene.avene.livina.content;
import android.util.Log;

import com.avene.avene.livina.upnp.DeviceDisplay;

import org.fourthline.cling.model.meta.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.subjects.BehaviorSubject;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 */
public class ServersContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DeviceDisplay> ITEMS = new ArrayList<DeviceDisplay>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DeviceDisplay> ITEM_MAP = new HashMap<String, DeviceDisplay>();

    private static BehaviorSubject<DeviceDisplay> contentStream = BehaviorSubject.create();

    public static BehaviorSubject<DeviceDisplay> getContentStream(){
        return contentStream;
    }
    public static void addItem(DeviceDisplay item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getDeviceMacId(), item);
    }

    public static void addItem(int position, DeviceDisplay item) {
        Log.d("ServersContent", "DeviceAdded");
        ITEMS.add(position, item);
        ITEM_MAP.put(item.getDeviceMacId(), item);
        contentStream.onNext(item);
    }

    public static void removeItem(DeviceDisplay dd){
        ITEMS.remove(dd);
        ITEM_MAP.remove(dd.getDeviceMacId());
    }

    public static void clear(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static int size() {
        return ITEMS.size();
    }

    public static void addItem(Device device) {
        DeviceDisplay d = new DeviceDisplay(device);
        int position = ITEMS.indexOf(d);
        if (position >= 0) {
            // Device already in the list, re-set new value at same position
            removeItem(d);
            addItem(position, d);
        } else {
            addItem(d);
        }
    }

    public static void subscribe(Observable<Device> deviceStream){
        deviceStream.subscribe(ServersContent::addItem);
    }
}
