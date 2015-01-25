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
 * TODO: Replace all uses of this class before publishing your app.
 */
public class AlbumsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<AlbumItem> ITEMS = new ArrayList<AlbumItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, AlbumItem> ITEM_MAP = new HashMap<String, AlbumItem>();

    static {
        // Add 3 sample items.
        addItem(new AlbumItem("1", "Album 1"));
        addItem(new AlbumItem("2", "Album 2"));
        addItem(new AlbumItem("3", "Album 3"));
        addItem(new AlbumItem("4", "Album 4"));
        addItem(new AlbumItem("5", "Album 5"));
        addItem(new AlbumItem("6", "Album 6"));
        addItem(new AlbumItem("7", "Album 7"));
        addItem(new AlbumItem("8", "Album 8"));
        addItem(new AlbumItem("9", "Album 9"));
        addItem(new AlbumItem("10", "Album 10"));
        addItem(new AlbumItem("11", "Album 11"));
        addItem(new AlbumItem("12", "Album 12"));
        addItem(new AlbumItem("13", "Album 13"));
        addItem(new AlbumItem("14", "Album 14"));
        addItem(new AlbumItem("15", "Album 15"));
    }

    public static void addItem(AlbumItem dd) {
        ITEMS.add(dd);
        ITEM_MAP.put(dd.id, dd);
    }

    public static void removeItem(AlbumItem dd) {
        ITEMS.remove(dd);
        ITEM_MAP.remove(dd.id);
    }
    
    public static void clearItems(){
        ITEMS.clear();
        ITEM_MAP.clear();
    }

    public static int size() {
        return ITEMS.size();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class AlbumItem {
        public String id;
        public String content;

        public AlbumItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
