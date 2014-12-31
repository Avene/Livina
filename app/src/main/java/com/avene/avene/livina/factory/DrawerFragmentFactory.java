package com.avene.avene.livina.factory;

import android.app.Fragment;

import com.avene.avene.livina.fragment.AlbumsFragment;
import com.avene.avene.livina.fragment.MediaServersFragment;

/**
 * Created by yamai on 12/31/2014.
 */
public class DrawerFragmentFactory {
    private final static Fragment[] sDrawerFragments;

    static {
        sDrawerFragments = new Fragment[]{
                AlbumsFragment.newInstance(null, null),
                MediaServersFragment.newInstance(null, null)
        };
    }

    public static Fragment getFragment(int position) {
        return sDrawerFragments[position];
    }

    public static String[] getDrawerListArray() {
        return new String[]{"Albums", "Servers"};
    }
}
