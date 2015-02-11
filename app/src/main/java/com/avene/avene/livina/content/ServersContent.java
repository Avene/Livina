package com.avene.avene.livina.content;
import com.avene.avene.livina.upnp.DeviceDisplay;

import org.fourthline.cling.model.meta.Device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;

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

    public static void addItem(DeviceDisplay item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getDeviceMacId(), item);
    }

    public static void addItem(int position, DeviceDisplay item) {
        ITEMS.add(position, item);
        ITEM_MAP.put(item.getDeviceMacId(), item);
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
}
