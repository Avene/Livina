package com.avene.avene.livina.fragment;

import android.app.Activity;
import android.app.Fragment;

import com.avene.avene.livina.R;
import com.avene.avene.livina.activity.MainActivity;

/**
 * Created by yamai on 1/1/2015.
 */
public abstract class LivinaFragment extends Fragment {
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getTitle());
    }
    
    public abstract String getTitle();
}
