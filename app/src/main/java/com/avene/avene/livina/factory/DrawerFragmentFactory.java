package com.avene.avene.livina.factory;

import android.app.Fragment;

import com.avene.avene.livina.fragment.AlbumsFragment;
import com.avene.avene.livina.fragment.LivinaFragment;
import com.avene.avene.livina.fragment.ServersFragment;
import com.avene.avene.livina.fragment.Player;

import java.util.ArrayList;

/**
 * Created by yamai on 12/31/2014.
 */
public class DrawerFragmentFactory {
    private final static LivinaFragment[] sDrawerFragments;
    private final static String[] sDrawerFragmentTitles;

    static {
        sDrawerFragments = new LivinaFragment[]{
                AlbumsFragment.newInstance(null, null),
                ServersFragment.newInstance(null, null),
                Player.newInstance(null, null)
        };
        ArrayList<String> titles = new ArrayList<String>();
        for(LivinaFragment f: sDrawerFragments){
            titles.add(f.getTitle());
        }
        sDrawerFragmentTitles = titles.toArray(new String[sDrawerFragments.length]);
    }

    public static Fragment getFragment(int position) {
        return sDrawerFragments[position];
    }

    public static String[] getDrawerTitlesArray() {
        return sDrawerFragmentTitles;
    }
}
