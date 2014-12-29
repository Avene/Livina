package com.avene.avene.livina;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;


public class AlbumsActivity extends ActionBarActivity
        implements AlbumsFragment.OnFragmentInteractionListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_list);
        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link AlbumsFragment.OnFragmentInteractionListener}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onFragmentInteraction(String id) {
        Intent detailIntent = new Intent(this, AlbumDetailActivity.class);
        detailIntent.putExtra(AlbumDetailFragment.ARG_ITEM_ID, id);
        startActivity(detailIntent);
    }

}
