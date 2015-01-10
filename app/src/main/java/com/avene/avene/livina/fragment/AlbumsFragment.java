package com.avene.avene.livina.fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avene.avene.livina.R;
import com.avene.avene.livina.adapter.AlbumListAdapter;
import com.avene.avene.livina.content.AlbumsContent;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class AlbumsFragment extends LivinaFragment
        implements AlbumListAdapter.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    @InjectView(R.id.albums_list)
    RecyclerView mRecyclerView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private AlbumListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static AlbumsFragment newInstance(String param1, String param2) {
        AlbumsFragment fragment = new AlbumsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlbumsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_albums, container, false);

        ButterKnife.inject(this, view);

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                       RecyclerView.State state) {
                int spacingSize = getActivity().getResources().getDimensionPixelSize(R
                        .dimen.albums_grid_spacing);
                outRect.set(spacingSize, spacingSize, spacingSize, spacingSize);
            }
        });

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new AlbumListAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);


        // Set the adapter
        mRecyclerView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mAdapter.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public String getTitle() {
        return "Albums";
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onAlbumsInteraction(AlbumsContent.ITEMS.get(position).id);
        }
    }

//    /**
//     * The default content for this Fragment has a TextView that is shown when
//     * the list is empty. If you would like to change the text, call this method
//     * to supply the text it should use.
//     */
//    public void setEmptyText(CharSequence emptyText) {
//        View emptyView = mRecyclerView.getEmptyView();
//
//        if (emptyView instanceof TextView) {
//            ((TextView) emptyView).setText(emptyText);
//        }
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onAlbumsInteraction(String id);
    }

}
