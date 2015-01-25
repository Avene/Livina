package com.avene.avene.livina.content;
import com.avene.avene.livina.upnp.DeviceDisplay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static void removeItem(DeviceDisplay dd){
        ITEMS.add(dd);
        ITEM_MAP.put(dd.getDeviceMacId(), dd);
    }

    public static void clear(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static int size() {
        return ITEMS.size();
    }
    
    public static class ServerItem {
        public String id;

        public String content;

        public ServerItem(String id, String content) {
            this.id = id;
            this.content = content;
        }
        @Override
        public String toString() {
            return content;
        }

    }
}
