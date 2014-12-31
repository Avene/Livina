package com.avene.avene.livina.content;

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
public class ServersContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<ServerItem> ITEMS = new ArrayList<ServerItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, ServerItem> ITEM_MAP = new HashMap<String, ServerItem>();

    static {
        // Add 3 sample items.
        addItem(new ServerItem("1", "Server 1"));
        addItem(new ServerItem("2", "Server 2"));
        addItem(new ServerItem("3", "Server 3"));
    }

    private static void addItem(ServerItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
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
