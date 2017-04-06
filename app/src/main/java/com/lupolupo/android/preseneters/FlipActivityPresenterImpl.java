package com.lupolupo.android.preseneters;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.lupolupo.android.R;
import com.lupolupo.android.common.LupolupoAPIApplication;
import com.lupolupo.android.common.StringUtils;
import com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager;
import com.lupolupo.android.model.Episode;
import com.lupolupo.android.model.Panel;
import com.lupolupo.android.model.loaders.FlipLoader;
import com.lupolupo.android.preseneters.mappers.FlipActivityMapper;
import com.lupolupo.android.views.FlipActivityView;
import com.lupolupo.android.views.activities.ComicActivity;
import com.lupolupo.android.views.activities.SplashActivity;
import com.lupolupo.android.views.activities.WebActivity;
import com.lupolupo.android.views.adaptors.FlipPagerAdapter;

import java.io.File;
import java.util.Arrays;

import bolts.Continuation;
import bolts.Task;

import static com.lupolupo.android.controllers.retrofit.LupolupoHTTPManager.PROD_ENDPOINT;

/**
 * @author Ritesh Shakya
 */
public class FlipActivityPresenterImpl implements FlipActivityPresenter {
    private final FlipActivityView mView;
    private final FlipActivityMapper mMapper;
    private FlipPagerAdapter mDashPageAdapter;
    private Episode episodeData;
    private int position;
    private boolean[] liked = {false};

    public FlipActivityPresenterImpl(FlipActivityView mView, FlipActivityMapper mMapper) {
        this.mView = mView;
        this.mMapper = mMapper;
    }

    @Override
    public void initializeData() {
        liked = new boolean[FlipLoader.getInstance().getFlipMap().size()];
        Arrays.fill(liked, Boolean.FALSE);
        if (FlipLoader.getInstance().getFlipMap().size() != 0) {
            mDashPageAdapter.setData(FlipLoader.getInstance().getFlipMap().keySet());
            mMapper.setCurrentPage(0);
            setPage(0);
        }
    }

    @Override
    public void initializeAdaptor() {
        mDashPageAdapter = new FlipPagerAdapter(((AppCompatActivity) mView.getActivity()).getSupportFragmentManager());
        mMapper.registerAdapter(mDashPageAdapter);
    }

    @Override
    public void setPage(int position) {
        episodeData = mDashPageAdapter.getData().get(position);
        this.position = position;
        setLikeDrawable();
    }

    @Override
    public void share() {
        Panel panel = FlipLoader.getInstance().getFlipMap().get(mDashPageAdapter.getData().get(position)).get(0);
        File imgFile = new File(LupolupoAPIApplication.get().getCacheDir(), "images/" + episodeData.comic_id + "/" + panel.episode_id + "/" + panel.panel_image);
        Uri contentUri = FileProvider.getUriForFile(mView.getActivity(), "com.lupolupo.android.file_provider", imgFile);
        if (contentUri != null) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "This app is too Funny – LupoLupo. You should check it out!");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType(mView.getActivity().getContentResolver().getType(contentUri));
            mView.getActivity().startActivity(Intent.createChooser(shareIntent, mView.getActivity().getResources().getText(R.string.send_to)));
        }
    }

    @Override
    public void showComic() {
        Intent intent = new Intent(mView.getActivity(), SplashActivity.class);
        intent.putExtra(ComicActivity.INTENT_COMIC_FULL, episodeData.comic_id);
        mView.getActivity().startActivity(intent);
    }

    @Override
    public void subscribe() {
        LupolupoHTTPManager.getInstance().subscribe(episodeData.comic_id).onSuccess(new Continuation<String, Task<Void>>() {
            @Override
            public Task<Void> then(Task<String> results) throws Exception {
                mView.toggleSubButton(true);
                Toast.makeText(mView.getActivity(), results.getResult(), Toast.LENGTH_SHORT).show();
                return null;
            }
        });
    }

    @Override
    public void showWeb() {
        if (StringUtils.isNotNull(episodeData.link)) {
            Intent intent = new Intent(mView.getActivity(), WebActivity.class);
            intent.putExtra(WebActivity.URL, episodeData.link);
            intent.putExtra(WebActivity.EPISODE_NAME, episodeData.episode_name);
            mView.getActivity().startActivity(intent);
        }
//        else {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PROD_ENDPOINT + "panels.php?epid=" + episodeData.id + "&cid=" + episodeData.comic_id));
//            mView.getActivity().startActivity(browserIntent);
//        }
    }

    @Override
    public void onLike() {
        liked[position] = !liked[position];
        setLikeDrawable();
        LupolupoHTTPManager.getInstance().postLikeUnlike(episodeData.id, liked[position]);
    }

    private void setLikeDrawable() {
        if (liked[position]) {
            mView.setLikeDrawable(ContextCompat.getDrawable(LupolupoAPIApplication.get(), R.drawable.ic_favorite_black_24px));
        } else {
            mView.setLikeDrawable(ContextCompat.getDrawable(LupolupoAPIApplication.get(), R.drawable.ic_favorite_border_black_24px));
        }
    }

    @Override
    public void initializeMenuItem() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mView.getActivity(),
                R.layout.toolbar_spinner_item_actionbar, Arrays.asList(mView.getActivity().getResources().getStringArray(R.array.spinner_list_item_array)));
        dataAdapter.setDropDownViewResource(R.layout.toolbar_spinner_item_dropdown);
        mView.setAdapter(dataAdapter);
        SpinnerInteractionListener listener = new SpinnerInteractionListener(mView.getActivity());
        mView.setListeners(listener);
    }

    @Override
    public void initializeViews() {
        mView.initializeToolbar();
    }
}
