package com.lupolupo.android.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lupolupo.android.R;
import com.lupolupo.android.common.StringUtils;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.loaders.FlipLoader;
import com.lupolupo.android.views.activities.WebActivity;
import com.lupolupo.android.views.adaptors.EpisodeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ritesh Shakya
 */
public class FlipPagerFragment extends Fragment {
    private static final String KEY_EPISODE_BUNDLE = "key_episode";

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.panelPager)
    RecyclerView mRecyclerView;

    private EpisodeAdapter mPanelAdapter;
    private Episode episodeData;

    public static FlipPagerFragment newInstance(Episode episode) {
        FlipPagerFragment fragment = new FlipPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_EPISODE_BUNDLE, episode);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View gridFrag = inflater.inflate(R.layout.fragment_flip_pager, container, false);
        ButterKnife.bind(this, gridFrag);
        episodeData = getArguments().getParcelable(KEY_EPISODE_BUNDLE);
        mPanelAdapter = new EpisodeAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mPanelAdapter);
        mPanelAdapter.setComicId(episodeData.comic_id);
        mPanelAdapter.setData(FlipLoader.getInstance().getPanelList(episodeData));
        mRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    if (StringUtils.isNotNull(episodeData.link)) {
                        Intent intent = new Intent(getContext(), WebActivity.class);
                        intent.putExtra(WebActivity.URL, episodeData.link);
                        intent.putExtra(WebActivity.EPISODE_NAME, episodeData.episode_name);
                        getActivity().startActivity(intent);
                    }
                    return super.onDown(e);
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }

        });
        return gridFrag;
    }
}
