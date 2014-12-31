package com.avene.avene.livina.dummy;

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
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    static {
        // Add 3 sample items.
        addItem(new DummyItem("1", "Album 1"));
        addItem(new DummyItem("2", "Album 2"));
        addItem(new DummyItem("3", "Album 3"));
        addItem(new DummyItem("4", "Album 4"));
        addItem(new DummyItem("5", "Album 5"));
        addItem(new DummyItem("6", "Album 6"));
        addItem(new DummyItem("7", "Album 7"));
        addItem(new DummyItem("8", "Album 8"));
        addItem(new DummyItem("9", "Album 9"));
        addItem(new DummyItem("10", "Album 10"));
        addItem(new DummyItem("11", "Album 11"));
        addItem(new DummyItem("12", "Album 12"));
        addItem(new DummyItem("13", "Album 13"));
        addItem(new DummyItem("14", "Album 14"));
        addItem(new DummyItem("15", "Album 15"));
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public String id;
        public String content;

        public DummyItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
